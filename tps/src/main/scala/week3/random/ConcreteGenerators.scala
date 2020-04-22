package week3.random

import scala.collection.mutable.ListBuffer

object ConcreteGenerators {

  val integers = new Generator[Int]() {
    val rand = new scala.util.Random(0)
    def generate = rand.nextInt()
  }

  // Generator for random booleans
  val booleans = for (v <- integers) yield v > 0

  // TODO: write a generator for random pairs of Int's (hint, do not extend Generator, use for loops).
  // Try to formulate the same using only map/flatMaps)
  val pairs: Generator[(Int, Int)] = for (i <- integers; j <- integers) yield (i, j)

  // TODO create a generator of uniform random numbers between lo and hi (not included) (hint: use only for loops)
  def choose(lo: Int, hi: Int): Generator[Int] = for (i <- integers) yield Math.abs(i) % (hi - lo) + lo

  // TODO create a generator selecting randomly among a number of xs (hint: use only for loops)
  def oneFrom[T](xs: T*): Generator[T] = for (i <- choose(0, xs.length)) yield xs(i)

  // A generator that always generate the same value x
  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate: T = x
  }

  // TODO: create a generator generating empty lists (hint: user single)
  def emptyLists: Generator[List[Nothing]] = single(List())

  // TODO:: create a generator generating random (non empty) lists of Int's (use for loops)
  def nonEmptyLists: Generator[List[Int]] = new Generator[List[Int]] {
    override def generate: List[Int] = {
      val l = ListBuffer(integers.generate)
      while (booleans.generate)
        l :+ integers.generate
      l.toList
    }
  }

  // TODO:: create a generator generating random lists, (empty or not empty)
  def lists: Generator[List[Int]] = new Generator[List[Int]] {
    override def generate: List[Int] = if (booleans.generate) nonEmptyLists.generate else emptyLists.generate
  }

  // Compare map to flatMap
  def tst: Boolean = {
    val g1 = new Generator[List[Int]]() {
      def generate = List(1, 2)
    }
    val g2 = new Generator[List[Int]]() {
      def generate = List(1, 2)
    }
    val a = g1.map(x => (x, x))
    val b = g2.flatMap(x => g2.unit((x, x)))
    var c = true
    for (_ <- 0 to 100) {
      val x = a.generate
      val y = b.generate
      c &= x == y
    }
    c
  }
}