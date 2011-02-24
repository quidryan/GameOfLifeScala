package jpr11

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.mutable.Stack

class AliveCellSpec extends FlatSpec with ShouldMatchers {

    "A live cell with two live neighbors" should "stay alive" in {
        AliveCell.nextGeneration(2) should be (AliveCell)
    }

    "A live cell with three live neighbors" should "stay alive" in {
        AliveCell.nextGeneration(3) should be (AliveCell)
    }

    "A live cell with one live neighbor" should "die" in {
        AliveCell.nextGeneration(1) should be (DeadCell)
    }

    "A live cell with four live neighbors" should "die" in {
        AliveCell.nextGeneration(4) should be (DeadCell)
    }

    "A live cell with zero live neighbors" should "die" in {
        AliveCell.nextGeneration(0) should be (DeadCell)
    }

    "A live cell with minimum possible live neighbors" should "die" in {
        AliveCell.nextGeneration(Integer.MIN_VALUE) should be (DeadCell)
    }

    "A live cell with maximum possible live neighbors" should "die" in {
        AliveCell.nextGeneration(Integer.MAX_VALUE) should be (DeadCell)
    }
}


// vim: set ts=4 sw=4 et:
