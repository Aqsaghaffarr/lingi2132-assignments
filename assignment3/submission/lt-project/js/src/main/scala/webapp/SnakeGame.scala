package webapp

import DSL.{Color, _}

object SnakeGame extends Game {

  var timeout = 100
  var alive = true
  var spotSize = 20

  override def useGameDSL(canvasy: Canvasy, ending: String): () => Unit = {
    // Define game parameters.
    val height = 20 // Number of rows on the grid (with walls).
    val width = 20 // Number of columns on the grid (with walls).
    val midH = (height - height%2) / 2 * spotSize
    val midW = (width - width%2) / 2 * spotSize
    val maxApples = 2 // Maximum concurrent number of apples on the field.
    val apples: ComposedSpot[Apple] = ComposedSpot[Apple](Array[Apple]()) // List of apples on the field.
    val score = Score(Point(midH + 4 * spotSize, midW - 30), spotSize, 0) // Score of the player (number of apples eaten).
    val winMessage = Message(Point(midH, midH - 30), spotSize, "Your score is:")
    val finalMessage = Message(Point(midH, midW), spotSize, ending)
    val random = new scala.util.Random // RNG.
    var lives = 1 // Number of lives.
    val snakeColor: String = "green"
    val appleColor: String = "red"
    val emptyColor: String = "white"

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
    val middle: Point = Point((height - height % 2) / 2, (width - width % 2) / 2)
    val snake: ComposedSpot[Snake] = ComposedSpot[Snake](Array(Snake(middle, spotSize)))
    var direction = Point(1, 0) // Direction of the snake (initially, to the right).
    grid.spots(middle.x.toInt)(middle.y.toInt) = snake.head

    snake change Color(snakeColor)

    // If there are not enough apples on the field, add more.
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

    // Draw first time.
    canvasy.drawGrid(grid)

    // Update snake direction.
    def update(): Point = {
      if (Keyboard.isHoldingLeft && direction != Point(1, 0)) Point(-1, 0)
      else if (Keyboard.isHoldingRight && direction != Point(-1, 0)) Point(1, 0)
      else if (Keyboard.isHoldingUp && direction != Point(0, 1)) Point(0, -1)
      else if (Keyboard.isHoldingDown && direction != Point(0, -1)) Point(0, 1)
      else if (alive) direction
      else Point(0, 0)
    }

    // Main game loop.
    val gameLoop = () => {
      // Check whether next snake position is occupied, act accordingly.
      direction = update()
      val newHeadPos: Point = snake.head.position + direction
      val newSpot: Spot = grid.spots(newHeadPos.x.toInt)(newHeadPos.y.toInt)
      var elongate: Boolean = false
      newSpot match {
        case _: Apple =>
          // If snake moves on apple, "eat" it.
          apples.remove(newHeadPos)
          score.score += 1
          elongate = true
        case _: Wall | _: Snake =>
          // If snake hits wall or itself, game over.
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

      // If the snake is still alive, regenerate grid.
      if (alive) {
        // Check if snake needs to become longer.
        if (elongate) {
          val newSnakeBlock: Snake = Snake(newHeadPos, spotSize)
          newSnakeBlock change Color(snakeColor)
          snake prepend newSnakeBlock
        } else {
          val lastPosition: Point = snake.last.position
          grid.spots(lastPosition.x.toInt)(lastPosition.y.toInt) = field(lastPosition.x.toInt - 1)(lastPosition.y.toInt - 1)
          snake.move(newHeadPos)
        }

        // Draw snake on grid.
        snake.foreach(sb => grid.spots(sb.position.x.toInt)(sb.position.y.toInt) = sb)

        // Regen apples and draw grid.
        generateApples()
        canvasy.drawGrid(grid)
      }
    }

    // Return game loop.
    gameLoop
  }
}
