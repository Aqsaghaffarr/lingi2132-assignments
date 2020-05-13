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
          ctx.fillRect(w.position.x * w.size, w.position.y * w.size, w.size, w.size)
        case a: Apple =>
          ctx.fillStyle = a.color
          ctx.lineWidth = a.strokeWidth
          ctx.fillRect(a.position.x * a.size, a.position.y * a.size, a.size, a.size)
        case e: Empty =>
          ctx.fillStyle = e.color
          ctx.lineWidth = e.strokeWidth
          ctx.fillRect(e.position.x * e.size, e.position.y * e.size, e.size, e.size)
        case s: Snake =>
          ctx.fillStyle = s.color
          ctx.lineWidth = s.strokeWidth
          ctx.fillRect(s.position.x * s.size, s.position.y * s.size, s.size, s.size)
        case _ => throw new UnsupportedOperationException
      }
    }
  }

  def showScore(score: Score): Unit = {
    val text = score.text + ": " + score.score.toString
    ctx.font = score.font
    ctx.fillText(text, score.position.x * score.size, score.position.y * score.size)
  }
}