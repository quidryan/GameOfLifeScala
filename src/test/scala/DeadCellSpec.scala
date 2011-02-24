package jpr11

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.mutable.Stack

class DeadCellSpec extends FlatSpec with ShouldMatchers {

    "A dead cell with three live neighbors" should "become alive" in {
        DeadCell.nextGeneration(3) should be (AliveCell)
    }

    "A dead cell with two live neighbors" should "stay dead" in {
        DeadCell.nextGeneration(2) should be (DeadCell)
    }

    "A dead cell with four live neighbors" should "stay dead" in {
        DeadCell.nextGeneration(4) should be (DeadCell)
    }

    "A dead cell with minimum possible live neighbors" should "stay dead" in {
        DeadCell.nextGeneration(Integer.MIN_VALUE) should be (DeadCell)
    }

    "A dead cell with maximum possible live neighbors" should "stay dead" in {
        DeadCell.nextGeneration(Integer.MAX_VALUE) should be (DeadCell)
    }

}


// vim: set ts=4 sw=4 et:
