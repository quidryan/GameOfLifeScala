package gameoflife

import scala.collection.mutable.HashMap

class Board(width:Int, height:Int) {
   val grid = new HashMap[Pair[Int, Int], Cell]

  //def getNeighbors(x:Int, y:Int):List[Cell]
}

sealed abstract class Cell {
  def nextGeneration(neighbors:Int):Cell
}

case object AliveCell extends Cell {
  override def nextGeneration(neighbors:Int) = neigbors match {
     case 2 => AliveCell
     case 3 => AliveCell
     case _ => DeadCell
  }
}

case object DeadCell extends Cell {
  override def nextGeneration(neighbors:Int) = neigbors match {
     case 3 => AliveCell
     case _ => DeadCell
  }
}

object Main {

  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]): Unit = {
  }

}
