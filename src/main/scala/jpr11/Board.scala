/*
 * Represents a fixed-size board. Cells on the edge of the board have dead neighbors.
 */

package jpr11

case class Location(x: Int, y: Int)

object Board {
  /**
   * Create the grid internally based on the cell generation function
   */
  private def createGrid(width: Int, height: Int, cellGenerationFunction:Location => Cell) = {
    val grid = collection.mutable.Map[Location, Cell]()
    for {
          x <- 0 to width - 1
          y <- 0 to height - 1
          val location = Location(x, y)
          val cell: Cell = cellGenerationFunction(location)
        }
        grid += (location -> cell)
    grid.toMap
  }
}

class Board(val width : Int, val height : Int, val grid:Map[Location, Cell]) {

  def this(width: Int, height: Int, cellGenerationFunction:Location => Cell) {
    this(width, height, Board.createGrid(width, height, cellGenerationFunction))
  }

  /**
   * Apply on the board itself delegates to the board's map
   * @param location the location to fetch a cell for
   * @return the cell at that location
   */
  def apply(location:Location) = {
    grid(location);
  }

  /**
   * Apply a function to all cells that match the condition. No promise that this
   * will visit in order
   */
  def visitCells(condition:(Cell=>Boolean))(fn:(Location=>Unit)) {
    for (
      (location, cell) <- grid
      if (condition(cell))
    ) {
      fn(location)
    }
  }

  /**
   * Get the count of live neighbors for a given location
   * @param a specific location
   * @return the count of neighbors that are alive (int 0-8)
   */
  def getLiveNeighborsCount(location: Location): Int = {
    require( location.x >= 0 && location.x < width, "Location.x out of bounds" )
    require( location.y >= 0 && location.y < height, "Location.y out of bounds" )

    val xValues = (0 max (location.x - 1)) to ((width - 1) min (location.x + 1))
    val yValues = (0 max (location.y - 1)) to ((height - 1) min (location.y + 1))
    val cells = for {
      xPos <- xValues
      yPos <- yValues
      if (Location(xPos, yPos) != location)
    }
    yield grid(Location(xPos, yPos))
    cells.count(_ == AliveCell)
  }

  /**
   * Evolve the board to its next generation
   * TODO parallelize this
   * @return the next generation of the board
   */
  def evolve(): Board = {
    return new Board(width, height, (location:Location) => {
      val cell = apply(location);
      cell.createNextGeneration(getLiveNeighborsCount(location))
    })
  }

  override def toString(): String = {
    val buf = new StringBuilder()
    for (y <- 0 to height - 1) {
      for(x <- 0 to width - 1) {
        val cell = grid(Location(x,y))
        buf.append(cell)
      }
      buf.append("\n")
    }
    buf.toString
  }
}
