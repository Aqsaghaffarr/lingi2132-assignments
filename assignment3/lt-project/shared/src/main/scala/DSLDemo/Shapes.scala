package DSLDemo

import scala.collection.Iterable


sealed trait Shape {
  type A <: Shape

  def and(s: Shape): ComposedShape[Shape] = ComposedShape(this :: s :: Nil)

  def change(property: CanvasElementModifier[A]): Unit

  def moveX(v: Int): Unit = moveX(v)

  def moveY(v: Int): Unit = moveY(v)
}


sealed trait ShapeAttributes {
  var color = "red"
  var strokeWidth = 1

  def color(col: String): Unit = color = col

  def strokeWidth(n: Int): Unit = strokeWidth = n
}


case class ComposedShape[T <: Shape](var l: List[T]) extends Shape {
  type A = T

  def map(f: Shape => Shape): ComposedShape[Shape] = ComposedShape(l.map(f))

  def flatMap(f: Shape => Iterable[Shape]): ComposedShape[Shape] = ComposedShape(l.flatMap(f))

  def foreach[B](f: Shape => B): Unit = {
    if (l.nonEmpty) {
      f(l.head)
      l.tail.foreach(f)
    }
  }

  def apply(i: Int): Shape = {
    if (i < 0)
      throw new IndexOutOfBoundsException
    else if (i == 0)
      l.head
    else l.tail(i - 1)
  }

  override def moveX(v: Int): Unit = l.foreach(_.moveX(v))

  override def moveY(v: Int): Unit = l.foreach(_.moveY(v))

  override def change(property: CanvasElementModifier[A]): Unit = l.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
}


case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends Shape with ShapeAttributes {
  type A = Rectangle

  override def moveX(v: Int): Unit = x += v

  override def moveY(v: Int): Unit = y += v

  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)
}


case class Circle(var x: Int, var y: Int, var radius: Int) extends Shape with ShapeAttributes {
  type A = Circle

  override def moveX(v: Int): Unit = x += v

  override def moveY(v: Int): Unit = y += v

  override def change(property: CanvasElementModifier[A]): Unit = property.change(this)

}

object Extends {

  implicit class shapeArrayExtend[T <: Shape](s: Array[T]) {

    type A = T

    def moveX(i: Int): Unit = {
      s.foreach(_.moveX(i))
    }

    def moveY(i: Int): Unit = {
      s.foreach(_.moveY(i))
    }

    def change(property: CanvasElementModifier[A]): Unit = {
      s.foreach(x => x.change(property.asInstanceOf[CanvasElementModifier[x.A]]))
    }

    implicit def and[U <: Shape](shape: Array[U]): ComposedShape[Shape] = {
      ComposedShape(s.toList) and ComposedShape(shape.toList)
    }
  }
}