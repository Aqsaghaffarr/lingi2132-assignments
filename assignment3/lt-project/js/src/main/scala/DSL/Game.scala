package DSL

// Class for games, to enable handling multiple games in launcher.
abstract class Game {
  def useGameDSL(canvasy: Canvasy, ending: String): () => Unit = ???

  var timeout: Int

  var alive: Boolean

  var spotSize: Int
}
