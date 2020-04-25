package DSLDemo

import java.lang.invoke.WrongMethodTypeException

trait CanvasElementModifier[-ApplyOn <: Shape] {
  def change(x: ApplyOn): Unit
}


case class Color(c: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case r: Rectangle  => r.color(c)
      case t: Circle => t.color(c)
    }
  }
}


case class StrokeWidth(n: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case r: Rectangle => r.strokeWidth(n)
      case t: Circle => t.strokeWidth(n)
    }
  }
}


case class Radius(n: Int) extends CanvasElementModifier[Circle] {
  override def change(x: Circle): Unit = {
    x.radius(n)
  }
}


case class Width(n: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = {
    x.width(n)
  }
}


case class Height(n: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = {
    x.height(n)
  }
}