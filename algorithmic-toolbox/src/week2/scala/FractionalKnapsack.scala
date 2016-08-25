object FractionalKnapsack {

  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    val (n, max) = (in.nextInt, in.nextInt)
    println(
      getMaxValue(
        (1 to n)
          .map(n => (in.nextDouble, in.nextInt))
          .sortWith((a, b) => if (a._2*b._1 == a._1*b._2) (a._1 > b._1) else (a._2*b._1 < a._1*b._2)),
        max))
  }

  def getMaxValue(l: Seq[(Double, Int)], max: Int) = {
    def getMax(curr: Seq[(Double, Int)], accWeight: Double, accAmount: Int): Double = {
      if ((curr.isEmpty) || (accAmount == max)) accWeight
      else {
        val h = curr.head
        if (accAmount + h._2 <= max) getMax(curr.tail, accWeight + h._1, accAmount + h._2)
        else accWeight + (h._1*(max-accAmount))/h._2
      }
    }

    (math rint getMax(l, 0, 0) * 100000) / 100000
  }
}