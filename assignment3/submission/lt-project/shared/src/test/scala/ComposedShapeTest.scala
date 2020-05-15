import DSLDemo._

import common.Common._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ComposedShapeTest extends AnyFunSuite with Matchers {

  Random.setSeed(947693)

  test("moving a group of shapes up") {
    val l = newRectangleList()
    val g = ComposedShape(l)
    val offSet = Random.nextInt(20)
    val expected = l.map(_.y - offSet)
    g moveY(-offSet)
    for (i <- l.indices)
      g(i).asInstanceOf[Rectangle].y shouldBe expected(i)
  }

  test("foreach simple") {
    val rl = newRectangleList()
    val cl = newCircleList()
    val g = ComposedShape(rl ++ cl)
    var counter = 0
    g.foreach(x => counter += 1)
    counter shouldBe rl.length + cl.length
  }

  test("map") {
    val rl = newRectangleList(10)
    val expected = List.tabulate(rl.length)(i => Circle(rl(i).x, rl(i).y, 20))
    val g = ComposedShape(rl)
    val newG = g.map(r => Circle(r.asInstanceOf[Rectangle].x, r.asInstanceOf[Rectangle].y, 20))
    for (i <- rl.indices) {
      g(i) shouldBe rl(i)
      newG(i) shouldBe expected(i)
      assert(g(i) != newG(i))
    }
  }

  test("change width on ComposedShape of rectangles") {
    val l = newRectangleList(0, 0, 10, 20, 10)
    val g = ComposedShape(l)
    g change Width(40)
    for (i <- l.indices) {
      assert(g(i).asInstanceOf[Rectangle].width != 10)
    }
  }

  test("change height on ComposedShape of rectangles") {
    val l = newRectangleList(0, 0, 10, 20, 10)
    val g = ComposedShape(l)
    g change Height(40)
    for (i <- l.indices) {
      assert(g(i).asInstanceOf[Rectangle].width != 20)
    }
  }

  test("change radius on ComposedShape of rectangles") {
    val l = newRectangleList()
    val g = ComposedShape(l)
    assertTypeError("g change Radius(40)")
  }

  test("change property on ComposedShape of rectangle and circle") {
    val r = Rectangle(10, 20, 40, 40)
    val c = Circle(0, 0, 10)
    val g = ComposedShape(List(r, c))
    assertTypeError("g change Radius(30)")
    assertTypeError("g change Height(30)")
    assertTypeError("g change Width(30)")
  }
}
