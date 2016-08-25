package week3.scala

import java.util.Scanner

object MajorityElement {
  def main(args: Array[String]) = {
    val in = new Scanner(System.in)
    println(majorityElement((1 to in.nextInt).map(n => in.nextInt)))
  }

  def majorityElement(xs: Seq[Int]) = {
    if (xs.groupBy(_.toInt).mapValues(_.size).find(_._2 > xs.size/2).isDefined) 1
    else 0
  }
}