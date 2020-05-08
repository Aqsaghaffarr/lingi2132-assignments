package DSLDemo

import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer

class Canvasy(canvas: html.Canvas) {

  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  var shapes = new ArrayBuffer[Shape]()

  def += (shape: Any) : Unit = {
    shape match {
      case r : Array[Rectangle] => r.foreach(s => shapes.append(s))
      case c : Array[Circle] => c.foreach(s => shapes.append(s))
      case _ => throw new UnsupportedOperationException
    }
  }

  def draw(): Unit = {
    shapes.foreach(sh =>
      sh match {
        case r: Rectangle =>
          ctx.strokeStyle = r.color
          ctx.lineWidth = r.strokeWidth
          ctx.strokeRect(r.x, r.y, r.width, r.height)
        case c: Circle =>
          ctx.strokeStyle = c.color
          ctx.lineWidth = c.strokeWidth
          ctx.beginPath()
          ctx.arc(c.x, c.y, c.radius, 0, Math.PI * 2)
          ctx.stroke()
        case _ => throw new UnsupportedOperationException
      }
    )
  }
}