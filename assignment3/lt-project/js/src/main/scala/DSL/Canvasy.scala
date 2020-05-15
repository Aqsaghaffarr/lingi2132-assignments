package DSL


import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}


class Canvasy(canvas: html.Canvas) {

  val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  // Draw grid (2d array).
  // Ball comes last because otherwise the shape is fucked up.
  def drawGrid(grid: Grid): Unit = {
    var ball: Ball = null

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
        case b: Ball =>
          ball = b
        case p: Player =>
          ctx.fillStyle = p.color
          ctx.lineWidth = p.strokeWidth
          ctx.fillRect(p.position.x * p.size, p.position.y * p.size, p.size, p.size)
        case _ => throw new UnsupportedOperationException
      }
    }

    if (ball != null) {
      ctx.fillStyle = ball.color
      ctx.lineWidth = ball.strokeWidth
      ctx.beginPath()
      ctx.arc(ball.position.x * ball.size, ball.position.y * ball.size, ball.size, 0, Math.PI * 2)
      ctx.fill()
    }
  }

  // Show a message object.
  def showMessage(message: Message): Unit = {
    val text = message.text
    ctx.font = message.font
    ctx.textAlign = "center"
    ctx.fillText(text, message.position.x, message.position.y)
  }

  // Show a score object.
  def showScore(score: Score): Unit = {
    ctx.font = score.font
    ctx.textAlign = "center"
    ctx.fillText(score.score.toString, score.position.x, score.position.y)
  }
}