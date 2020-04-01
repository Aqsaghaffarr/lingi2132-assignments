package week1.polynom

import scala.collection.mutable

class Polynom(coefs: Traversable[Int]) {

  assert(coefs.nonEmpty, "no coefficient")

  // coefficients(0) + coefficients(1) * x + coefficients(2) * x^2 + ... 
  private val coefficients: Vector[Int] = coefs.toVector
  var deg: Int = coefficients.length - 1
  reduce()

  /**
   * Returns the coefficient related to `degree`.
   * Example: 1 + 2x + 5x^2
   * p(0) = 1
   * p(1) = 2 
   * p(5) = 0
   */
  def apply(degree: Int): Int = {
    if (degree < 0) throw new IllegalArgumentException("negative degree")
    else if (degree > deg) 0
    else coefficients(degree)
  }

  def reduce(): Unit = {
    var i = coefficients.length - 1
    while (i > 0) {
      if (coefficients(i) != 0) {
        deg = i
        return
      }
      i -= 1
    }
    deg = 0
  }

  /** 
   * Returns the product of this `Polynom` with the `Polynom` p.
   * Example: (1 + 2x + 5x^2) * (4x) = 4x + 8x^2 + 20x^3
   */
  def *(p: Polynom): Polynom = {
    val polyc = mutable.MutableList.fill(deg + p.deg + 1)(0)
    var i = 0
    while (i <= deg) {
      var j = 0
      while (j <= p.deg) {
        polyc(i + j) += coefficients(i) * p.coefficients(j)
        j += 1
      }
      i += 1
    }
    val poly = Polynom(polyc: _*)
    poly.reduce()
    poly
  }

  /** 
   * Returns the subtraction of this `Polynom` with the `Polynom` p.
   * Example: (1 + 2x + 5x^2) - (4x + x^3) = 1 - 2x + 5x^2 - x^3
   */
  def -(p: Polynom): Polynom = {
    val c = coefficients.zipAll(p.coefficients, 0, 0)
    val poly = Polynom(c map {case (x, y) => x - y}: _*)
    poly.reduce()
    poly
  }

  /** 
   * Returns the addiction of this `Polynom` with the `Polynom` p
   * Example: (1 + 2x + 5x^2) + (4x + x^3) = 1 + 6x + 5x^2 + x^3
   */
  def +(p: Polynom): Polynom = {
    val c = coefficients.zipAll(p.coefficients, 0, 0)
    val poly = Polynom(c map {case (x, y) => x + y}: _*)
    poly.reduce()
    poly
  }

  /** 
   * Returns the maximum degree of this `Polynom`.
   * Example: the maximum degree of 4x + x^3 is 3
   */
  def maxDegree: Int = deg

  /** 
   * Returns the minimum degree of this `Polynom`.
   * Example: the minimum degree of 1 + 4x + x^3 is 0
   */
  def minDegree: Int = if (deg == 0) 0 else coefficients.indexWhere(_ != 0)

  /** 
   * Returns true if this `Polynom` represents the same `Polynom` as that.
   */
  override def equals(that: Any) = that match {
    case p: Polynom => p.coefficients.equals(coefficients)
    case _ => false
  }

  override def hashCode = coefficients.hashCode
  
  override def toString: String = {
    coefficients.zipWithIndex.filter(p => p._1 != 0).map(p => p._1 + "x^" + p._2).mkString(" + ")
  }
}

object Polynom {

  // coefs(0) + coefs(1) * x + coefs(2) * x^2 + ...
  def apply(coefs: Int*) = new Polynom(coefs)

  def sum(polynoms: Iterable[Polynom]): Polynom = {
    // hint, uses map/max/min/... to first compute coefficients
    val res = polynoms.reduceLeft(_ + _)
    res.reduce()
    res
  }
}