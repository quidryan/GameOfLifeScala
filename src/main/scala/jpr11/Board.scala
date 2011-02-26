/*
 * Represents a fixed-size board. Cells on the edge of the board have dead neigbors.
 */

package jpr11

case class Location(x: Int, y: Int)

class Board(val width : Int, val height : Int, cellGenerationFunction:Location => Cell) {

  private val grid: Map[Location, Cell] = createGrid()

  def getCell(location:Location) = {
    grid(location);
  }

  def visitCells(condition:(Cell=>Boolean))(fn:(Location=>Unit)) {
    for (
      (location, cell) <- grid
      if (condition(cell))
    ) {
      fn(location)
    }
  }

  /**
   * Get the count of live neigbors for a given location
   * @param a specific location
   * @return the count of neighbors that are alive (int 0-8)
   */
  def getLiveNeighborsCount(location: Location): Int = {
    val xValues = (0 max (location.x - 1)) to (width min (location.x + 1))
    val yValues = (0 max (location.y - 1)) to (height min (location.y + 1))
    val cells = for {
      xPos <- xValues
      yPos <- yValues
      if (Location(xPos, yPos) != location)
    }
    yield grid(Location(xPos, yPos))
    cells.count(_ == AliveCell)
  }

  /**
   * Create the grid internally based on the cell generation function
   */
  private def createGrid() = {
    val grid = for {
          x <- 0 to width
          y <- 0 to height
          val location = Location(x, y)
          val cell: Cell = cellGenerationFunction(location)
        }
        yield (location, cell)
        Map.empty ++ grid
  }

  override def toString(): String = {
    val buf = new StringBuilder()
    for (y <- 0 to height) {
      for (x <- 0 to width) {
        val cell = grid(Location(x, y))
        buf.append(cell)
      }
      buf.append("\n")
    }
    buf.toString
  }
}
