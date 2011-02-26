/*
 * Represents a cell in the board.  May be alive or dead.
 */

package jpr11

sealed abstract class Cell {
  def nextGeneration(liveNeighborCount: Int): Cell
}

case object AliveCell extends Cell {
  override def nextGeneration(liveNeighborCount: Int) = liveNeighborCount match {
    case 2 => AliveCell
    case 3 => AliveCell
    case _ => DeadCell
  }

  override def toString = "X"
}

case object DeadCell extends Cell {
  override def nextGeneration(liveNeighborCount: Int) = liveNeighborCount match {
    case 3 => AliveCell
    case _ => DeadCell
  }

  override def toString = "."
}
