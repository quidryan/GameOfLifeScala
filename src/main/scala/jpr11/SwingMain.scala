package jpr11

import java.awt.{Dimension, Color, Graphics2D}
import scala.actors.Actor._
import swing._
import event.ButtonClicked

object SwingMain extends SimpleSwingApplication {

  private val BOARD_WIDTH = 65;
  private val BOARD_HEIGHT = 65;

  val gameGrid = {
    val grid = new GameGrid
    grid.board = randomBoard
    grid
  }

  def top = new MainFrame {
    title = "Comway's Game of Life"

    val startStopButton = new Button("Stop")
    val randomButton = new Button("Random")

    contents = new BorderPanel() {

      import BorderPanel._

      add(new FlowPanel {
        contents += startStopButton
        contents += randomButton
      }, Position.North)
      add(gameGrid, Position.Center)

      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    var mainActor = actor {
      var updating = true
      loop {
        reactWithin(250) {
          case "start" =>
            updating = true;
            gameGrid.board = gameGrid.board.evolve()
          case "stop" =>
            updating = false;
          case "random" =>
            gameGrid.board = randomBoard
          case _ =>
            if (updating)
              gameGrid.board = gameGrid.board.evolve()

        }
      }
    }

    listenTo(startStopButton)
    listenTo(randomButton)

    reactions += {
      case ButtonClicked(`startStopButton`) => {
        startStopButton.text match {
          case "Stop" => mainActor ! "stop"; startStopButton.text = "Start"
          case _ => mainActor ! "start"; startStopButton.text = "Stop"
        }
      }
      case ButtonClicked(`randomButton`) => {
        mainActor ! "random"
      }
    }
  }

  def randomBoard: Board = {
    new Board(BOARD_WIDTH, BOARD_HEIGHT, (location: Location) => if (util.Random.nextBoolean) AliveCell else DeadCell)
  }
}

class GameGrid extends Component {

  private var _board: Board = null;
  val scale = 8

  def board = _board

  def board_=(b: Board) = {
    _board = b;
    preferredSize = new Dimension(_board.width * scale, _board.height * scale)
    repaint
  }

  override def paintComponent(g: Graphics2D) {
    if (_board != null) {
      g.setColor(Color.WHITE)
      g.fillRect(0, 0, _board.width * scale, _board.height * scale)
      g.setColor(Color.BLACK)
      _board.visitCells(_ == AliveCell) {
        location =>
          g.fillRect(location.x * scale, location.y * scale, scale, scale);
      }
    }
  }
}