package DSL

import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.{document, html}

// Enable custom play loop.
object GameLauncher {
  def play(game: Game): Play = {
    new Play(game)
  }

  // Create a canvas with specified dimensions.
  def createCanvas(width: Int, height: Int): html.Canvas = {
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    canvas.width = width
    canvas.height = height
    canvas
  }

  class Play(game: Game) {
    var canvas: html.Canvas = createCanvas(500, 500)
    var canvasy = new Canvasy(canvas)

    // Game has messages, show them.
    def withMessages(controls: String, ending: String): Unit = {
      var gameLoop: () => Unit = null
      var hasSeenCommands: Boolean = false

      // Until users presses space, show commands.
      val keepWaiting: () => Any = () => {
        document.body.removeChild(canvas)
        canvas = createCanvas(500, 500)
        canvasy = new Canvasy(canvas)
        if (Keyboard.keysDown.contains(KeyCode.Space)) {
          hasSeenCommands = true
          gameLoop = game.useGameDSL(canvasy, ending)
        } else {
          canvasy.showMessage(Message(Point(200, 200), game.spotSize, controls))
        }
      }

      // Reset function, needs to be called when the game ends to check for restart.
      val reset: () => Any = () => {
        if (Keyboard.keysDown.contains(KeyCode.Space)) {
          document.body.removeChild(canvas)
          game.alive = true
          canvas = createCanvas(500, 500)
          canvasy = new Canvasy(canvas)
          gameLoop = game.useGameDSL(canvasy, ending)
          gameLoop
        }
      }

      // Decide what to do next.
      val decision: () => Any = () =>
        if (!hasSeenCommands) keepWaiting()
        else if (game.alive) gameLoop()
        else reset()

      // Iterate at constant time intervals.
      dom.window.setInterval(decision, game.timeout)
    }
  }
}
