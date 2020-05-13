package DSL


trait CanvasElementModifier[-ApplyOn <: Spot] {
  def change(x: ApplyOn): Unit
}


case class Color(col: String) extends CanvasElementModifier[Spot] {
  override def change(x: Spot): Unit = {
    x match {
      case w: Wall => w.color(col)
      case a: Apple => a.color(col)
      case e: Empty => e.color(col)
      case sn: Snake => sn.color(col)
      case _ => throw new UnsupportedOperationException
    }
  }
}


case class StrokeWidth(n: Int) extends CanvasElementModifier[Spot] {
  override def change(x: Spot): Unit = {
    x match {
      case w: Wall => w.strokeWidth(n)
      case a: Apple => a.strokeWidth(n)
      case e: Empty => e.strokeWidth(n)
      case sn: Snake => sn.strokeWidth(n)
      case _ => throw new UnsupportedOperationException
    }
  }
}

case class Font(font: String) extends CanvasElementModifier[Score] {
  override def change(x: Score): Unit = x.font = font
}
