package DSL


case class Grid(width: Int, height: Int, wall_top: ComposedSpot[Wall],
                wall_bottom: ComposedSpot[Wall], wall_right: ComposedSpot[Wall],
                wall_left: ComposedSpot[Wall], field: ComposedSpot2D[Empty]) {

  val spots: ComposedSpot2D[Spot] = ComposedSpot2D(Array.ofDim[Spot](height, width))

  for (i <- 0 until height; j <- 0 until width) {
    if (i == 0) {
      spots(i)(j) = wall_top(j)
    } else if (i == height - 1) {
      spots(i)(j) = wall_bottom(j)
    } else if (j == 0) {
      spots(i)(j) = wall_right(i-1)
    } else if (j == width - 1) {
      spots(i)(j) = wall_left(i-1)
    } else {
      spots(i)(j) = field(i-1)(j-1)
    }
  }
}
