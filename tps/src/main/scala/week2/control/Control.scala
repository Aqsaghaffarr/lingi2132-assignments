package week2.control

object Control {

  /*

  TODO:

  Create Scala constructs to make this new control block work

  loop(1 to 5) { i =>

  } onException {

  }


   This code iterates over a Scala range,
   i is the current value if iteration passed as argument to the closure.
   If an exception is thrown, it is caught and the the onException block is executed
   before continuing on next value of the range

   Hint: Don't hesitate to create intermediate classes

  */

  def loop(r: Range)(body: => Int => Unit): Loop = {
    new Loop(r, body)
  }

  class Loop(r: Range, body: => Int => Unit) {
    def onException(p: => Unit): Unit = {
      for (i <- r) {
        try {
          body(i)
        } catch {
          case _: Throwable => p
        }
      }
    }
  }
}