package jpr11

import java.awt.{Dimension, Color, Graphics2D}
import swing._
import java.awt.geom.Rectangle2D
import actors.Actor

case class SetUpdating(update:Boolean)
case class AdjustSpeed(deltaMs:Int)
case object Randomize
case object Exit

class GameGrid extends Component with Actor {

  preferredSize = new Dimension(250, 250)

  val width = 120
  val height = 120

  private var _board = randomBoard;
  private var speed = 250
  private var updating = true

  override def paintComponent(g: Graphics2D) {
    val bounds = g.getClipBounds
    val xScale = (bounds.width-bounds.x) / _board.width.asInstanceOf[Double]
    val yScale = (bounds.height-bounds.y) / _board.height.asInstanceOf[Double]
    g.setColor(Color.WHITE)
    g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height)
    g.setColor(Color.BLACK)
    _board.grid.foreach {
      case (location, AliveCell) =>
        g.fill(new Rectangle2D.Double(location.x * xScale, location.y * yScale, xScale, yScale))
      case _ =>
    }
  }

  def act() = loop {
    reactWithin(speed) {
      case SetUpdating(value) => updating = value
      case Randomize => _board = randomBoard
      case AdjustSpeed(delta) => speed = 50 max (speed-delta)
      case Exit => exit
      case _ if (updating) => {
        _board = _board.evolve
        repaint
      }
    }
  }

  def randomBoard: Board = {
    new Board(width, height, (location) => if (util.Random.nextBoolean) AliveCell else DeadCell)
  }
}






