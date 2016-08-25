import scala.io.StdIn._

object SmallFibonacci {

  def main(args: Array[String]) = {
    println(smallFib(readInt))
  }

  def smallFib(n: Int): Int = {
    def smallFib(p: (Int, Int), c: Int): Int = {
      if (c == n) p._1
      else smallFib((p._2, (p._1 + p._2)), c + 1)
    }

    smallFib((0, 1), 0)
  }
}