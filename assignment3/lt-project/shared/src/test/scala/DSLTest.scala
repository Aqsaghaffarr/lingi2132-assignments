import DSL._
import common.CommonDSL._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class DSLTest extends AnyFunSuite with Matchers {

  Random.setSeed(947693)

  test("Move spots") {
    val offSet = Point(2, 2)
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val expected = Point(2, 2)
    for (spot <- List(wall, empty, apple, snake)) {
      spot move offSet
      spot.position shouldBe expected
    }
  }

  test("Change color of spots via keyword") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    for (spot <- List(wall, empty, apple, snake)) {
      spot.color = newColor
      spot.color shouldBe newColor
    }
  }

  test("Change color of spots via property") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    for (spot <- List(wall, empty, apple, snake)) {
      spot change Color(newColor)
      spot.color shouldBe newColor
    }
  }

  test("Change strokeWidth of spots via keyword") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val newStrokeWidth = Random.nextInt(255)
    for (spot <- List(wall, empty, apple, snake)) {
      spot.strokeWidth = newStrokeWidth
      spot.strokeWidth shouldBe newStrokeWidth
    }
  }

  test("Change strokeWidth of spots via property") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val newStrokeWidth = Random.nextInt(255)
    for (spot <- List(wall, empty, apple, snake)) {
      spot change StrokeWidth(newStrokeWidth)
      spot.strokeWidth shouldBe newStrokeWidth
    }
  }

  test("Change color of spots via property on ComposedSpot") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val l = List(wall, empty, apple, snake)
    val g = ComposedSpot(l)
    val newColor = s"rgb ${Random.nextInt(255)} ${Random.nextInt(255)} ${Random.nextInt(255)}"
    g change Color(newColor)
    for (spot <- l) {
      spot.color shouldBe newColor
    }
  }

  test("Change strokeWidth of spots via property on ComposedSpot") {
    val wall = newWall()
    val empty = newEmpty()
    val apple = newApple()
    val snake = newSnake()
    val l = List(wall, empty, apple, snake)
    val g = ComposedSpot(l)
    val newStrokeWidth = Random.nextInt(255)
    g change StrokeWidth(newStrokeWidth)
    for (spot <- l) {
      spot.strokeWidth shouldBe newStrokeWidth
    }
  }

  test("Moving a snake") {
    val l = List(Snake(Point(1, 1), 10), Snake(Point(2, 2), 10))
    val g = ComposedSpot(l)
    val newHead: Point = Point(0, 0)
    val expected = List(Point(0, 0), Point(1, 1))
    g move newHead
    for (i <- l.indices)
      g(i).position shouldBe expected(i)
  }

  test("Simple foreach") {
    val sl = newSnakeList()
    val al = newAppleList()
    val g = ComposedSpot(sl ++ al)
    var counter = 0
    g.foreach(_ => counter += 1)
    counter shouldBe sl.length + al.length
  }

  test("Map on ComposedSpot") {
    val el = newEmptyList()
    val expected = List.tabulate(el.length)(i => Wall(Point(el(i).position.x, el(i).position.y), 20))
    val g = ComposedSpot(el)
    val newG = g.map(e => Wall(Point(e.asInstanceOf[Empty].position.x, e.asInstanceOf[Empty].position.y), 20))
    for (i <- el.indices) {
      g(i) shouldBe el(i)
      newG(i) shouldBe expected(i)
      assert(g(i) != newG(i))
    }
  }

  test("Prepend in ComposedSpot") {
    val l = newWallList()
    val elem = newWall()
    val eleml = elem +: l
    val g1 = ComposedSpot(l)
    g1 prepend elem
    val g2 = ComposedSpot(eleml)
    for (i <- eleml.indices) {
      g1(i) shouldBe g2(i)
    }
  }

  test("Head, last, and size of ComposedSpot") {
    val l = newWallList()
    val g = ComposedSpot(l)
    g.head shouldBe l.head
    g.last shouldBe l.last
    g.size shouldBe l.length
  }

  test("Contains and remove in ComposedSpot") {
    val oldl = newWallList()
    val l = oldl :+ newWall(Point(-1, -2), 10)
    val g = ComposedSpot(l)
    g.contains(Point(-1, -2)) shouldBe true
    g.contains(Point(-2, -1)) shouldBe false
    g.remove(Point(-1, -2))
    for (i <- oldl.indices) {
      g(i) shouldBe oldl(i)
    }
  }
}
