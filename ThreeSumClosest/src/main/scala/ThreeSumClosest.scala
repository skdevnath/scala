/*
Clearly, if we simply test all possible 3-tuples, we'd solve the problem in O(n3) -- that's the brute-force baseline. Is it possible to do better? What if we pick the tuples in a somewhat smarter way?

First, we invest some time to sort the array, which costs us an initial penalty of O(n log n). Now we execute this algorithm:

This algorithm works by placing three pointers, i, j, and k at various points in the array. i starts off at the beginning and slowly works its way to the end. k points to the very last element. j points to where i has started at. We iteratively try to sum the elements at their respective indices, and each time one of the following happens:

The sum is exactly right! We've found the answer.
The sum was too small. Move j closer to the end to select the next biggest number.
The sum was too big. Move k closer to the beginning to select the next smallest number.
For each i, the pointers of j and k will gradually get closer to each other. Eventually they will pass each other, and at that point we don't need to try anything else for that i, since we'd be summing the same elements, just in a different order. After that point, we try the next i and repeat.

Eventually, we'll either exhaust the useful possibilities, or we'll find the solution. You can see that this is O(n2) since we execute the outer loop O(n) times and we execute the inner loop O(n) times.
 */


object ThreeSumClosest {
  def main(args: Array[String]): Unit = {
    val nums = Array[Int](-1,2,1,-4)
    val target = 1
    val sum = threeSumClosest(nums, target)
    println(s"Sum: ${sum}")
  }

  def threeSumClosest(nums: Array[Int], target: Int): Int = {
    val numsSorted = nums.sorted
    var sumOpt:Option[Int] = None
    var j = 0
    var k = 0
    var min = Int.MaxValue
    var sumToReturn = 0
    numsSorted.foreach(x => println(x))
    for  {
      i <- 0 until (numsSorted.size - 1)
      if sumOpt.forall(_ != target)
    } {
      j = i + 1
      k = numsSorted.size - 1
      //println(s"Outside while: i = $i, j = $j, k = $k, sumOpt: $sumOpt")
      while (k > j && sumOpt.forall(_ != target)) {
        val sum = numsSorted(i) + numsSorted(j) + numsSorted(k)
        sumOpt = Some(sum)
        val diff = math.abs(sum - target)
        if (sum >= target)  k = k - 1 else j = j + 1
        if (min > diff) {
          min = diff
          sumToReturn = sum
        }
        //println(s"i = $i, j = $j, k = $k, sum = $sum, sumOpt: $sumOpt, min = $min, diff = $diff")
      }
    }
    sumToReturn
  }
}
