package webapp

import DSL.GameLauncher._

object Main {
  def main(args: Array[String]): Unit = {
    play (SnakeGame) withMessages
      ("Arrow keys to move, space to play", "Press space to restart")

    /*
    play (PongGame) withMessages
      ("s, x, k and comma to move; space to play", "Press space to restart")
     */
  }
}