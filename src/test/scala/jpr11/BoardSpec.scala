package jpr11

import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class BoardSpec extends FlatSpec with ShouldMatchers {

  val board = new Board(3, 3, (location:Location) => location.x match {
    case 0 => AliveCell
    case 1 => DeadCell
    case 2 => AliveCell
    case _ => DeadCell
  })

  "The test board" should "have the correct width and height" in {
    board.width should be(3)
    board.height should be(3)
  }

  it should "have cells with the correct alive/dead states" in {
    board(Location(0,0)) should be(AliveCell)
    board(Location(1,2)) should be(DeadCell)
  }

}


// vim: set ts=4 sw=4 et:
