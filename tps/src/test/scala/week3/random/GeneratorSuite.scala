package week3.random

import common.TestSuite
import ConcreteGenerators._

class GeneratorSuite extends TestSuite {

  def test[T](g: Generator[T], numTimes: Int = 100)(test: T => Boolean): Unit = {
    // TODO: run test numTimes on values generated by g
    //       if the test fails on some value, print the value 
    //       and raise an exception
    for (_ <- 0 to numTimes)
      test(g.generate) shouldBe true
  }  
  
  
  test("Random1") {
    val counts = Array.fill(5)(0)
    val gen = choose(0,5)
    for (_ <- 0 to 100) {
      val a: Int = gen.generate
      a should be <(5)
      a should be >=(0)
      counts(a) += 1
    }
    counts.forall(_ > 10) should be(true)
  }
  
  
  test("Random2") {
    val counts = Array.fill(5)(0)
    val gen = oneFrom(0,1,2,3,4)
    for (i <- 0 to 100) {
      val a: Int = gen.generate
      a should be <(5)
      a should be >=(0)
      counts(a) += 1
    }
    counts.forall(_ > 10) should be(true)
  }
  
  
  test("Test max function on lists") {
    test(nonEmptyLists) { l =>
      val maxVal = l.max
      l.forall(i => i <= maxVal) && l.contains(maxVal) 
    } 
  }
  
  test("Test sortBy function on lists") {
    test(nonEmptyLists) { l =>
      val lSorted = l.sortBy(i => i)
      (lSorted zip lSorted.tail).forall(pair => pair._1 <= pair._2)
    } 
  }

  test("Test flatMap") {
    tst shouldBe true
  }
  
}