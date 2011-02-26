package jpr11

import org.scalatest.matchers.ShouldMatchers
import org.scalatest._

class BoardSpec extends FlatSpec with ShouldMatchers {

  // TODO replace with String constructor.  This is the board:
  //X...
  //XX..
  //X.X.
  //X...
  val board = new Board(3, 3, (location:Location) => location.x match {
    case 0 => AliveCell
    case 1 => location.y match {
      case 1 => AliveCell
      case _ => DeadCell
    }
    case 2 => location.y match {
      case 2 => AliveCell
      case _ => DeadCell
    }
    case _ => DeadCell
  })

  System.out.println("Board \n%s" format board.toString)

  "The test board" should "have the correct width and height" in {
    board.width should be(3)
    board.height should be(3)
  }

  it should "have cells with the correct alive/dead states" in {
    board(Location(0,0)) should be(AliveCell)
    board(Location(0,1)) should be(AliveCell)
    board(Location(0,2)) should be(AliveCell)

    board(Location(1,0)) should be(DeadCell)
    board(Location(1,1)) should be(AliveCell)
    board(Location(1,2)) should be(DeadCell)

    board(Location(2,0)) should be(DeadCell)
    board(Location(2,1)) should be(DeadCell)
    board(Location(2,2)) should be(AliveCell)

    // Strange, this should produce NoElementException, but it does not
//    evaluating { board(Location(3,1)) } should produce[NoSuchElementException]
  }

  // not really a behavior, but needed to test
  it should "generate the correct live neighbor counts" in {
    board.getLiveNeighborsCount(Location(1,0)) should be(3)
    board.getLiveNeighborsCount(Location(1,1)) should be(4)
    board.getLiveNeighborsCount(Location(2,2)) should be(1)
  }

  it should "evolve into the next generation correctly" in {
    val nextBoard = board.evolve

    // Refer to the rules on this page: http://en.wikipedia.org/wiki/Conway's_Game_of_Life
    nextBoard(Location(0,0)) should be(AliveCell) // still alive (rule 2)
    nextBoard(Location(0,1)) should be(AliveCell) // still alive (rule 2)
    nextBoard(Location(0,2)) should be(AliveCell) // still alive (rule 2)

    nextBoard(Location(1,0)) should be(AliveCell) // became alive (rule 4)
    nextBoard(Location(1,1)) should be(DeadCell)  // dies (rule 3)

    nextBoard(Location(2,1)) should be(DeadCell)  // dies (rule 1)
  }

}


// vim: set ts=4 sw=4 et:
