package week2.control

import org.scalatest._
import week2.control.Control._

class ControlSuite extends FlatSpec with Matchers {

  "Control" should "work" in {
    var nbErr = 0
    loop(1 to 6) { i =>
      if (i % 2  == 0) println(i)
      else throw new Exception("odd number")
    } onException {
      nbErr += 1
    }
    
    nbErr should be(3)
    
  }
  


}