package webapp

//import DSLDemo._
import DSL.Extends._
import DSL.{Color, _}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{document, html}
//import DSLDemo.Extends._
import scala.util.Random

object PongGame {

  val timeout = 20
  var alive: Boolean = true

  def useGameDSL(canvas: html.Canvas): () => Unit = {
    val spotSize = 5 // Size of the squares on the grid.
    val height = 100 // Number of rows on the grid (with walls).
    val width = 100 // Number of columns on the grid (with walls).
    val canvasy = new Canvasy(canvas)
    val playerLength = 10
    val score1 = Score(Point(35 * spotSize, 10 * spotSize), spotSize, 0)
    val score2 = Score(Point(65 * spotSize, 10 * spotSize), spotSize, 0)
    val message = Message(Point(50 * spotSize, 5 * spotSize), spotSize, "Score:")
    val winMessage1 = Message(Point(50 * spotSize, 30 * spotSize), spotSize, "Player 1 wins!")
    val winMessage2 = Message(Point(50 * spotSize, 30 * spotSize), spotSize, "Player 2 wins!")
    val finalMessage = Message(Point(50 * spotSize, 40 * spotSize), spotSize, "Please press space to restart.")
    score1 change Font("15px Helvetica")
    score2 change Font("15px Helvetica")
    val lives = 1 // Number of lives.
    val emptyColor: String = "white"
    val up: Point = Point(0, -1)
    val down: Point = Point(0, 1)
    val freeze: Point = Point(0, 0)
    var direction1: Point = freeze
    var direction2: Point = freeze
    var distance: Point = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
    val middle: Point = Point((height - height % 2) / 2, (width - width % 2) / 2)

    // Walls.
    val wallTop: ComposedSpot[Wall] = ComposedSpot(Array.tabulate(width)(i => Wall(Point(0, i), spotSize)))
    val wallRight: ComposedSpot[Wall] = ComposedSpot(Array.tabulate(height - 2)(i => Wall(Point(i + 1, 0), spotSize)))
    val wallBottom: ComposedSpot[Wall] = ComposedSpot(Array.tabulate(width)(i => Wall(Point(height - 1, i), spotSize)))
    val wallLeft: ComposedSpot[Wall] = ComposedSpot(Array.tabulate(height - 2)(i => Wall(Point(i + 1, width - 1), spotSize)))

    // Field.
    val field: ComposedSpot2D[Empty] = ComposedSpot2D(Array.ofDim[Empty](height, width))
    // Define the white squares that make up the playing field.
    for (i <- 0 until height; j <- 0 until width) {
      field(i)(j) = Empty(Point(i + 1, j + 1), spotSize)
    }
    field change Color(emptyColor)

    // Define the grid, containing both the walls and the field.
    val grid = Grid(width, height, wallTop, wallBottom, wallRight, wallLeft, field)

    // The snake (starts off at size 1, in the middle of the playing field).

    val ball = Ball(Point(middle.x, middle.y), spotSize)
    grid.spots(middle.x.toInt)(middle.y.toInt) = ball

    val player1: ComposedSpot[Player] = ComposedSpot(Array.tabulate(playerLength)(i => Player(Point(5, middle.y.toInt - playerLength / 2 + i), spotSize)))
    val player2: ComposedSpot[Player] = ComposedSpot(Array.tabulate(playerLength)(i => Player(Point(95, middle.y.toInt - playerLength / 2 + i), spotSize)))

    player1.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)
    player2.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)

    canvasy.drawGrid(grid)
    canvasy.showScore(score1)
    canvasy.showScore(score2)
    canvasy.showMessage(message)

    def drawEmpty(player: Spot): Unit = {
      grid.spots(player.position.x.toInt)(player.position.y.toInt) = field(player.position.x.toInt - 1)(player.position.y.toInt - 1)
    }

    // Update direction.
    def update(upKey: Boolean, downKey: Boolean, player: ComposedSpot[Player]): Point = {
      if (upKey && player.head.position.y > 1) {
        drawEmpty(player.last)
        up
      } else if (downKey && player.last.position.y < 98) {
        drawEmpty(player.head)
        down
      } else {
        freeze
      }
    }

    def collision1(player: ComposedSpot[Player]): Unit = {
      val corner1 = player.last.position
      val corner2 = player.head.position
      val minY = math.min(corner1.y, corner2.y)
      val maxY = math.max(corner1.y, corner2.y)

      if (ball.position.x - 1 <= corner1.x && minY <= ball.position.y && ball.position.y <= maxY) {
        distance = distance.copy(x = -distance.x)
      }
    }

    def collision2(player: ComposedSpot[Player]): Unit = {
      val corner1 = player.last.position
      val corner2 = player.head.position
      val minY = math.min(corner1.y, corner2.y)
      val maxY = math.max(corner1.y, corner2.y)

      if (ball.position.x >= corner1.x && minY <= ball.position.y && ball.position.y <= maxY) {
        distance = distance.copy(x = -distance.x)
      }
    }

    // Main game loop.
      val gameLoop = () => {
      var newBallPos = ball.position + distance
      if (newBallPos.y <= 1 || newBallPos.y >= height - 1) {
        distance.y = -distance.y
        newBallPos = ball.position + distance
      }

      grid.spots(ball.position.x.toInt)(ball.position.y.toInt) = field(ball.position.x.toInt - 1)(ball.position.y.toInt - 1)
      ball.move(newBallPos)
      grid.spots(newBallPos.x.toInt)(newBallPos.y.toInt) = ball


      if (ball.position.x < 5) {
        score2.score += 1
        grid.spots(ball.position.x.toInt)(ball.position.y.toInt) = field(ball.position.x.toInt - 1)(ball.position.y.toInt - 1)
        ball.move(Point(width / 2, height / 2))
        grid.spots(width / 2)(height / 2) = ball
        distance = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
        if (score2.score == lives) alive = false
      } else if (ball.position.x > width - 5) {
        score1.score += 1
        grid.spots(ball.position.x.toInt)(ball.position.y.toInt) = field(ball.position.x.toInt - 1)(ball.position.y.toInt - 1)
        ball.move(Point(width / 2, height / 2))
        grid.spots(width / 2)(height / 2) = ball
        distance = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
        if (score1.score == lives) alive = false
      }
      collision1(player1)
      collision2(player2)

      direction1 = update(Keyboard.isHoldingS, Keyboard.isHoldingX, player1)
      direction2 = update(Keyboard.isHoldingK, Keyboard.isHoldingM, player2)
      player1.translate(direction1)
      player2.translate(direction2)

      player1.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)
      player2.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)
      grid.spots(ball.position.x.toInt)(ball.position.y.toInt) = ball

      canvasy.drawGrid(grid)
      canvasy.showScore(score1)
      canvasy.showScore(score2)
      canvasy.showMessage(message)
      if (!alive) {
        if (score1.score > score2.score) {
          canvasy.showMessage(winMessage1)
        }
        else {
          canvasy.showMessage(winMessage2)
        }
        canvasy.showMessage(finalMessage)
      }
    }

    gameLoop
  }

}
