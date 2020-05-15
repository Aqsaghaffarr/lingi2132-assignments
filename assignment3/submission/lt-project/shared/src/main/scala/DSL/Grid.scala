package DSL


// Grid on which a game can be played.
case class Grid(width: Int, height: Int, wallTop: ComposedSpot[Wall],
                wallBottom: ComposedSpot[Wall], wallRight: ComposedSpot[Wall],
                wallLeft: ComposedSpot[Wall], field: ComposedSpot2D[Empty]) {

  val spots: ComposedSpot2D[Spot] = ComposedSpot2D(Array.ofDim[Spot](height, width))

  for (i <- 0 until height;
       j <- 0 until width) {
    if (i == 0) spots(i)(j) = wallTop(j)
    else if (i == height - 1) spots(i)(j) = wallBottom(j)
    else if (j == 0) spots(i)(j) = wallRight(i-1)
    else if (j == width - 1) spots(i)(j) = wallLeft(i-1)
    else spots(i)(j) = field(i-1)(j-1)
  }
}
