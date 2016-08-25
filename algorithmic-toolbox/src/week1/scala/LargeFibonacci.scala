import scala.io.StdIn._

object LargeFibonacci {

  def main(args: Array[String]) = {
    println(largeFib(readInt))
  }

  def largeFib(n: Int): Int = {
    def largeFib(p: (Int, Int), c: Int): Int = {
      if (c == n) (p._1 % 10)
      else largeFib((p._2, (p._1 + p._2) % 10), c + 1)
    }
    
    largeFib((0, 1), 0)
  }
}