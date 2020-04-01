package week1.polynom

import common.TestSuite

class PolynomSuite extends TestSuite {
  
  test("Addition of Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2)
    val p3 = p1 + p2
    p3.maxDegree shouldBe 2
    p3.minDegree shouldBe 0
    p3(2) shouldBe 3
  }

  test("Equality on Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2, 3)
    val p3 = Polynom(1, 2, 1)
    p1 == p2 shouldBe true 
    p1 == p3 shouldBe false 
  }
  
  test("Substraction of Polynoms") {
    val p1 = Polynom(1, 2, 3)
    val p2 = Polynom(1, 2, 3)
    val p3 = p1 - p2
    p3.maxDegree shouldBe 0
    p3.minDegree shouldBe 0
    p3(0) shouldBe 0
    intercept[Exception] { p3(-1) }
  }

  test("Product of Polynoms") {
    // (1 + 2x + 5x^2) * (4x)
    val p1 = Polynom(1, 2, 5)
    val p2 = Polynom(0, 4)
    val p3 = p1 * p2
    p3.maxDegree shouldBe 3
    p3.minDegree shouldBe 1
    p3(0) shouldBe 0
    p3(1) shouldBe 4
    p3(2) shouldBe 8
    p3(3) shouldBe 20
    p3(4) shouldBe 0
  }

  test("Addition of multiple Polynoms") {
    val p1 = Polynom(1)
    val p2 = Polynom(0, 2)
    val p3 = Polynom(0, 0, 3)
    val p4 = Polynom.sum(Iterable(p1, p2, p3))
    p4.maxDegree shouldBe 2
    p4.minDegree shouldBe 0
    p4(0) shouldBe 1
    p4(1) shouldBe 2
    p4(2) shouldBe 3
  }
  
  // Add more tests
}