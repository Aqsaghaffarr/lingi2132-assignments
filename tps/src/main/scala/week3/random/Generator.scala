package week3.random

/**
 * Trait that generate random elements of type T
 */
trait Generator[T] {
  
  /**
   * generate a random element of type T
   */
  def generate: T

  def map[S](f: T => S): Generator[S] = {
    new Generator[S] {
      def generate = f(Generator.this.generate)
    }
  }
  
  def flatMap[S](f: T => Generator[S]):  Generator[S] = {
    new Generator[S] {
      // TODO: complete the implementation of flatMap
      def generate = ???
    }    
  }
}

