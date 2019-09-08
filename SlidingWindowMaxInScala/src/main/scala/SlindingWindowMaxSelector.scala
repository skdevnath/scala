import scala.collection.mutable.ArrayBuffer

/**
  * @author sandip@plumewifi.com
  */
object SlindingWindowMaxSelector {

  def maxArrayFinding(inList: Array[Int], windowSz: Int): Array[Int] = {
    val retMaxList = ArrayBuffer.empty[Int]
    if (windowSz > 0 && windowSz < inList.size) {
      val inListSize = inList.size
      val iterationElements = inListSize - windowSz

      val tmpWindowArray = new Array[Int](windowSz)
      for (i <- 0 to iterationElements) {
        var maxValue = 0
        val start = i
        for (j <- 0 to tmpWindowArray.size - 1) {tmpWindowArray(j) = inList(start + j)}
        val sortedSubArray = tmpWindowArray.sortWith((a, b) => (a > b))
        if (sortedSubArray.size > 0) {
          retMaxList.append(sortedSubArray(0))
        }
      }
    } else {
      println(s"Window Size is not proper: ${windowSz}, in array size: ${inList.size}")
    }
    retMaxList.toArray
  }
}
