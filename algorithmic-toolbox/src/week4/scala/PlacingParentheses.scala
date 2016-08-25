object PlacingParentheses {

  def main(args: Array[String]) = {
    val in = new java.util.Scanner(System.in)
    println(maxValue(in.next))
  }

  def maxValue(s: String) = {
    import scala.collection.mutable.Map

    val (min, max) = (Map[(Int, Int), BigInt](), Map[(Int, Int), BigInt]())
    val (ns, ops) = (
      """(\d)""".r.findAllIn(s).matchData.map(_.group(1).toInt).toList,
      """(-|\+|\*|/)""".r.findAllIn(s).matchData.map(_.group(1).head).toList)

    def placeParenthesis = {
      def minAndMax(i: Int, j: Int) = {
        def op(op: Char): ((BigInt, BigInt) => BigInt) = op match {
          case '+' => _ + _
          case '-' => _ - _
          case '*' => _ * _
          case _   => _ / _
        }

        def minValue(ns: BigInt*) = ns.min
        def maxValue(ns: BigInt*) = ns.max
        def minAndMax(r: Int, k: Int, currMin: BigInt, currMax: BigInt): (BigInt, BigInt) = {
          if (k == j) (currMin, currMax)
          else {
            val (a, b, c, d) = (
              op(ops(k - 1))(max((i, k)), max((k + 1, j))),
              op(ops(k - 1))(max((i, k)), min((k + 1, j))),
              op(ops(k - 1))(min((i, k)), max((k + 1, j))),
              op(ops(k - 1))(min((i, k)), min((k + 1, j))))

            if (r == 0) minAndMax(r + 1, k + 1, minValue(a, b, c, d),          maxValue(a, b, c, d))
            else        minAndMax(r + 1, k + 1, minValue(a, b, c, d, currMin), maxValue(a, b, c, d, currMax))
          }
        }

        minAndMax(0, i, 0, 0)
      }

      (1 to ns.size).foreach(i => {
        min += (i, i) -> ns(i - 1)
        max += (i, i) -> ns(i - 1)
      })

      (1 until ns.size).foreach(s => {
        (1 to ns.size - s).foreach(i => {
          val j = i + s
          val minmax = minAndMax(i, j)
          min += (i, j) -> minmax._1
          max += (i, j) -> minmax._2
        })
      })

      max(1, ns.size)
    }

    placeParenthesis
  }
}