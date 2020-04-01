package week1.factorial

import common.TestSuite

class MyMathSuite extends TestSuite {

  test("Factorial should throw an exception on negative input") {
    intercept[Exception] {
      MyMath.factorial(-5)
    }
  }
  
  test("Factorial of 0 should be 1") {
    MyMath.factorial(0) shouldBe 1
  }
  
  test("Factorial of 1 should be 1") {
    MyMath.factorial(1) shouldBe 1
  }
  
  test("Factorial should be correct") {
    MyMath.factorial(5) shouldBe 120
    MyMath.factorial(4) shouldBe 24
    MyMath.factorial(3) shouldBe 6
    MyMath.factorial(2) shouldBe 2
  }

  // Add some tests if necessary
}