package com.sandip

object TryCatchTest {
  val youngCustomer = Customer(15)
  def main(args: Array[String]): Unit = {
    println("In main()")
    try {
      buyCigarettes(youngCustomer)
      println("You got Cigarett")
    } catch {
      case UnderAgeException(msg) => {
        println(msg)
      }
    }
  }

  def buyCigarettes(customer: Customer): Cigarettes = {
    if (customer.age < 18) {
      throw UnderAgeException("Person has to be above or same to 18")
    } else {
      new Cigarettes
    }
  }
}
