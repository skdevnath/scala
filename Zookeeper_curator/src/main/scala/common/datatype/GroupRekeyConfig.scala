package common.datatype


import enumeratum._

sealed abstract class GroupRekeyConfig(override val entryName: String) extends EnumEntry

object GroupRekeyConfig extends Enum[GroupRekeyConfig]{
  val values = findValues

  case object Enable extends GroupRekeyConfig("enable")
  case object Disable extends GroupRekeyConfig("disable")
  case object Auto extends GroupRekeyConfig("auto")
}
