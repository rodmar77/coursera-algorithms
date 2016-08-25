object PartialSumFibonacci {

  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    println(partialSumFib(in.nextLong, in.nextLong))
  }

  def partialSumFib(start: Long, end: Long) = {
    type Matrix = ((Int, Int, Int), (Int, Int, Int), (Int, Int, Int))

    def sumFib(n: Long) = {
      def square(m: Matrix) = mult(m, m)
      def m(n: Int) = n % 10
      def mult(a: Matrix, b: Matrix) = (
        (
          m(m(a._1._1 * b._1._1) + m(a._1._2 * b._2._1) + m(a._1._3 * b._3._1)),
          m(m(a._1._1 * b._1._2) + m(a._1._2 * b._2._2) + m(a._1._3 * b._3._2)),
          m(m(a._1._1 * b._1._3) + m(a._1._2 * b._2._3) + m(a._1._3 * b._3._3))),
        (
          m(m(a._2._1 * b._1._1) + m(a._2._2 * b._2._1) + m(a._2._3 * b._3._1)),
          m(m(a._2._1 * b._1._2) + m(a._2._2 * b._2._2) + m(a._2._3 * b._3._2)),
          m(m(a._2._1 * b._1._3) + m(a._2._2 * b._2._3) + m(a._2._3 * b._3._3))),
        (
          m(m(a._3._1 * b._1._1) + m(a._3._2 * b._2._1) + m(a._3._3 * b._3._1)),
          m(m(a._3._1 * b._1._2) + m(a._3._2 * b._2._2) + m(a._3._3 * b._3._2)),
          m(m(a._3._1 * b._1._3) + m(a._3._2 * b._2._3) + m(a._3._3 * b._3._3))))
  
      def pow(m: Matrix, p: Long): Matrix = {
        if (p <= 1) m
        else if (p == 2) square(m)
        else if ((p % 2) == 0) pow(square(m), p / 2);
        else mult(m, pow(square(m), (p - 1) / 2));
      }
  
      pow(((1, 1, 0), (1, 0, 0), (1, 0, 1)), n + 1)._3._2
    }

    val (prefix, suffix) = (sumFib(start - 1), sumFib(end))
    if (suffix < prefix) (10 + suffix) - prefix
    else suffix - prefix
  }
}