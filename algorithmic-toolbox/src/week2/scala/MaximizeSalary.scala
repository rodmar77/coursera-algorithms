object MaximizeSalary {
  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    println(
      (1 to in.nextInt)
        .map(n => in.next)
        .sortWith((a, b) => (a + b).toInt > (b + a).toInt)
        .mkString)
  }
}