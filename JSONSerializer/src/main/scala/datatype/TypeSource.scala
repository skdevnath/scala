package datatype

import SanJson.{ JsonSerializable, JsonSerializableCompanion }
import enumeratum.{ Enum, EnumEntry }

import scala.collection.immutable.IndexedSeq


sealed abstract class TypeSource(override val entryName: String) extends EnumEntry with JsonSerializable {


  override def companion: JsonSerializableCompanion = TypeSource
}

private final case class UnknownTypeSource(name: String) extends TypeSource(name) {
}

object TypeSource extends Enum[TypeSource] with JsonSerializableCompanion {
  val values: IndexedSeq[TypeSource] = findValues

  case object Hostname extends TypeSource("1")
  case object UserAgent extends TypeSource("3")
  case object Oui extends TypeSource("7")
  case object Fingerbank extends TypeSource("6")
  case object YAUAA extends TypeSource("5")
  case object DeviceDetector extends TypeSource("4")
  case object UPnP extends TypeSource("8")
  case object DestHost extends TypeSource("9")

  // Support for undefined / unknown type sources

  override def withNameOption(name: String): Option[TypeSource] =
    namesToValuesMap.get(name).orElse(Some(UnknownTypeSource(name)))

  override def withNameInsensitiveOption(name: String): Option[TypeSource] =
    lowerCaseNamesToValuesMap.get(name.toLowerCase).orElse(Some(UnknownTypeSource(name)))

  override def withNameUppercaseOnlyOption(name: String): Option[TypeSource] =
    upperCaseNameValuesToMap.get(name).orElse(Some(UnknownTypeSource(name)))

  override def withNameLowercaseOnlyOption(name: String): Option[TypeSource] =
    lowerCaseNamesToValuesMap.get(name).orElse(Some(UnknownTypeSource(name)))

  // Serialization

  //override val serializers: Set[Serializer[_]] = Set(EnumSerializer)
}
