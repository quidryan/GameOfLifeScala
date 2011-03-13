/*
 * Represents a fixed-size board. Cells on the edge of the board have dead neighbors.
 */

package jpr11

object EndlessBoard {
  def randomBoard(width:Int, height: Int) = {
    val aliveLocations = for {
      x <- 0 to width
      y <- 0 to height
      if(util.Random.nextBoolean)
    } yield Location(x, y)
    new EndlessBoard(aliveLocations.toSet)
  }
}

class EndlessBoard(val aliveCells:Set[Location]) {
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
        case (location, 3) => location
        case (location, 2) if aliveCells(location) => location
      }.toSet
  )

  val grid = (for {
      x <- minX to maxX
      y <- minY to maxY
      val location = Location(x, y)
    } yield location -> apply(location)
  ).toMap

  def apply(location:Location) = if (aliveCells.contains(location)) AliveCell else DeadCell

  def withinBounds(x1: Int, y1:Int, x2:Int, y2:Int) = new EndlessBoard(
    aliveCells.filter((l) => l.x >= x1 && l.x <= x2 && l.y >= y1 && l.y <= y2)
  )

  def getNeighbors(l:Location) =
    Location(l.x-1, l.y-1) :: Location(l.x,   l.y-1) ::  Location(l.x+1, l.y-1) ::
    Location(l.x-1, l.y)                             ::  Location(l.x+1, l.y) ::
    Location(l.x-1, l.y+1) :: Location(l.x,   l.y+1) ::  Location(l.x+1, l.y+1) ::
    Nil
}








