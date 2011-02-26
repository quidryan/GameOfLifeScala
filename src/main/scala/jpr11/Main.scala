package jpr11

object Game {
  //val previousGeneration:Board
  def evolve(oldBoard: Board): Board = {
    return new Board(oldBoard.width, oldBoard.height, (location:Location) => {
      // TODO how to parallelize?
      val cell = oldBoard.getCell(location);
      cell.createNextGeneration(oldBoard.getLiveNeighborsCount(location))
    })
  }

  def generateRandomBoard(width: Int, height: Int): Board = {
    return new Board(width, height, (location:Location) => if (util.Random.nextBoolean) AliveCell else DeadCell)
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
