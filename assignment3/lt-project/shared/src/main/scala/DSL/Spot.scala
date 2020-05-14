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

  def map(f: Spot => Spot): ComposedSpot[Spot] = ComposedSpot(l.map(f))

  def flatMap(f: Spot => Iterable[Spot]): ComposedSpot[Spot] = ComposedSpot(l.flatMap(f))

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

  def prepend(sb: A): Unit = l = sb +: l

  def contains(p: Point): Boolean = l.map(_.position).contains(p)

  def head: Spot = l.head

  def last: Spot = l.last

  def size: Int = l.length

  def remove(p: Point): Unit = l = l.filterNot(_.position == p)

  override def change(property: CanvasElementModifier[A]): Unit = {
    l.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
  }

  override def move(p: Point): Unit = {
    var oldPosition: Point = p
    for (s <- l) {
      val newPosition: Point = Point(s.position.x, s.position.y)
      s.move(oldPosition)
      oldPosition = newPosition
    }
  }
}


case class ComposedSpot2D[T <: Spot](spots: Array[Array[T]]) extends Spot {
  type A = T

  case class ArrayMapper(i: Int) {
    def apply(j: Int): Spot = spots(i)(j)

    def update(j: Int, v: T): Unit = spots(i)(j) = v

    def indices: Range = spots(i).indices
  }

  def apply(i: Int): ArrayMapper = ArrayMapper(i)

  def indices: Range = spots.indices

  override def change(property: CanvasElementModifier[A]): Unit = {
    spots.foreach(_.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]])))
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