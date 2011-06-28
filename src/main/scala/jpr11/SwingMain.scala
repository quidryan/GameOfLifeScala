package jpr11

import swing._
import event._

object SwingMain extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "Comway's Game of Life"

    val startStopButton = new Button("Stop")
    val fasterButton = new Button("+")
    val slowerButton = new Button("-")
    val randomButton = new Button("Random")
    val gameGrid = new GameGrid

    contents = new BorderPanel() {

      import BorderPanel._

      add(new FlowPanel {
        contents += startStopButton
        contents += fasterButton
        contents += slowerButton
        contents += randomButton
      }, Position.North)
      add(gameGrid, Position.Center)

      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

            gameGrid.board = gameGrid.board.evolve()
    listenTo(startStopButton)
    listenTo(randomButton)
    listenTo(fasterButton)
    listenTo(slowerButton)
    listenTo(gameGrid)

    reactions += {
      case WindowOpened(_) => gameGrid.start
      case WindowClosed(_) => gameGrid ! Exit
      case ButtonClicked(`randomButton`) => gameGrid ! Randomize
      case ButtonClicked(`fasterButton`) => gameGrid ! AdjustSpeed(50)
      case ButtonClicked(`slowerButton`) => gameGrid ! AdjustSpeed(-50)

      case ButtonClicked(`startStopButton`) => {
        startStopButton.text match {
          case "Stop" => gameGrid ! SetUpdating(false); startStopButton.text = "Start"
          case _ => gameGrid ! SetUpdating(true); startStopButton.text = "Stop"
        }
      }
      case ComponentResized(`gameGrid`) => {
        val gWidth = gameGrid.size.width;
        val gHeight = gameGrid.size.height;
        // Adjust size if the gameGrid is not square
        if (gWidth < gHeight) {
          self.setSize(size.width, size.height-gHeight+gWidth)
        } else if (gameGrid.size.width > gameGrid.size.height) {
          self.setSize(size.width-gWidth+gHeight, size.height)
        }
      }
    }
  }

}

