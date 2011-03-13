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
    getNeighbors(location).count(grid(_) == AliveCell)
  }

  def getNeighbors(srcLocation: Location):Seq[Location] = {
    require( srcLocation.x >= 0 && srcLocation.x < width, "Location.x out of bounds" )
    require( srcLocation.y >= 0 && srcLocation.y < height, "Location.y out of bounds" )

    val xValues = (0 max (srcLocation.x - 1)) to ((width - 1) min (srcLocation.x + 1))
    val yValues = (0 max (srcLocation.y - 1)) to ((height - 1) min (srcLocation.y + 1))
    for {
      xPos <- xValues
      yPos <- yValues
      val location = Location(xPos, yPos)
      if (location != srcLocation)
    }
    yield location
  }

  /**
   * Evolve the board to its next generation
   * TODO parallelize this
   * @return the next generation of the board
   */
  def evolve(): Board = {
    // Type optional, but added to clearify code.
    val survivors:Map[Location, Cell] = grid
      // flattent the map to a list of all neighbors to live cells,
      // including duplicates.
      .flatMap {
        case (l, AliveCell) => getNeighbors(l)
        case _ => Nil
      }
      // Group together the locations. Each occurence of a location in
      // the collection represents it borders to a live cell. So the count of
      // matching locations is the count of neighbors
      .groupBy((l) => l)
      // Convert the values in the map to their size
      .mapValues(_.size)
      // evolve a cell at a location based on it's state and neighbor count
      .map {
        case (location, neighborCount) =>
          location -> grid(location).createNextGeneration(neighborCount)
      }

    return new Board(width, height, survivors.getOrElse(_, DeadCell))
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
