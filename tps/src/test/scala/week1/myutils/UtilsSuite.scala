package week1.myutils

import common.TestSuite

class UtilsSuite extends TestSuite {

  import week1.myutils.Utils._

  // Add more tests 
  
  test("filterOut should work") {
    filterOut(List(1, 2, 5), _ % 2 == 0) shouldBe List(1, 5)
  }

  test("zipAndFilter should work") {
    zipAndFilter(List(1, 2, 3), List(3, 2, 2), (a: Int, b: Int) => a + b == 4) shouldBe List((1, 3), (2, 2))
  }

  test("trueForAll should work") {
    trueForAll(List(1, 2, 3), _ > 0) shouldBe true
    trueForAll(List(1, 2, 3), _ < 3) shouldBe false
  }
  
  test("toLowerCase should work") {
    toLowerCase("ToTo") shouldBe "toto"  
  }

  test("flip should work") {
    flip(List(Apple, Orange, Banana)) shouldBe List(Orange, Apple, Banana)
  }
}