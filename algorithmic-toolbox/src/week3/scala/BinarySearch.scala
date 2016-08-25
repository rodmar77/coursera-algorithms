package week3.scala

import java.util.Scanner

object BinarySearch {
  def main(args: Array[String]) = {
    val in = new Scanner(System.in)
    val a = readList(in).sortBy(_._2)
    println(readList(in).map(n => binarySearch(a, n._2)).mkString(" "))
  }

  def readList(in: Scanner) = (0 until in.nextInt).map((_, in.nextLong))
  def binarySearch(l: Seq[(Int, Long)], k: Long): Int = {
    def binarySearch(lo: Int, hi: Int): Int = {
      val mid = (lo + hi) / 2

      if (lo > hi) -1
      else if (lo == hi) (if (l(lo)._2 == k) l(lo)._1 else -1)
      else if (l(mid)._2 == k) l(mid)._1
      else if ((l(mid)._2 > k) || (mid == lo)) binarySearch(lo, mid) 
      else binarySearch(mid, hi)
    }

    binarySearch(0, l.length)
  }
}