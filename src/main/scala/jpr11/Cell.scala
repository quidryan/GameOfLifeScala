/*
 * Represents a cell in the board.  May be alive or dead.
 */

package jpr11

sealed abstract class Cell {
  def createNextGeneration(liveNeighborCount: Int): Cell = {
    if (liveNeighborCount < 0 || liveNeighborCount > 8)
      throw new IllegalArgumentException("Live neighbor count must be between 0 and 8. Was %s" format liveNeighborCount)

    doCreation(liveNeighborCount);
  }

  protected def doCreation(liveNeighborCount: Int): Cell
}

case object AliveCell extends Cell {
  override def doCreation(liveNeighborCount: Int) = liveNeighborCount match {
    case 2 => AliveCell
    case 3 => AliveCell
    case _ => DeadCell
  }
}

case object DeadCell extends Cell {
  override def doCreation(liveNeighborCount: Int) = liveNeighborCount match {
    case 3 => AliveCell
    case _ => DeadCell
  }
}
