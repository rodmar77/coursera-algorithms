import scala.io.StdIn._

object KmpSearch {
  def main(args: Array[String]) = {
    println(kmpSearch(readLine, readLine))
  }

  def kmpSearch(pattern: String, genome: String) = {
    val (gLen, pLen) = (genome.length, pattern.length)
    val kmpTable = {
      def getKmpTable(pos: Int, cnd: Int, acc: List[Int]): List[Int] = {
        if (pos >= pLen) acc
        else if (pattern(pos - 1) == pattern(cnd)) getKmpTable(pos + 1, cnd + 1, acc :+ (cnd + 1))
        else if (cnd > 0) getKmpTable(pos, acc(cnd), acc)
        else getKmpTable(pos + 1, cnd, acc :+ 0)
      }

      getKmpTable(2, 0, List(-1, 0))
    }

    def kmpSearch(m: Int, i: Int): Int = {
      if (m + i > gLen) -1
      else if (pattern.charAt(i) == genome.charAt(m + i)) {
        if (i == pLen - 1) m
        else kmpSearch(m, i + 1)
      } else kmpSearch(m + (i - kmpTable(i)), kmpTable(i) max 0)
    }

    kmpSearch(0, 0)
  }
}