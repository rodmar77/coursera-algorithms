import scala.io.StdIn._

object PairwiseDistinctSum {
  def main(args: Array[String]) = {
    val l = getPairwiseDistinctSum(readInt)
    println(s"${l.size}\n${l.mkString(" ")}")
  }

  def getPairwiseDistinctSum(total: Long) = {
    def getStartList = (1 until (Math.sqrt(8 * total + 1).toInt - 1) / 2).map(_.toLong)
    def getPairwiseDistinctSum(curr: Long, lastn: Long, acc: Seq[Long]): Seq[Long] = {
      if ((curr - lastn) <= lastn) acc :+ curr
      else getPairwiseDistinctSum(curr - lastn, lastn + 1, acc :+ lastn)
    }

    if (total < 3) List(total)
    else {
      val l = getStartList
      getPairwiseDistinctSum(total - l.sum, l.last + 1, l)
    }
  }

}