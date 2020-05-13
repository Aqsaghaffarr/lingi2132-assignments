package DSL


import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}


class Canvasy(canvas: html.Canvas) {

  val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def drawGrid(grid: Grid): Unit = {
    for (i <- grid.spots.indices; j <- grid.spots(0).indices) {
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
        case s: SnakeBlock =>
          ctx.fillStyle = s.color
          ctx.lineWidth = s.strokeWidth
          ctx.fillRect(s.point.x * s.size, s.point.y * s.size, s.size, s.size)
        case _ => throw new UnsupportedOperationException
      }
    }
  }


}