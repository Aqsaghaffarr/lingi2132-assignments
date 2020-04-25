package DSLDemo

import java.awt.Canvas

import scala.collection.Iterable


sealed trait Shape {
  type A <: Shape

  def and(s: Shape): ComposedShape = {
    val list = this :: s :: Nil
    ComposedShape(list)
  }

  def change(property: CanvasElementModifier[A]): Unit = ???

  def moveX(v: Int): Unit = ???

  def moveY(v: Int): Unit = ???
}


sealed trait ShapeAttributes {
  var color: String = "red"
  var strokeWidth: Int = 1

  def color(col: String): Unit = {
    color = col
  }

  def strokeWidth(n: Int): Unit = {
    strokeWidth = n
  }

  // Add more attributes here
}


case class ComposedShape(var l: List[Shape]) extends Shape {
  def map(f: Shape => Shape): ComposedShape = {
    ComposedShape(l.map(f))
  }

  def flatMap(f: Shape => Iterable[Shape]): ComposedShape = {
    ComposedShape(l.flatMap(f))
  }

  def foreach[B](f: Shape => B): Unit = {
    if (l.nonEmpty) {
      f(l.head)
      l.tail.foreach(f)
    }
  }

  def apply(i: Int): Shape = {
    if (i < 0) {
      throw new NoSuchElementException("Negative indices are not allowed.")
    } else if (i == 0) {
      l.head
    } else {
      l.tail(i-1)
    }
  }

  override def moveX(v: Int): Unit = {
    l.foreach(s => s.moveX(v))
  }

  override def moveY(v: Int): Unit = {
    l.foreach(s => s.moveY(v))
  }

  def change(property: Radius): Unit = {
    for (x <- l) {
      x match {
        case c: Circle => c.change(property)
      }
    }
  }

  def change(property: Width): Unit = {
    for (x <- l) {
      x match {
        case r: Rectangle => r.change(property)
      }
    }
  }

  def change(property: Height): Unit = {
    for (x <- l) {
      x match {
        case r: Rectangle => r.change(property)
      }
    }
  }

  override def change(property: CanvasElementModifier[A]): Unit = {
    foreach(_ => change(property))
  }
}


case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends Shape with ShapeAttributes {
  type A = Rectangle

  override def moveX(v: Int): Unit = {
    x += v
  }

  override def moveY(v: Int): Unit = {
    y += v
  }

  override def change(property: CanvasElementModifier[A]): Unit = {
    property match {
      case _: Color | _: Width | _: Height => property.change(this)
    }
  }

  def width(n: Int): Unit = {
    width = n
  }

  def height(n: Int): Unit = {
    height = n
  }
}


case class Circle(var x: Int, var y: Int, var radius: Int) extends Shape with ShapeAttributes {
  type A = Circle

  override def moveX(v: Int): Unit = {
    x += v
  }

  override def moveY(v: Int): Unit = {
    y += v
  }

  override def change(property: CanvasElementModifier[A]): Unit = {
    property match {
      case _: Color | _: Radius => property.change(this)
    }
  }

  def radius(n: Int): Unit = {
    radius = n
  }
}