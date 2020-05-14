package DSL
import scala.scalajs.js
import js.Dynamic.{ global => g }
import org.scalajs.dom
import scala.collection.mutable
import scala.collection.mutable.HashMap

object Keyboard {
  val keysDown = mutable.HashMap[Int, Boolean]()

  dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
    keysDown += e.keyCode -> true
  }, useCapture = false)

  dom.window.addEventListener("keyup", (e: dom.KeyboardEvent) => {
    keysDown -= e.keyCode
  }, useCapture = false)

  def isHoldingLeft = keysDown.contains(37)
  def isHoldingUp = keysDown.contains(38)
  def isHoldingRight = keysDown.contains(39)
  def isHoldingDown = keysDown.contains(40)
}
