package DSL

import org.scalajs.dom
import scala.collection.mutable

object Keyboard {
  val keysDown: mutable.Map[Int, Boolean] = mutable.HashMap[Int, Boolean]()

  dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
    keysDown += e.keyCode -> true
  }, useCapture = false)

  dom.window.addEventListener("keyup", (e: dom.KeyboardEvent) => {
    keysDown -= e.keyCode
  }, useCapture = false)

  def isHoldingLeft: Boolean = keysDown.contains(37)
  def isHoldingUp: Boolean = keysDown.contains(38)
  def isHoldingRight: Boolean = keysDown.contains(39)
  def isHoldingDown: Boolean = keysDown.contains(40)
  def isHoldingS: Boolean = keysDown.contains(83)
  def isHoldingX: Boolean = keysDown.contains(88)
  def isHoldingK: Boolean = keysDown.contains(75)
  def isHoldingM: Boolean = keysDown.contains(188)
}
