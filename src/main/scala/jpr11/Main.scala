package jpr11

import scala.collection.mutable.HashMap

case class Location(val x:Int, val y:Int) extends Tuple2[Int, Int](x,y)

object Game {
  //val previousGeneration:Board
  def evolve(oldBoard:Board):Board = {
     oldBoard
  }
}

class Board(width:Int, height:Int) {
   val grid = new HashMap[Location, Cell]

  def getNeighbors(location:Location):Int = {
    val xValues = Math.max(0, location.x-1) to Math.min(width, location.x+1)
    val yValues = Math.max(0, location.y-1) to Math.min(height, location.y+1)
    val cells = for {
           xPos <- xValues
           yPos <- yValues
           if(Location(xPos, yPos) != location)
         }
         yield grid(Location(xPos, yPos))
    cells.count(_ == AliveCell)
  }
}

sealed abstract class Cell {
  def nextGeneration(liveNeighborCount:Int):Cell
}

case object AliveCell extends Cell {
  override def nextGeneration(liveNeighborCount:Int) = liveNeighborCount match {
     case 2 => AliveCell
     case 3 => AliveCell
     case _ => DeadCell
  }
}

case object DeadCell extends Cell {
  override def nextGeneration(liveNeighborCount:Int) = liveNeighborCount match {
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
