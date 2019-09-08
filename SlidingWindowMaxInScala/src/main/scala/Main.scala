object Main {
  def main(args: Array[String]): Unit = {
    println("In side main")

    val inArray = Array[Int](1,3,-1,-3,5,3,6,7)
    println("Enter window size: ")
    val windowSz = io.StdIn.readInt()
    val maxList = SlindingWindowMaxSelector.maxArrayFinding(inArray, windowSz)

    println("In array:" )
    inArray.foreach(x => println(x))
    println("Out max array:" )
    maxList.foreach(x => println(x))
  }
}