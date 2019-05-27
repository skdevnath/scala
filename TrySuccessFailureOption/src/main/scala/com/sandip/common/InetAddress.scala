package com.sandip.common

import scala.util.{ Failure, Success, Try }

// TODO: consider more efficient implementation
final class InetAddress private(val value: String, val intValue: Int) {
  import InetAddress._

  def asTuple: (Int, Int, Int, Int) =
    (getOctet(Octet.First), getOctet(Octet.Second), getOctet(Octet.Third), getOctet(Octet.Fourth))

  override def equals(that: Any): Boolean = that match {
    case that: InetAddress => (that.value == this.value)
    case _ => false
  }

  @inline final def getOctet(octet: Octet): Int = (intValue & octet.mask) >>> octet.offset

  @inline final def setOctet(octet: Octet, value: Int): Try[InetAddress] = {
    if (value >= 0  && value <= 255) {
      Success(InetAddress.fromInt((intValue & ~octet.mask) | ((value << octet.offset) & octet.mask)))
    } else {
      Failure(new IllegalArgumentException("Supplied octet must be between 0 and 255: " + value))
    }
  }

  def masked(mask: InetAddress): InetAddress = InetAddress.fromInt(intValue & mask.intValue)

  override def hashCode: Int = value.hashCode

  override def toString: String = value
}

object InetAddress {

  sealed abstract class Octet(val mask: Int, val offset: Int)

  /* Most significant to least significant */
  object Octet {
    case object First extends Octet(0xff000000, 24)
    case object Second extends Octet(0x00ff0000, 16)
    case object Third extends Octet(0x0000ff00, 8)
    case object Fourth extends Octet(0x000000ff, 0)
  }

  private val ValidationPattern = """^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$""".r

  private val ExtractionPattern = """(\d+)\.(\d+)\.(\d+)\.(\d+)""".r

  private val UnknownAddress = "0.0.0.0"

  def parse(value: String): Try[InetAddress] = value match {
    case ExtractionPattern(a, b, c, d) =>
      fromOctets(a.toInt, b.toInt, c.toInt, d.toInt)
        .map(Success(_))
        .getOrElse(invalidValueFailure(value))

    case _ => invalidValueFailure(value)
  }

  private def invalidValueFailure(value: String): Failure[InetAddress] = {
    Failure(new IllegalArgumentException("Supplied string is not a valid IPv4 address: " + value))
  }

  def apply(value: String): Option[InetAddress] = value match {
    case ExtractionPattern(a, b, c, d) => fromOctets(a.toInt, b.toInt, c.toInt, d.toInt)
    case _ => None
  }

  def unapply(inetAddress: InetAddress): Option[String] = Some(inetAddress.value)

  def fromInt(intValue: Int): InetAddress = {
    val a = ((intValue & 0xff000000) >>> 24).toString
    val b = ((intValue & 0x00ff0000) >>> 16).toString
    val c = ((intValue & 0x0000ff00) >>> 8).toString
    val d = (intValue & 0x000000ff).toString
    new InetAddress(s"$a.$b.$c.$d", intValue)
  }

  def fromOctets(a: Int, b: Int, c: Int, d: Int): Option[InetAddress] = {
    if (isValid(a, b, c, d)) {
      val value = s"$a.$b.$c.$d"
      val intValue = (a << 24) | (b << 16) | (c << 8) | d
      Some(new InetAddress(value, intValue))
    } else {
      None
    }
  }

  def isValid(value: String): Boolean = ValidationPattern.findFirstIn(value).isDefined

  def isValid(a: Int, b: Int, c: Int, d: Int): Boolean =
    a >= 0 && a <= 255 && b >= 0 && b <= 255 && c >= 0 && c <= 255 && d >= 0 && d <= 255

  val Unknown = InetAddress(UnknownAddress).get
}
