/*
 * Represents a fixed-size board. Cells on the edge of the board have dead neighbors.
 */

package jpr11

object EndlessBoard {
  import util.Random.nextBoolean

  def randomBoard(width:Int, height: Int) = {
    new EndlessBoard(
      (0 to width).flatMap((x) => (0 to height).map((y) => Location(x,y)))
        .filter((_) => nextBoolean))
  }
}

class EndlessBoard(val aliveCells:Set[Location]) {

  def this(aliveCells:Traversable[Location]) = this(aliveCells.toSet)

  val minX = aliveCells.map(_.x).min
  val maxX = aliveCells.map(_.x).max
  val minY = aliveCells.map(_.y).min
  val maxY = aliveCells.map(_.y).max
  val width = maxX-minX
  val height = maxY-minY

  def evolve() = new EndlessBoard(
    aliveCells
      // Convert to a list, or flatMap will not produce duplicate results
      .toList
      .flatMap(getNeighbors)
      .groupBy((l) => l)
      .mapValues(_.size)
      // Collect only live cells
      .collect {
        case (location, count) if (apply(location).createNextGeneration(count) == AliveCell) => location
      }
  )

  def apply(location:Location) = if (aliveCells(location)) AliveCell else DeadCell

  def getNeighbors(l:Location) =
    Location(l.x-1, l.y-1) :: Location(l.x,   l.y-1) ::  Location(l.x+1, l.y-1) ::
    Location(l.x-1, l.y)                             ::  Location(l.x+1, l.y) ::
    Location(l.x-1, l.y+1) :: Location(l.x,   l.y+1) ::  Location(l.x+1, l.y+1) ::
    Nil
}








