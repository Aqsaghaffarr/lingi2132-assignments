package week3.random

object ConcreteGenerators {

  val integers = new Generator[Int]() {
    val rand = new scala.util.Random()
    def generate = rand.nextInt()
  }

  // Generator for random booleans
  val booleans = for (v <- integers) yield v > 0

  // TODO: write a generator for random pairs of Int's (hint, do not extend Generator, use for loops).
  // Try to formulate the same using only map/flatMaps)
  val pairs: Generator[(Int, Int)] = ???

  // TODO create a generator of uniform random numbers between lo and hi (not included) (hint: use only for loops)
  def choose(lo: Int, hi: Int): Generator[Int] = ???

  // TODO create a generator selecting randomly among a number of xs (hint: use only for loops)
  def oneFrom[T](xs: T*): Generator[T] = ???

  // A generator that always generate the same value x
  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate = x
  }

  // TODO: create a generator generating empty lists (hint: user single)
  def emptyLists = ???

  // TODO:: create a generator generating random (non empty) lists of Int's (use for loops)
  def nonEmptyLists: Generator[List[Int]] = ???

  // TODO:: create a generator generating random lists, (empty or not empty)
  def lists: Generator[List[Int]] = ???

}