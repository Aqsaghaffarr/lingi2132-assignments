package week2.stream

import common.TestSuite
import week2.stream.StreamUtils._

class StreamSuite extends TestSuite {

  test("Filter") {
    val str = streamFrom(5)
    val str5 = str.take(5).filter(_ < 7)

    for (i <- 0 until 1) {
      str5(i) should be(5+i)
    }
    intercept[NoSuchElementException] {
      str5(2)
    }
  }
  
  test("Take") {
    val str = streamFrom(5)
    val str5 = str.take(5)
    
    for (i <- 0 until 5) {
      str5(i) should be(5+i)
    }
    intercept[NoSuchElementException] {
      str5(5)
    }    
  }

  test("Foreach") {
    val str = streamFrom(5)
    val str5 = str.take(5)
    str5.foreach(println)
  }

  test("Sum with foreach") {
    val str = streamFrom(1)
    val str5 = str.take(5)
    var sum = 0
    str5.foreach( sum += _ )
    sum should be(15)
  }
  
  
  test("Fibonacci") {
    val fib = fibonacci(0, 1)
    fib(0) should be(0)
    fib(1) should be(1)
    fib(2) should be(1)
    fib(3) should be(2)
    fib(4) should be(3)
    fib(5) should be(5)
    fib(6) should be(8)
    
    
    fib.filter(_ > 100)(5) should be(1597)
    
  }
  
  // Add more tests
}