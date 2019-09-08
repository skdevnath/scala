/**
  * @author sandip@plumewifi.com
  */
object HammingDistance {

  /*
  Hamming distance between two strings of equal length is the number of positions
  at which the corresponding symbols are different. In other words, it measures the
   minimum number of substitutions required to change one string into the other,
   or the minimum number of errors that could have transformed one string into the
    other. In a more general context, the Hamming distance is one of several string
     metrics for measuring the edit distance between two sequences.

  The Hamming distance between:

"karolin" and "kathrin" is 3.
"karolin" and "kerstin" is 3.
1011101 and 1001001 is 2.
2173896 and 2233796 is 3.
   */

    def findDistance(firstStr: String, secondStr: String): Option[Int] = {
      println("First Str:" + firstStr + " SecondStr: " + secondStr)
      val retDistance: Option[Int] =
        if (firstStr.length == secondStr.length) {
          var distance = 0
          for (i <- 0 to (firstStr.length - 1)) {
            if (firstStr.charAt(i) != secondStr.charAt(i)) {
              distance += 1
            }
          }
          Some(distance)
        } else {
          None
        }
      retDistance
    }
    def main(args: Array[String]) {
      if (args.size == 2) {
        val distance = findDistance(args(0), args(1))
        println("Distance:" + distance)
      }

    }
}
