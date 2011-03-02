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

    object buttonState extends Enumeration("Start", "Stop") {
      val start, stop = Value
    }

    class StartStopButton(var currentState: buttonState.Value) extends Button {
      text = currentState.toString

      private def setState(newState: buttonState.Value) = {
        currentState = newState
        text = currentState.toString
      }

      def toggle = {
        if (currentState == buttonState.start) {
          mainActor ! "start"
          setState(buttonState.stop)
        } else {
          mainActor ! "stop"
          setState(buttonState.start)
        }
      }
    }

    val startStopButton = new StartStopButton(buttonState.stop)
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

    actor {
      loop {
        Thread.sleep(250)
        mainActor ! "refresh"
      }
    }

    var mainActor = actor {
      var updating = true
      loop {
        react {
          case "start" =>
            updating = true;
          case "stop" =>
            updating = false;
          case "refresh" =>
            if (updating)
              gameGrid.board = gameGrid.board.evolve()
          case "random" =>
            gameGrid.board = randomBoard
        }
      }
    }

    listenTo(startStopButton)
    listenTo(randomButton)
    reactions += {
      case ButtonClicked(`startStopButton`) => {
        startStopButton.toggle
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
    if (_board != null) _board.clear() // seems like I shouldn't need this, but it leaks the board contents every time without it
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