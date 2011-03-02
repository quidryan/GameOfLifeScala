package jpr11

import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class BoardFactorySpec extends FlatSpec with ShouldMatchers {

  val blinkerString = """|_____
                         |_____
                         |_XXX_
                         |_____
                         |_____""".stripMargin


  "The Board Factory" should "split a multiline string into an array" in {
    val arr = BoardFactory.splitIntoArray(blinkerString)
    arr.size should be(5)
    arr(0).size should be(5)
  }

  it should "load blinker string into a board" in {
    val board: Board = BoardFactory.loadBoard(blinkerString)
    board(Location(0, 0)) should be(DeadCell)
    board(Location(1, 1)) should be(DeadCell)
    board(Location(2, 2)) should be(AliveCell)
    board(Location(3, 3)) should be(DeadCell)
  }

}


// vim: set ts=4 sw=4 et:
