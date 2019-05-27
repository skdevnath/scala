package com.sandip.common


import org.json4s.Serializer

import scala.util.{ Failure, Success, Try }

final class Subnet private(
   val subnet: InetAddress,
   val mask: InetAddress) extends JsonSerializable {

  override def companion: JsonSerializableCompanion = Subnet

  @inline final def containsAddress(ip: InetAddress): Boolean =
    (subnet.intValue & mask.intValue) == (ip.intValue & mask.intValue)

  def matches(that: Subnet): Boolean = (mask == that.mask) && containsAddress(that.subnet)
}

object Subnet extends JsonSerializableCompanion {
  override val serializers: Set[Serializer[_]] = InetAddressSerializer.serializers

  def apply(subnet: InetAddress, mask: InetAddress): Try[Subnet] = {
    println(s"inside Apply")
    if (isMaskValid(mask)) {
      Success(new Subnet(subnet, mask))
    } else {
      Failure(new IllegalArgumentException("Supplied mask is not a valid: " + mask.toString))
    }
  }

  //def unapply(arg: Subnet): Option[(InetAddress, InetAddress)] =

  private def isMaskValid(mask: InetAddress): Boolean = {
    var maskInt = mask.intValue
    while(maskInt <= 0) {
      maskInt = maskInt << 1
    }

    (maskInt == 0)


    /*
    var bitValueToCheck = 1
    var noError = true
    for (i <- 31 to 0 by -1 if noError) {
      if (((mask.intValue >> i) & 1) == bitValueToCheck) {
        println(s"bit $i is valid")
      } else {
        println(s"bit $i is changed")
        if (bitValueToCheck == 1) {
          bitValueToCheck = 0
        } else {
          return Failure(new IllegalArgumentException("Supplied mask is not a valid: " + mask.toString))
        }
      }
    }
    */
  }
}