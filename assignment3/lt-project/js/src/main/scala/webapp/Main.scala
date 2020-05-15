package webapp

//import DSLDemo._
import DSL.Extends._
import DSL.{Color, _}
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{document, html}
//import DSLDemo.Extends._
import scala.util.Random

object Main {

  def createCanvas(width: Int, height: Int): html.Canvas = {
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    canvas.width = width
    canvas.height = height
    canvas
  }

  def main(args: Array[String]): Unit = {
    // Define canvas.
    var canvas = createCanvas(500, 500)

    var game = SnakeGame
    var gameLoop = game.useGameDSL(canvas)

    val reset = () => {
      if (Keyboard.keysDown.contains(KeyCode.Space)){
        val w = canvas.width
        val h = canvas.height
        document.body.removeChild(canvas)
        game.alive = true
        game = SnakeGame
        canvas = createCanvas(w, h)
        gameLoop = game.useGameDSL(canvas)
        gameLoop
      }
    }

    val decision = () => if (game.alive) {
      gameLoop()
    } else {
      reset()
    }

    dom.window.setInterval(decision, game.timeout)

  }
}