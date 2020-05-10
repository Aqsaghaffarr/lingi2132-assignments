package DSL

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}

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

  def draw(): Unit = {
    spots.foreach {
      case w: Wall =>
        ctx.strokeStyle = w.color
        ctx.lineWidth = w.strokeWidth
        ctx.fillRect(w.x, w.y, w.width, w.height)
        ctx.fillStyle = w.color
      case a: Apple =>
        ctx.strokeStyle = a.color
        ctx.lineWidth = a.strokeWidth
        ctx.strokeRect(a.x, a.y, a.width, a.height)
        ctx.fillStyle = a.color
      case e: Empty =>
        ctx.strokeStyle = e.color
        ctx.lineWidth = e.strokeWidth
        ctx.strokeRect(e.x, e.y, e.width, e.height)
        ctx.fillStyle = e.color
      case s: Snake =>
        ctx.strokeStyle = s.color
        ctx.lineWidth = s.strokeWidth
        ctx.strokeRect(s.x, s.y, s.width, s.height)
        ctx.fillStyle = s.color
      case _ => throw new UnsupportedOperationException
    }
  }
}