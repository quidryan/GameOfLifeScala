package jpr11

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.mutable.Stack

class AliveCellSpec extends FlatSpec with ShouldMatchers {

    "A live cell with two live neighbors" should "stay alive" in {
        AliveCell.nextGeneration(2) should be (AliveCell)
    }

}


// vim: set ts=4 sw=4 et:
