package DSL

sealed trait Spot {
  def change(property: CanvasElementModifier[Spot]): Unit
}

sealed trait SpotAttributes {
  var color = "black"
  var strokeWidth = 1

  def color(col: String): Unit = color = col

  def strokeWidth(n: Int): Unit = strokeWidth = n
}

case class Wall(point: Point, val size: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Apple(point: Point, var size: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Empty(point: Point, var size: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Snake(var point: Point, var size: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
  def move(p: Point): Unit = {
    point.x = p.x
    point.y = p.y
  }
}

object Extends {

  implicit class shapeArrayExtend[T <: Spot](s: Array[T]) {

    type A = T

    def change(property: CanvasElementModifier[A]): Unit = {
      s.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[Spot]]))
    }
  }
}

case class Grid(val width: Int, val height: Int, wall_top: Array[Wall], wall_bottom: Array[Wall], wall_right: Array[Wall],
           wall_left: Array[Wall], field: Array[Array[Empty]]){
  val spots: Array[Array[Spot]] = Array.ofDim[Spot](width, height)
  for(i <- 0 until height){
    for(j <- 0 until width){
      if(i == 0){
        spots(i)(j) = wall_top(j)
      }
      else if(i == height - 1){
        spots(i)(j) = wall_bottom(j)
      }
      else if(j == 0){
        spots(i)(j) = wall_right(i-1)
      }
      else if(j == width - 1){
        spots(i)(j) = wall_left(i-1)
      }
      else{
        spots(i)(j) = field(i-1)(j-1)
      }
    }
  }

}

case class Point(var x: Double, var y: Double){
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
  def <(other: Point) = x < other.x && y < other.y
  def >(other: Point) = x > other.x && y > other.y
  def /(value: Double) = Point(x / value, y / value)
}