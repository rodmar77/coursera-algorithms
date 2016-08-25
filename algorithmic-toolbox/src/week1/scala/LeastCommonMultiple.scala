object LeastCommonMultiple {
  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    println(lcm(in.nextLong, in.nextLong))
  }

  def lcm(a: Long, b: Long): Long = (a*b)/gcd(a, b)
  def gcd(a: Long, b: Long): Long = if (b == 0) a else gcd(b, a % b)
}