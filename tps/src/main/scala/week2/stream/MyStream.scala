package week2.stream


abstract class MyStream {
    import MyStream._

    def isEmpty: Boolean
    def head: Int
    def tail: MyStream

    def apply(i: Int): Int = {
      if (i < 0) throw new NoSuchElementException("")
      else if (i == 0) head
      else tail(i-1)
    }
    def filter(p: Int => Boolean): MyStream = {
      if (!isEmpty) {
        if (p(head)) {
          cons(head, tail.filter(p))
        } else {
          tail.filter(p)
        }
      } else {
        empty
      }
    }
    
    /**
     * take the n first entry of this stream to create a finite stream of at most n entry
     */
    def take(n: Int): MyStream = {
    	if (n == 0 || isEmpty) {
        empty
      } else {
        cons(head, tail.take(n - 1))
      }
    }
    
    /**
     * apply the f function on each entry of the stream
     * this allow us to use iterate on MyStream object using for loops
     */
    def foreach[U](f: Int => U): Unit = {
      if (!isEmpty) {
        f(head)
        tail.foreach(f)
      }
    }
}

object MyStream {
    def cons(hd: Int, tl: => MyStream): MyStream = new MyStream {
      def isEmpty = false
      def head: Int = hd
      def tail: MyStream = tl

    }
    val empty = new MyStream {
      def isEmpty = true
      def head = throw new NoSuchElementException("empty.head")
      def tail = throw new NoSuchElementException("empty.tail")
    }
}

object StreamUtils {
  
  import MyStream._
  
  def streamFrom(from: Int): MyStream = cons(from, streamFrom(from + 1))
  
  /**
   * Return an infinite fibonacci stream: a, b, a+b, b+a+b, a+b+b+a+b, b+a+b+a+b+b+a+b, ...
   */
  def fibonacci(a: Int, b: Int): MyStream = cons(a, cons(b, fibonacci(a + b, b + a + b)))
  
}

  
  