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
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    val w = 500
    canvas.width = w
    canvas.height = w
    useGameDSL(canvas)
  }

  def useGameDSL(canvas: html.Canvas): Unit = {
    val spotSize = 20
    val canvasy = new Canvasy(canvas)
    val numberMaxApple = 2
    var numberApple = 0

    // walls
    val walls_top = Array.tabulate(20)(i => Wall(Point(0, i), spotSize))
    val walls_right = Array.tabulate(18)(i => Wall(Point(i + 1, 0), spotSize))
    val walls_bottom = Array.tabulate(20)(i => Wall(Point(19, i), spotSize))
    val walls_left = Array.tabulate(18)(i => Wall(Point(i + 1, 19), spotSize))

    // field
    val field : Array[Array[Empty]] = new Array[Array[Empty]](18)
    for (i <- 0 until 18) {
      field(i) = Array.tabulate(18)(j => Empty(Point(i + 1, j + 1), spotSize))
      field(i) change Color("white")
    }

    val grid = Grid(20, 20, walls_top, walls_bottom, walls_right, walls_left, field)

    var snake: Array[Snake] = new Array[Snake](1)
    snake(0) = Snake(Point(5, 5), spotSize)
    var direction = Point(1, 0)
    grid.spots(5)(5) = snake(0)

    snake change Color("green")
    canvasy.drawGrid(grid)

    val keysDown = mutable.HashMap[Int, Boolean]()

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      keysDown += e.keyCode -> true
    }, useCapture = false)

    dom.window.addEventListener("keyup", (e: dom.KeyboardEvent) => {
      keysDown -= e.keyCode
    }, useCapture = false)

    def update(): Point = {
      if      (keysDown.contains(KeyCode.Left))  Point(-1, 0)
      else if (keysDown.contains(KeyCode.Right)) Point(1, 0)
      else if (keysDown.contains(KeyCode.Up))    Point(0, -1)
      else if (keysDown.contains(KeyCode.Down))  Point(0, 1)
      else                                       direction
    }

    // The main game loop
    val gameLoop = () => {
      grid.spots(direction.x)(direction.y) = field(snake(0).point.x + direction.x - 1)(snake(0).point.y + direction.y - 1)
      direction = update()
      snake(0).move(snake(0).point + direction)
      grid.spots(snake(0).point.x)(snake(0).point.y) = snake(0)
      canvasy.drawGrid(grid)

      if (numberApple < numberMaxApple) {
        val random = new scala.util.Random
        val xApple = random.nextInt(spotSize - 2)
        val yApple = random.nextInt(spotSize - 2)
        val pApple = Point(xApple + 1, yApple + 1)
        val apple = Apple(pApple, spotSize)
        apple change Color("red")
        grid.spots(pApple.x)(pApple.y) = apple
        canvasy.drawGrid(grid)
        numberApple += 1
      }
    }

    dom.window.setInterval(gameLoop, 50)
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