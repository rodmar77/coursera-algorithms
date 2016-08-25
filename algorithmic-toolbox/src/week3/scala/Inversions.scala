package week3.scala

import java.util.Scanner

object Inversions {
  def main(args: Array[String]) = {
    val in = new Scanner(System.in)
    println(inversionCount((1 to in.nextInt).map(n => in.nextInt).toList))
  }

  def inversionCount(ns: List[Int]) = {
    def sort(xs: List[Int]): (List[Int], Long) = xs match {
      case x :: Nil => (xs, 0)
      case ns => ns.splitAt(ns.length / 2) match {
        case (a, b) => merge(sort(a), sort(b))
      }
    }

    def merge(a: (List[Int], Long), b: (List[Int], Long)): (List[Int], Long) = {
      def merge(left: List[Int], right: List[Int], acc: (List[Int], Long)): (List[Int], Long) = {
        if (left.isEmpty) (acc._1 ++ right, acc._2)
        else if (right.isEmpty) (acc._1 ++ left, acc._2)
        else if (right.head < left.head) merge(left, right.tail, (acc._1 :+ right.head, acc._2 + left.size))
        else merge(left.tail, right, (acc._1 :+ left.head, acc._2))
      }

      merge(a._1, b._1, (Nil, a._2 + b._2))
    }

    sort(ns)._2
  }
}