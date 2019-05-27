package common.datatype


import enumeratum._

sealed abstract class FtConfig(override val entryName: String) extends EnumEntry

object FtConfig extends Enum[FtConfig] {
  val values = findValues

  case object Enable extends FtConfig("enable")
  case object Disable extends FtConfig("disable")
  case object Auto extends FtConfig("auto")
}