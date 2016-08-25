object CoveringSegments {
  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    val ps = getPoints(
      (1 to in.nextInt)
        .map(n => (in.nextInt, in.nextInt))
        .sortWith((a, b) => if (a._2 == b._2) (a._1 > b._1) else (a._2 < b._2)))
    println(s"${ps.size}\n${ps.mkString(" ")}")
  }

  def getPoints(ps: Seq[(Int, Int)]) = {
    def getPoints(rem: Seq[(Int, Int)], max: Int, acc: List[Int]): List[Int] = {
      if (rem.isEmpty) acc
      else if (max < rem.head._1) getPoints(rem.tail, rem.head._2, acc :+ rem.head._2)
      else getPoints(rem.tail, max, acc)
    }

    getPoints(ps.tail, ps.head._2, List(ps.head._2))
  }
}