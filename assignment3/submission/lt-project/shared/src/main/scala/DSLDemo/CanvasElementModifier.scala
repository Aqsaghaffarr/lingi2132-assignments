package DSLDemo


trait CanvasElementModifier[-ApplyOn <: Shape] {
  def change(x: ApplyOn): Unit
}


case class Color(col: String) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case r: Rectangle => r.color(col)
      case c: Circle => c.color(col)
      case _ => throw new UnsupportedOperationException
    }
  }
}


case class StrokeWidth(n: Int) extends CanvasElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case r: Rectangle => r.strokeWidth(n)
      case c: Circle => c.strokeWidth(n)
      case _ => throw new UnsupportedOperationException
    }
  }
}


case class Radius(n: Int) extends CanvasElementModifier[Circle] {
  override def change(x: Circle): Unit = x.radius = n
}


case class Width(n: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.width = n
}


case class Height(n: Int) extends CanvasElementModifier[Rectangle] {
  override def change(x: Rectangle): Unit = x.height = n
}