import scala.util.Try
/**
  * Here is the document
  * https://drive.google.com/drive/folders/0B9bBF10EeCBsMFgzNmRzNllMOTQ
  */

object MainMethod {
  val data = Array(1,2,3,4,5,6)
  def main(args: Array[String]): Unit = {
    if (args.size != 2) {
      println("Please specify value of n and r")
    } else {
      val n = Try(args(0).toInt).getOrElse(-1)
      val r = Try(args(1).toInt).getOrElse(-1)
      if (n == -1 || r == -1) {
        println(s"Bad input for n = ${n}, r = ${r}. Enter only positive integer")
      } else {
        println(s"Got input for n = ${n}, r = ${r}")
      }

      val combinations = new Array[Int](r)
      fillCombination(combinations, r - 1, 0, 5)
    }
  }

  def fillCombination(combinations: Array[Int], combEnd: Int, startMain: Int, endMain: Int): Unit = {
    for(i <- (startMain until endMain)) {
      combinations(combEnd) = data(i)
      if (combEnd - 1 >= 0) {
        fillCombination(combinations, combEnd - 1, i + 1, endMain)
      } else {
        print("(")
        for (j <- (0 until combinations.size)) {
          print(s"${combinations(j)},")
        }
        print(")\n")
        val  s = "sandip"
        s.substring(2,3)
      }
    }
  }
}
