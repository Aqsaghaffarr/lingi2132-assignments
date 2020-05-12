package DSL

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}
import scala.scalajs.js
import js.Dynamic.{ global => g }
import org.scalajs.dom
import scala.collection.mutable

import scala.collection.mutable.ArrayBuffer

class Canvasy(canvas: html.Canvas) {

  val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  var spots = new ArrayBuffer[Spot]()

  def += (spot: Any) : Unit = {
    spot match {
      case w : Array[Wall] => w.foreach(s => spots.append(s))
      case a : Array[Apple] => a.foreach(s => spots.append(s))
      case e : Array[Empty] => e.foreach(s => spots.append(s))
      case sn : Array[Snake] => sn.foreach(s => spots.append(s))
      case _ => throw new UnsupportedOperationException
    }
  }

  def empty() : Unit = {
    spots.clear()
  }


  def drawGrid(grid : Grid): Unit = {
    for {
      i <- 0 until grid.spots.length
      j <- 0 until grid.spots(0).length
    } {
      grid.spots(i)(j) match {
        case w: Wall =>
          ctx.fillStyle = w.color
          ctx.lineWidth = w.strokeWidth
          ctx.fillRect(w.point.x * w.size, w.point.y * w.size, w.size, w.size)
        case a: Apple =>
          ctx.fillStyle = a.color
          ctx.lineWidth = a.strokeWidth
          ctx.fillRect(a.point.x * a.size, a.point.y * a.size, a.size, a.size)
        case e: Empty =>
          ctx.fillStyle = e.color
          ctx.lineWidth = e.strokeWidth
          ctx.fillRect(e.point.x * e.size, e.point.y * e.size, e.size, e.size)
        case s: Snake =>
          ctx.fillStyle = s.color
          ctx.lineWidth = s.strokeWidth
          ctx.fillRect(s.point.x * s.size, s.point.y * s.size, s.size, s.size)
      }
    }
  }


}