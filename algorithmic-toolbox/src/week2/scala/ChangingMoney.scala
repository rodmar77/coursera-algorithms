import scala.io.StdIn._

object ChangingMoney {

  def main(args: Array[String]) = {
    println(totalCoins(readInt))
  }

  def totalCoins(c: Int): Int = {
    if (c >= 10) (c/10) + totalCoins(c%10)
    else if (c >= 5) 1 + totalCoins(c - 5)
    else c
  }
}