package webapp

//import DSLDemo._
import DSL.Extends._
import DSL.{Color, _}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{document, html}

import scala.collection.mutable
//import DSLDemo.Extends._

object Main {

  def main(args: Array[String]): Unit = {
    // Define canvas.
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    val w = 500
    canvas.width = w
    canvas.height = w
    useGameDSL(canvas)
  }

  def useGameDSL(canvas: html.Canvas): Unit = {
    val spotSize = 20 // Size of the squares on the grid.
    val height = 20 // Number of rows on the grid (with walls).
    val width = 20 // Number of columns on the grid (with walls).
    val canvasy = new Canvasy(canvas)
    val numberMaxApple = 2 // Maximum concurrent number of apples on the field.
    var numberApple = 0 // Current number of apples on the field.
    var score = 0 // Score of the player (number of apples eaten).
    val random = new scala.util.Random // RNG.
    var lives = 1 // Number of lives.
    val snakeColor: String = "green"
    val appleColor: String = "red"
    val emptyColor: String = "white"

    // Walls.
    val wallTop: Array[Wall] = Array.tabulate(width)(i => Wall(Point(0, i), spotSize))
    val wallRight: Array[Wall] = Array.tabulate(height - 2)(i => Wall(Point(i + 1, 0), spotSize))
    val wallBottom: Array[Wall] = Array.tabulate(width)(i => Wall(Point(height - 1, i), spotSize))
    val wallLeft: Array[Wall] = Array.tabulate(height - 2)(i => Wall(Point(i + 1, width - 1), spotSize))

    // Field.
    val field : Array[Array[Empty]] = new Array[Array[Empty]](height - 2)
    // Define the white squares that make up the playing field.
    for (i <- field.indices) {
      field(i) = Array.tabulate(height - 2)(j => Empty(Point(i + 1, j + 1), spotSize))
      field(i) change Color(emptyColor)
    }

    // Define the grid, containing both the walls and the field.
    val grid = Grid(width, height, wallTop, wallBottom, wallRight, wallLeft, field)

    // The snake (starts off at size 1, in the middle of the playing field).
    val middle: Point = Point((height - height%2) / 2, (width - width%2) / 2)
    val snake: Snake = Snake(List(SnakeBlock(middle, spotSize)))
    var direction = Point(1, 0) // Direction of the snake (initially, to the right).
    grid.spots((height - height%2) / 2)((width - width%2) / 2) = snake(0)

    snake change Color(snakeColor)
    canvasy.drawGrid(grid)

    // Detect key presses.
    val keysDown = mutable.HashMap[Int, Boolean]()

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      keysDown += e.keyCode -> true
    }, useCapture = false)

    dom.window.addEventListener("keyup", (e: dom.KeyboardEvent) => {
      keysDown -= e.keyCode
    }, useCapture = false)

    // Update snake direction.
    def update(): Point = {
      if (keysDown.contains(KeyCode.Left) && direction != Point(1, 0)) {
        Point(-1, 0)
      } else if (keysDown.contains(KeyCode.Right) && direction != Point(-1, 0)) {
        Point(1, 0)
      } else if (keysDown.contains(KeyCode.Up) && direction != Point(0, 1)) {
        Point(0, -1)
      } else if (keysDown.contains(KeyCode.Down) && direction != Point(0, -1)) {
        Point(0, 1)
      } else {
        direction
      }
    }

    // Main game loop.
    val gameLoop = () => {
      direction = update()
      val newHeadPos: Point = snake(0).point + direction
      val newSpot: Spot = grid.spots(newHeadPos.x.toInt)(newHeadPos.y.toInt)
      var elongate: Boolean = false
      newSpot match {
        case _: Apple =>
          numberApple -= 1
          score += 1
          elongate = true
        case _: Wall =>
          lives -= 1
        case _: SnakeBlock =>
          lives -= 1
          snake change Color("blue")
        case _: Empty =>
        case _ => throw new UnsupportedOperationException
      }

      if (elongate) {
        val newSnakeBlock: SnakeBlock = SnakeBlock(snake.last().point, spotSize)
        newSnakeBlock change Color(snakeColor)
        snake += newSnakeBlock
      } else {
        val lastPosition: Point = snake.last().point
        grid.spots(lastPosition.x.toInt)(lastPosition.y.toInt) = field(lastPosition.x.toInt - 1)(lastPosition.y.toInt - 1)
      }

      snake.move(newHeadPos)

      for (sb <- snake) {
        grid.spots(sb.point.x.toInt)(sb.point.y.toInt) = sb
      }

      canvasy.drawGrid(grid)

      if (numberApple < numberMaxApple) {
        var applePosition = Point(random.nextInt(spotSize - 2) + 1, random.nextInt(spotSize - 2) + 1)
        while (snake.containsPosition(applePosition)) {
          applePosition = Point(random.nextInt(spotSize - 2) + 1, random.nextInt(spotSize - 2) + 1)
        }
        val apple = Apple(applePosition, spotSize)
        apple change Color(appleColor)
        grid.spots(applePosition.x.toInt)(applePosition.y.toInt) = apple
        canvasy.drawGrid(grid)
        numberApple += 1
      }
    }

    dom.window.setInterval(gameLoop, 100)
  }

  /*def scalaJSDemo(c: html.Canvas): Unit = {
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val w = 300
    c.width = w
    c.height = w

    ctx.strokeStyle = "red"
    ctx.lineWidth = 3
    ctx.beginPath()
    ctx.moveTo(w/3, 0)
    ctx.lineTo(w/3, w/3)
    ctx.moveTo(w*2/3, 0)
    ctx.lineTo(w*2/3, w/3)
    ctx.moveTo(w, w/2)
    ctx.arc(w/2, w/2, w/2, 0, 3.14)

    ctx.stroke()
  }*/

  /*/*
   * TODO: When you've done the first part, you should be able to uncomment this
   *       method and call it without problems
   */
  def useMySuperDSL(canvas: html.Canvas): Unit = {
    // After you've done the first part of the project, everything should
    // compile and do the expected behaviour
    val canvasy = new Canvasy(canvas)

    val circles = Array.fill(4)(Circle(50, 100, 100))
    val rectangles = Array.tabulate(5)(i => Rectangle(i*10, i*10, 10, 30))

    canvasy += circles
    canvasy += rectangles

    // First we can modify property of Shapes by modifying their property directly
    circles(0) color "red"
    rectangles(0) strokeWidth 10
    rectangles(1) moveX 10

    // We should also be able to do the same on a group of shapes
    // (list, array, iterables, ...)
    print(circles.isInstanceOf[Array[Circle]])
    circles moveX 20

    // We can also change property using the CanvasElementModifier trait
    circles change Color("blue")

    // We can group the shapes easily with the keyword and
    val superGroupOfShapes = circles and rectangles

    // And of course, we have foreach/map/flatmap available
    (rectangles(0) and circles(1)).foreach(_ moveY 30)

    // We should also be able to use common operators to group shapes
    val anotherSuperGroup = rectangles ++ circles

    // We can get back the elements by their index
    val s = anotherSuperGroup(0)

    // Take care that some property change should not compile, like this one
    // (rectangles(0) + circles(0)) change Width(30)
    // because Circles have no width

    // You can have a nice draw function to draw all of this on the canvas
    canvasy.draw()
  }*/
}