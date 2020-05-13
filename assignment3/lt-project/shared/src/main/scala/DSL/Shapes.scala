package DSL


sealed trait Spot {
  type A <: Spot

  val position: Point = Point(0, 0)

  def change(property: CanvasElementModifier[A]): Unit

  def move(p: Point): Unit = {
    position.x = p.x
    position.y = p.y
  }
}


trait SpotAttributes {
  var color = "black"
  var strokeWidth = 1

  def color(col: String): Unit = color = col

  def strokeWidth(n: Int): Unit = strokeWidth = n
}


case class Wall(override val position: Point, size: Int) extends Spot with SpotAttributes {
  type A = Wall

  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Apple(override val position: Point, size: Int) extends Spot with SpotAttributes {
  type A = Apple

  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Empty(override val position: Point, size: Int) extends Spot with SpotAttributes {
  type A = Empty


  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Snake(override val position: Point, size: Int) extends Spot with SpotAttributes {
  type A = Snake

  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Score(override val position: Point, size: Int, var score: Int, text: String) extends Spot with SpotAttributes {
  type A = Score
  var font = "20px Helvetica"
  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class ComposedSpot[T <: Spot](var l: Seq[T]) extends Spot {
  type A = T

  def foreach[B](f: Spot => B): Unit = {
    if (l.nonEmpty) {
      f(l.head)
      l.tail.foreach(f)
    }
  }

  def apply(i: Int): Spot = {
    if (i < 0) {
      throw new IndexOutOfBoundsException
    } else if (i == 0) {
      l.head
    } else {
      l.tail(i - 1)
    }
  }

  def prepend(sb: A): Unit = {
    l = sb +: l
  }

  def containsPosition(p: Point): Boolean = {
    l.exists(_.position == p)
  }

  def head: Spot = {
    l.head
  }

  def last: Spot = {
    l.last
  }

  def size: Int = {
    l.length
  }

  def remove(p: Point): Unit = {
    l = l.filterNot(_.position == p)
  }

  override def change(property: CanvasElementModifier[A]): Unit = {
    l.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
  }

  override def move(p: Point): Unit = {
    if (l.nonEmpty) {
      val newPosition = Point(l.head.position.x, l.head.position.y)
      l.head.move(p)
      ComposedSpot(l.tail).move(newPosition)
    }
  }
}


object Extends {
  implicit class shapeArrayExtend[T <: Spot](s: Array[T]) {
    type A = T

    def change(property: CanvasElementModifier[A]): Unit = {
      s.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
    }
  }
}


case class Grid(width: Int, height: Int, wall_top: Array[Wall],
                wall_bottom: Array[Wall], wall_right: Array[Wall],
                wall_left: Array[Wall], field: Array[Array[Empty]]) {

  val spots: Array[Array[Spot]] = Array.ofDim[Spot](height, width)

  for (i <- 0 until height; j <- 0 until width) {
    if (i == 0) {
      spots(i)(j) = wall_top(j)
    } else if (i == height - 1) {
      spots(i)(j) = wall_bottom(j)
    } else if (j == 0) {
      spots(i)(j) = wall_right(i-1)
    } else if (j == width - 1) {
      spots(i)(j) = wall_left(i-1)
    } else {
      spots(i)(j) = field(i-1)(j-1)
    }
  }
}


case class Point(var x: Double, var y: Double){
  def +(other: Point): Point = Point(x + other.x, y + other.y)
  def -(other: Point): Point = Point(x - other.x, y - other.y)
  def <(other: Point): Boolean = x < other.x && y < other.y
  def >(other: Point): Boolean = x > other.x && y > other.y
  def ==(other: Point): Boolean = x == other.x && y == other.y
}