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
    var initial = Game.generateRandomBoard(20, 20)
    for ( x <- 1 to 10 ) {
      val generation1 = Game.evolve(initial)
      println(generation1)
      initial = generation1
    }

//    println(initial)
//    val generation1 = Game.evolve(initial)
//    println(generation1)
//    val generation2 = Game.evolve(generation1)
//    println(generation2)
//    val generation3 = Game.evolve(generation2)
//    println(generation3)
  }

}
