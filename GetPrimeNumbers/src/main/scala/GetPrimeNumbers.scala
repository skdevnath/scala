import scala.collection.mutable.ArrayBuffer

/**
  * @author sandip@plumewifi.com
  */
object GetPrimeNumbers {

  def main(args: Array[String]) = {
    val primeNumbers = ArrayBuffer.empty[Int]
    primeNumbers.append(2)
    var prime = 3
    while (primeNumbers.length < 100) {
       if (isPrime(prime)) {
         primeNumbers.append(prime)
       }
       prime += 1
    }

    primeNumbers.foreach(x => print(s"$x, "))
  }

  def isPrime(number: Int): Boolean = {
    if (number == 0 || number == 1 || number == 2) {
      false
    } else {
      var modOuter = -1
      val til = Math.sqrt(number).toInt
      var i = 2
      while (modOuter != 0 && i <= til) {
        modOuter = number % i
        i = i + 1
      }
      if (modOuter == 0) {
        false
      } else {
        true
      }
    }
  }

}
