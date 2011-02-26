package jpr11

import swing.{Component, MainFrame, SimpleSwingApplication}
import java.awt.{Dimension, Color, Graphics2D}
import java.util.concurrent.{TimeUnit, Executors}

object SwingMain extends SimpleSwingApplication {

  val initial = Game.generateRandomBoard(40, 40)
  def top = new MainFrame {
    val gameGrid = new GameGrid
    gameGrid.board = initial

    title = "Comway's Game of Life"
    contents = gameGrid
    size = new Dimension(400,400)
    Executors.newSingleThreadScheduledExecutor.scheduleAtFixedRate(new Runnable {
      def run() = {
        java.awt.EventQueue.invokeLater(new Runnable() {
          def run() = {
            gameGrid.board = Game.evolve(gameGrid.board)
          }
        })
      }
    }, 250, 250, TimeUnit.MILLISECONDS)
  }


}

class GameGrid extends Component {

  private var _board:Board = null;
  var scale = 8

  def board = _board
  def board_=(b:Board) = {
    _board = b;
    repaint
  }

  override def paintComponent(g:Graphics2D) {
    if (_board != null) {
      g.setColor(Color.WHITE)
      g.fillRect(0, 0, _board.width*scale+scale, _board.height*scale+scale)
      g.setColor(Color.BLACK)
      for (
        (location, cell) <- _board.grid
        if cell == AliveCell
      ) {
        g.fillRect(location.x*scale, location.y*scale, scale, scale);
      }
    }
  }
}