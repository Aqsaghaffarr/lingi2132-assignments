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

case class Wall(val x: Int, val y: Int, val width: Int, val height: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Apple(var x: Int, var y: Int, var width: Int, var height: Int, var bonus: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Empty(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

case class Snake(var x: Int, var y: Int, var width: Int, var height: Int) extends Spot with SpotAttributes{
  override def change(property: CanvasElementModifier[Spot]): Unit = property.change(this)
}

class Grid(val width: Int, val height: Int, wall_top: Array[Wall], wall_bottom: Array[Wall], wall_rigth: Array[Wall],
           wall_left: Array[Wall], field: Array[Array[Empty]]){
  val spots: Array[Array[Spot]] = Array.ofDim[Spot](width, height)
  for(i <- 0 until width){
    for(j <- 0 until height){
      if(i == 0){
        spots(i)(j) = wall_top(j)
      }
      if(i == width - 1){
        spots(i)(j) = wall_bottom(j)
      }
      if(j == 0){
        spots(i)(j) = wall_rigth(i)
      }
      if(j == height - 1){
        spots(i)(j) = wall_left(i)
      }
      else{
        spots(i)(j) = field(i-1)(j-1)
      }
    }
  }

}



case class Point(x: Double, y: Double){
  def +(other: Point) = Point(x + other.x, y + other.y)
  def -(other: Point) = Point(x - other.x, y - other.y)
  def <(other: Point) = x < other.x && y < other.y
  def >(other: Point) = x > other.x && y > other.y
  def /(value: Double) = Point(x / value, y / value)
}

object Extends {

  implicit class shapeArrayExtend[T <: Spot](s: Array[T]) {

    type A = T

    def change(property: CanvasElementModifier[A]): Unit = {
      s.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[Spot]]))
    }
  }
}