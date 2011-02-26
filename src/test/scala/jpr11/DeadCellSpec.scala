package jpr11

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class DeadCellSpec extends FlatSpec with ShouldMatchers {

  "A dead cell with three live neighbors" should "become alive" in {
    DeadCell.createNextGeneration(liveNeighborCount = 3) should be(AliveCell)
  }

  "A dead cell with two live neighbors" should "stay dead" in {
    DeadCell.createNextGeneration(liveNeighborCount = 2) should be(DeadCell)
  }

  "A dead cell with four live neighbors" should "stay dead" in {
    DeadCell.createNextGeneration(liveNeighborCount = 4) should be(DeadCell)
  }

  "A dead cell with minimum possible live neighbors" should "stay dead" in {
    DeadCell.createNextGeneration(liveNeighborCount = 0) should be(DeadCell)
  }

  "A dead cell with maximum possible live neighbors" should "stay dead" in {
    DeadCell.createNextGeneration(liveNeighborCount = 8) should be(DeadCell)
  }

  "A live cell with an illegal number of live neighbors" should "throw an exception" in {
    evaluating { AliveCell.createNextGeneration(liveNeighborCount = 9) } should produce [IllegalArgumentException]
    evaluating { AliveCell.createNextGeneration(liveNeighborCount = -1) } should produce [IllegalArgumentException]
  }
}


// vim: set ts=4 sw=4 et:
