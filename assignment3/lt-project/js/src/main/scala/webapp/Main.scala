package webapp

//import DSLDemo._
import DSL.{Color, _}
import DSL.Extends._
import org.scalajs.dom.{document, html}
import org.scalajs.dom
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

    val canvasy = new Canvasy(canvas)

    """walls"""
    val walls_top = Array.tabulate(20)(i => Wall(0, i*20, 20, 20))
    val walls_bottom = Array.tabulate(20)(i => Wall(i*20, 0, 20, 20))
    val walls_right = Array.tabulate(20)(i => Wall(380, i*20, 20, 20))
    val walls_left = Array.tabulate(20)(i => Wall(i*20, 400, 20, 20))

    walls_top change Color("black")
    walls_bottom change Color("black")
    walls_right change Color("black")
    walls_left change Color("black")

    """field"""
    var field : Array[Empty] = new Array[Empty](400)
    for (i <- 0 until 20 ){
      for (j <- 1 until 20 ){
        field(i*20 + j) = Empty(j*20, i*20, 20, 20)
      }
    }


    val field_1 = Array.tabulate(20)(i => Empty(20, i*20, 20, 20))
    val field_2 = Array.tabulate(20)(i => Empty(40, i*20, 20, 20))
    val field_3 = Array.tabulate(20)(i => Empty(60, i*20, 20, 20))
    val field_4 = Array.tabulate(20)(i => Empty(80, i*20, 20, 20))
    val field_5 = Array.tabulate(20)(i => Empty(100, i*20, 20, 20))
    val field_6 = Array.tabulate(20)(i => Empty(120, i*20, 20, 20))
    val field_7 = Array.tabulate(20)(i => Empty(140, i*20, 20, 20))
    val field_8 = Array.tabulate(20)(i => Empty(160, i*20, 20, 20))
    val field_9 = Array.tabulate(20)(i => Empty(180, i*20, 20, 20))
    val field_10 = Array.tabulate(20)(i => Empty(200, i*20, 20, 20))
    val field_11 = Array.tabulate(20)(i => Empty(220, i*20, 20, 20))
    val field_12 = Array.tabulate(20)(i => Empty(240, i*20, 20, 20))
    val field_13 = Array.tabulate(20)(i => Empty(260, i*20, 20, 20))
    val field_14 = Array.tabulate(20)(i => Empty(280, i*20, 20, 20))
    val field_15 = Array.tabulate(20)(i => Empty(300, i*20, 20, 20))
    val field_16 = Array.tabulate(20)(i => Empty(320, i*20, 20, 20))
    val field_17 = Array.tabulate(20)(i => Empty(340, i*20, 20, 20))
    val field_18 = Array.tabulate(20)(i => Empty(360, i*20, 20, 20))

    /*val apple = Apple(50, 50, 10, 10)

    apple change Color("green")*/

    canvasy += walls_top
    canvasy += walls_bottom
    canvasy += walls_right
    canvasy += walls_left
    canvasy += field_1
    canvasy += field_2
    canvasy += field_3
    canvasy += field_4
    canvasy += field_5
    canvasy += field_6
    canvasy += field_7
    canvasy += field_8
    canvasy += field_9
    canvasy += field_10
    canvasy += field_11
    canvasy += field_12
    canvasy += field_13
    canvasy += field_14
    canvasy += field_15
    canvasy += field_16
    canvasy += field_17
    canvasy += field_18
    /*canvasy += apple*/

    canvasy.draw()
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