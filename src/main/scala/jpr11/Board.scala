/*
 * Represents a fixed-size board. Cells on the edge of the board have dead neigbors.
 */

package jpr11

case class Location(val x:Int, val y:Int) extends Tuple2[Int, Int](x,y)

class Board(val grid:Map[Location,Cell] ) {

  val width:Int = grid.keys.map(_.x).max
  val height:Int = grid.keys.map(_.y).max

  def getLiveNeighbors(location:Location):Int = {

    val xValues = (0 max (location.x-1)) to (width min (location.x+1))
    val yValues = (0 max (location.y-1)) to (height min (location.y+1))
    val cells = for {
           xPos <- xValues
           yPos <- yValues
           if(Location(xPos, yPos) != location)
         }
         yield grid(Location(xPos, yPos))
    cells.count(_ == AliveCell)
  }

  override def toString():String = {
    val buf = new StringBuilder()
    for( y <- 0 to height) {
      for( x <- 0 to width) {
        val cell = grid( Location(x,y) )
        buf.append( cell )
      }
      buf.append("\n")
    }
    buf.toString
  }
}
