object MinimumDotProduct {

  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    val n = in.nextInt
    println((1 to n)
      .map(n => in.nextLong)
      .sorted
      .zip(
        (1 to n)
          .map(n => in.nextLong)
          .sortWith(_ > _))
      .map(n => n._1 * n._2)
      .sum)
  }
}