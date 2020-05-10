package DSL

sealed trait Spot {
  type A <: Spot
  def change(property: CanvasElementModifier[A]): Unit
}

sealed trait SpotAttributes {

  var color = "black"
  var strokeWidth = 1

  def color(col: String): Unit = color = col

  def strokeWidth(n: Int): Unit = strokeWidth = n
}

case class Wall(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  type A = Wall
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}

case class Apple(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  type A = Apple
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}

case class Empty(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  type A = Empty
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Snake(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  type A = Snake
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


/*case class Point(x: Double, y: Double){
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
  def <(other: Point) = x < other.x && y < other.y
  def >(other: Point) = x > other.x && y > other.y
  def /(value: Double) = Point(x / value, y / value)
}*/

object Extends {

  implicit class shapeArrayExtend[T <: Spot](s: Array[T]) {

    type A = T

    def change(property: CanvasElementModifier[A]): Unit = {
      s.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
    }
  }
}