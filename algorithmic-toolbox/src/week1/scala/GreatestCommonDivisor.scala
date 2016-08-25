import scala.io.StdIn._

object GreatestCommonDivisor {

  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    println(gcd(in.nextLong, in.nextLong))
  }

  def gcd(a: Long, b: Long): Long = if (b == 0) a else gcd(b, a % b);
}