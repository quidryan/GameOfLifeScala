package jpr11

import swing.{Component, MainFrame, SimpleSwingApplication}
import java.awt.{Dimension, Color, Graphics2D}
import java.util.concurrent.{TimeUnit, Executors}

object SwingMain extends SimpleSwingApplication {

  private final val BOARD_WIDTH = 65;
  private final val BOARD_HEIGHT = 65;

  implicit def funToRunnable(fun: () => Unit) = new Runnable() { def run() = fun() }

  // generate random board
  val initial = new Board(BOARD_WIDTH, BOARD_HEIGHT, (location:Location) => if (util.Random.nextBoolean) AliveCell else DeadCell)
  
  def top = new MainFrame {
    val gameGrid = new GameGrid
    gameGrid.board = initial

    title = "Comway's Game of Life"
    contents = gameGrid
    size = new Dimension(gameGrid.board.width*gameGrid.scale+gameGrid.scale, gameGrid.board.height*gameGrid.scale+gameGrid.scale)

    Executors.newSingleThreadScheduledExecutor.scheduleAtFixedRate(() => {
       gameGrid.board = gameGrid.board.evolve()
    }, 250, 250, TimeUnit.MILLISECONDS)
  }

}

class GameGrid extends Component {

  private var _board:Board = null;
  var scale = 8

  def board = _board
  def board_=(b:Board) = {
    _board = b;
    size = new Dimension(board.width*scale+scale, board.height*scale+scale)
    repaint
  }

  override def paintComponent(g:Graphics2D) {
    if (_board != null) {
      g.setColor(Color.WHITE)
      g.fillRect(0, 0, _board.width*scale+scale, _board.height*scale+scale)
      g.setColor(Color.BLACK)
      board.visitCells(_ == AliveCell) { location =>
        g.fillRect(location.x*scale, location.y*scale, scale, scale);
      }
    }
  }
}