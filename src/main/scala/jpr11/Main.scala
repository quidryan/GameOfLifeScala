package jpr11

object Game {
  //val previousGeneration:Board
  def evolve(oldBoard:Board):Board = {
    val newGrid = oldBoard.grid.map(entry => {
        val (location,cell) = entry;
        (location, cell.nextGeneration(oldBoard.getLiveNeighbors(location)))
      })
    return new Board(newGrid) // <-- Implicit conversion from Iterable[Tuple2] to Map[Location,Cell]
  }
}

object Main {

  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
  }

}
