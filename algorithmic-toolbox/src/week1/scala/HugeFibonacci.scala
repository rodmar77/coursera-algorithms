import scala.io.StdIn._

object HugeFibonacci {
  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    val (number, mod) = (in.nextLong, in.nextInt)
    val index = number % getPisano(mod);
    println(fibonacciMod(index.toInt, mod));
  }

  def getPisano(mod: Int): Long = {
    def sumFibonacciMod(start: Int, end: Int, mod: Long) = {
      def sumFibonacciMod(curr: Int, total: Long): Long = {
        if (curr == end) total
        else sumFibonacciMod(curr + 1, total + fibonacciMod(curr, mod))
      }

      sumFibonacciMod(start, 0)
    }

    def getLowerBound(number: Int) = {
      def getLucasNumberIndex(max: Int) = {
        def getLucasNumberIndex(a: Int, b: Int, index: Int): Int = {
          if (b >= max) index
          else getLucasNumberIndex(b, a + b, index + 1)
        }

        getLucasNumberIndex(2, 1, 0);
      }

      2 * getLucasNumberIndex(number)
    }

    def getPisano(ac: Long, c: Long, ap: Long, p: Long, l: Int): Long = {
      if ((ac == ap) && (c == p)) l
      else getPisano(
        fibonacciMod(l, mod),
        c + fibonacciMod(l, mod),
        fibonacciMod(2 * l + 2, mod),
        p - fibonacciMod(l + 1, mod) + fibonacciMod(2 * l + 1, mod) + fibonacciMod(2 * l + 2, mod),
        l + 1)
    }

    val l = getLowerBound(mod)
    getPisano(0, sumFibonacciMod(0, l, mod), 0, sumFibonacciMod(l + 1, 2 * l + 1, mod), l) + 1
  }

  val cache = scala.collection.mutable.Map[Int, Long]()
  def fibonacciMod(n: Int, mod: Long) = {
    def fibonacciMod(index: Int): Long = {
      cache.getOrElseUpdate(index,
        if (index == 0) 0
        else if (index == 1) 1
        else (fibonacciMod(index - 1) + fibonacciMod(index - 2)) % mod);
    }

    fibonacciMod(n)
  }
}