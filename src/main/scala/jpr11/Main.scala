package jpr11

object Game {
  //val previousGeneration:Board
  def evolve(oldBoard: Board): Board = {
    val newGrid = oldBoard.grid.map(entry => {
      val (location, cell) = entry
      (location, cell.createNextGeneration(oldBoard.getLiveNeighbors(location)))
    })
    return new Board(newGrid) // <-- Implicit conversion from Iterable[Tuple2] to Map[Location,Cell]
  }

  def generateRandomBoard(width: Int, height: Int): Board = {
    val randomGrid = for {
      x <- 0 to width
      y <- 0 to height
      val cell: Cell = if (util.Random.nextBoolean) AliveCell else DeadCell
    }
    yield (Location(x, y), cell)
    return new Board(Map.empty ++ randomGrid)
  }
}

object Main {

  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
    val initial = Game.generateRandomBoard(20, 20)
    println(initial)
    val generation1 = Game.evolve(initial)
    println(generation1)
    val generation2 = Game.evolve(initial)
    println(generation1)
    val generation3 = Game.evolve(initial)
    println(generation1)
  }

}
