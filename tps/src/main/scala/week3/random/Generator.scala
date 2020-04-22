package week3.random

/**
 * Trait that generate random elements of type T
 */
trait Generator[T] {
  
  /**
   * generate a random element of type T
   */
  def generate: T

  def unit[T](x: T): Generator[T] = {
    new Generator[T] {
      def generate: T = x
    }
  }

  def map[S](f: T => S): Generator[S] = {
    new Generator[S] {
      def generate: S = f(Generator.this.generate)
    }
  }
  
  def flatMap[S](f: T => Generator[S]): Generator[S] = {
    new Generator[S] {
      // TODO: complete the implementation of flatMap
      def generate: S = f(Generator.this.generate).generate
    }    
  }

  def withFilter(f: T => Boolean): Generator[T] = {
    new Generator[T] {
      def generate: T = {
        var sol = this.generate
        while (!f(sol))
          sol = this.generate
        sol
      }
    }
  }
}

