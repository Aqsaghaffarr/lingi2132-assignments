import DSLDemo._

import common.Common._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class ElementaryShapeTest extends AnyFunSuite with Matchers {

  Random.setSeed(183945)

  test("move rectangle up") {
    val offSet = Random.nextInt(20)
    val rectangle = newRectangle()
    val expected = rectangle.y - offSet
    rectangle moveY(-offSet)
    rectangle.y shouldBe expected
  }

  test("move circle left") {
    val offSet = Random.nextInt(20)
    val circle = newCircle()
    val expected = circle.x - offSet
    circle moveX(-offSet)
    circle.x shouldBe expected
  }

  test("change color of rectangle via keyword") {
    val rectangle = newRectangle()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    rectangle change Color(newColor)
    rectangle.color shouldBe newColor
  }

  test("change color of circle via property") {
    val circle = newCircle()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    circle change Color(newColor)
    circle.color shouldBe newColor
  }

  test("change radius in rectangle") {
    val offSet = Random.nextInt(20)
    val rectangle = newRectangle()
    assertTypeError("rectangle change Radius(40)")
  }
}
