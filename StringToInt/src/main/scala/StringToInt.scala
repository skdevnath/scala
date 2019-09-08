/**
  * https://leetcode.com/problems/string-to-integer-atoi/
  */
object Solution {
  def myAtoi(str: String): Int = {
    val trimStr = str.trim
    var digitStart = 0
    val sign = if (trimStr.length > 0 ) {
      if (trimStr.charAt(0) == '-') {
        digitStart += 1
        -1
      } else if (trimStr.charAt(0) == '+') {
        digitStart += 1
        +1
      } else {
        +1
      }
    } else {
      0
    }

    // Find digit end
    var i = digitStart
    var digitEnd = digitStart
    while ((i < trimStr.length) && trimStr(i).isDigit) {
      digitEnd += 1
      i += 1
    }

    var value:Long = 0
    var tenPower = 0
    println(s"digitStart = ${digitStart}, digitEnd = ${digitEnd}")
    for {
      i <- (digitEnd - 1) to digitStart by -1
      if value < Int.MaxValue
      if value >= 0
    } {
      val digit = trimStr.charAt(i).toInt - '0'.toInt
      val digitValue:Long = digit * math.pow(10, tenPower).toLong
      value = value + digitValue
      tenPower += 1
      println(s"Final value = ${value}")
    }
    if (value < 0) {
      value = Int.MaxValue
    } else {
      value = value * sign
    }
    if (value < Int.MinValue) {
      Int.MinValue
    } else if (value > Int.MaxValue) {
      Int.MaxValue
    } else {
      value.toInt
    }
  }
}

