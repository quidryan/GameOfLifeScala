package jpr11

object BoardFactory {

  def splitIntoArray(gridString:String):List[List[Char]] = {
    val splitOnWhitespace = """\s+""".r
    val rows = splitOnWhitespace.split(gridString.trim).toList
    return for {
      row <- rows
    } yield row.toList

  }

  def loadBoard(gridString:String):Board = {
    val rows = splitIntoArray(gridString)
    return new Board(rows(0).size, rows.size, (location:Location) => {
        val ch = rows(location.y)(location.x)
        ch match {
          case 'X' => AliveCell
          case _ => DeadCell
        }
    })
  }
}
