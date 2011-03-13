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

  def randomBoard = EndlessBoard.randomBoard _

  private var _board = randomBoard(width, height);
  private var speed = 250
  private var updating = true
  private var stopped = false

  var minX = 0
  var maxX = 0
  var minY = 0
  var maxY = 0

  override def paintComponent(g: Graphics2D) {
    val bounds = g.getClipBounds
    minX = minX min _board.minX
    maxX = maxX max _board.maxX
    minY = minY min _board.minY
    maxY = maxY max _board.maxY
    val width = maxX - minX
    val height = maxY - minY

    val xScale = (bounds.width-bounds.x) / width.asInstanceOf[Double]
    val yScale = (bounds.height-bounds.y) / height.asInstanceOf[Double]
    g.setColor(Color.WHITE)
    g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height)
    g.setColor(Color.BLACK)
    _board.aliveCells.foreach { location =>
        g.fill(new Rectangle2D.Double((location.x-minX) * xScale, (location.y-minY) * yScale, xScale, yScale))
    }
  }

  def act() = loopWhile(!stopped) {
    reactWithin(speed) {
      case SetUpdating(value) => updating = value
      case Randomize => _board = randomBoard(width, height)
      case AdjustSpeed(delta) => speed = 50 max (speed-delta)
      case Exit => stopped = true
      case _ if (updating) => {
        _board = _board.evolve
        repaint
      }
    }
  }

}






