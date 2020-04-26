package DSLDemo

import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer

class Canvasy(canvas: html.Canvas) {

  var shapes = new ArrayBuffer[Shape]()

  def += (shape: Any) : Unit = {
    shape match {
      case r : Array[Rectangle] => r.foreach(s => shapes.append(s))
      case c : Array[Circle] => c.foreach(s => shapes.append(s))
    }
  }

  def draw(): Unit = ???
}