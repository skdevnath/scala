import scala.util.{Failure, Try}
import scala.math._

/**
  */
object ConvertTo7Base {
  val encode7 = Map(10 -> "7", 0 -> "0", 1 -> "a", 2 -> "t", 3 -> "l", 4 -> "s", 5 -> "i", 6 -> "N")
  def main(args: Array[String]) {
    var digitAfterErrHdl: Try[Int] = Try(0)
    do {
      println("Enter a decimal number: ")
      val enteredDigit = Try(scala.io.StdIn.readInt())
      digitAfterErrHdl = enteredDigit.recoverWith {
        case e: Exception => println(s"Error: ${e.getMessage}")
          Failure(e)
      }
    } while(digitAfterErrHdl.isFailure)
    for {
      finalDigit <- digitAfterErrHdl
    } {
      println(s"Got this: ${finalDigit}")
      println(s"Converted value: ${convert(finalDigit, 7)}")
    }
  }

  def convert(digit: Int, base: Int): String = {
   var digitVar = digit
   var retStr = ""
    while (digitVar > 0)  {
      val remainder = abs(digitVar % base)
      retStr = encode7(remainder) + retStr
      digitVar = digitVar/base
    }
    retStr
  }
}
