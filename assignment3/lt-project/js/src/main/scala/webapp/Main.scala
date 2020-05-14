package webapp

//import DSLDemo._
import DSL.Extends._
import DSL.{Color, _}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{document, html}
//import DSLDemo.Extends._
import scala.util.Random

object Main {

  def createCanvas(width: Int, height: Int): html.Canvas = {
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    canvas.width = width
    canvas.height = height
    canvas
  }

  def main(args: Array[String]): Unit = {
    // Define canvas.
    val canvas = createCanvas(500, 500)
    useGameDSL(canvas)
  }

  def usePingDSL(canvas: html.Canvas): Unit = {
    val spotSize = 5 // Size of the squares on the grid.
    val height = 100 // Number of rows on the grid (with walls).
    val width = 100 // Number of columns on the grid (with walls).
    val canvasy = new Canvasy(canvas)
    val playerLength = 10
    val score1 = Score(Point(35*spotSize, 10*spotSize), spotSize, 0)
    val score2 = Score(Point(65*spotSize, 10*spotSize), spotSize, 0)
    val message = Message(Point(50*spotSize, 5*spotSize), spotSize, "Score:")
    val winMessage1 = Message(Point(50*spotSize, 30*spotSize), spotSize, "Player 1 wins!")
    val winMessage2 = Message(Point(50*spotSize, 30*spotSize), spotSize, "Player 2 wins!")
    val finalMessage = Message(Point(50*spotSize, 40*spotSize), spotSize, "Please press space to restart.")
    score1 change Font("15px Helvetica")
    score2 change Font("15px Helvetica")
    val lives = 6 // Number of lives.
    val emptyColor: String = "white"
    var alive: Boolean = true
    val up: Point = Point(0, -1)
    val down: Point = Point(0, 1)
    val freeze: Point = Point(0, 0)
    var direction1: Point = freeze
    var direction2: Point = freeze
    val timeout = 20
    var distance: Point = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
    val middle: Point = Point((height - height%2) / 2, (width - width%2) / 2)

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

    val player1: ComposedSpot[Player] = ComposedSpot(Array.tabulate(playerLength)(i => Player(Point(5, middle.y.toInt - playerLength/2 + i), spotSize)))
    val player2: ComposedSpot[Player] = ComposedSpot(Array.tabulate(playerLength)(i => Player(Point(95, middle.y.toInt - playerLength/2 + i), spotSize)))

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
        ball.move(Point(width/2,height/2))
        grid.spots(width/2)(height/2) = ball
        distance = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
        if(score2.score == lives) alive = false
      } else if (ball.position.x > width - 5) {
        score1.score += 1
        grid.spots(ball.position.x.toInt)(ball.position.y.toInt) = field(ball.position.x.toInt - 1)(ball.position.y.toInt - 1)
        ball.move(Point(width/2,height/2))
        grid.spots(width/2)(height/2) = ball
        distance = Point(Random.nextInt(2) - 0.5, Random.nextInt(2) - 0.5)
        if(score1.score == lives) alive = false
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
        if(!alive){
          if(score1.score > score2.score){
            canvasy.showMessage(winMessage1)
          }
          else{
            canvasy.showMessage(winMessage2)
          }
          canvasy.showMessage(finalMessage)
        }
      }

    val reset = () => {
      if (Keyboard.keysDown.contains(KeyCode.Space)){
        val w = canvas.width
        val h = canvas.height
        document.body.removeChild(canvas)
        usePingDSL(createCanvas(w, h))
      }
    }

    val decision = () => if (alive) gameLoop() else reset()

    dom.window.setInterval(decision, timeout)
  }

  def useGameDSL(canvas: html.Canvas): Unit = {
    val spotSize = 20 // Size of the squares on the grid.
    val height = 20 // Number of rows on the grid (with walls).
    val width = 20 // Number of columns on the grid (with walls).
    val canvasy = new Canvasy(canvas)
    val maxApples = 2 // Maximum concurrent number of apples on the field.
    val apples: ComposedSpot[Apple] = ComposedSpot[Apple](Array[Apple]()) // List of apples on the field.
    val score = Score(Point(height/2 * spotSize + 4*spotSize, width/2 * spotSize - 30), spotSize, 0) // Score of the player (number of apples eaten).
    val winMessage = Message(Point(height/2 * spotSize, width/2 * spotSize - 30), spotSize, "Your score is:")
    val finalMessage = Message(Point(height/2 * spotSize, width/2 * spotSize), spotSize, "Please press space to continue.")
    val random = new scala.util.Random // RNG.
    var lives = 1 // Number of lives.
    val snakeColor: String = "green"
    val appleColor: String = "red"
    val emptyColor: String = "white"
    var alive: Boolean = true

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
    val middle: Point = Point((height - height%2) / 2, (width - width%2) / 2)
    val snake: ComposedSpot[Snake] = ComposedSpot[Snake](Array(Snake(middle, spotSize)))
    var direction = Point(1, 0) // Direction of the snake (initially, to the right).
    grid.spots(middle.x.toInt)(middle.y.toInt) = snake.head

    snake change Color(snakeColor)

    def generateApples(): Unit = {
      while (apples.size < maxApples) {
        var applePosition = Point(random.nextInt(height - 2) + 1, random.nextInt(width - 2) + 1)
        while (snake.contains(applePosition) || apples.contains(applePosition)) {
          applePosition = Point(random.nextInt(height - 2) + 1, random.nextInt(width - 2) + 1)
        }
        val apple = Apple(applePosition, spotSize)
        apple change Color(appleColor)
        grid.spots(applePosition.x.toInt)(applePosition.y.toInt) = apple
        apples prepend apple
      }
    }

    generateApples()

    canvasy.drawGrid(grid)

    // Update snake direction.
    def update(): Point = {
      if (Keyboard.isHoldingLeft && direction != Point(1, 0)) {
        Point(-1, 0)
      } else if (Keyboard.isHoldingRight && direction != Point(-1, 0)) {
        Point(1, 0)
      } else if (Keyboard.isHoldingUp && direction != Point(0, 1)) {
        Point(0, -1)
      } else if (Keyboard.isHoldingDown && direction != Point(0, -1)) {
        Point(0, 1)
      } else if (alive) {
        direction
      } else {
        Point(0, 0)
      }
    }

    // Main game loop.
    val gameLoop = () => {
      direction = update()
      val newHeadPos: Point = snake.head.position + direction
      val newSpot: Spot = grid.spots(newHeadPos.x.toInt)(newHeadPos.y.toInt)
      var elongate: Boolean = false
      newSpot match {
        case _: Apple =>
          apples.remove(newHeadPos)
          score.score += 1
          elongate = true
        case _: Wall | _: Snake =>
          lives -= 1
          if (lives == 0) {
            canvasy.showScore(score)
            canvasy.showMessage(winMessage)
            canvasy.showMessage(finalMessage)
          }
          alive = false
        case _: Empty =>
        case _ => throw new UnsupportedOperationException
      }

      if (alive) {
        if (elongate) {
          val newSnakeBlock: Snake = Snake(newHeadPos, spotSize)
          newSnakeBlock change Color(snakeColor)
          snake prepend newSnakeBlock
        } else {
          val lastPosition: Point = snake.last.position
          grid.spots(lastPosition.x.toInt)(lastPosition.y.toInt) = field(lastPosition.x.toInt - 1)(lastPosition.y.toInt - 1)
          snake.move(newHeadPos)
        }

        snake.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)

        generateApples()
        canvasy.drawGrid(grid)
      }
    }

    val reset = () => {
      if (Keyboard.keysDown.contains(KeyCode.Space)){
        val w = canvas.width
        val h = canvas.height
        document.body.removeChild(canvas)
        useGameDSL(createCanvas(w, h))
      }
    }

    val decision = () => if (alive) gameLoop() else reset()

    dom.window.setInterval(decision, 100)
  }
}