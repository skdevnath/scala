package common.datatype

import SanJson.{ FtConfigSerializer, GroupRekeyConfigSerializer, JsonSerializable, JsonSerializableCompanion }
import org.json4s.{ Formats, Serializer }

final case class WifiConfig(
                             fastTransition: FtConfig,
                             groupRekey: GroupRekeyConfig,
                             testInt: Int) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = WifiConfig
}

object WifiConfig extends JsonSerializableCompanion {
  override val serializers: Set[Serializer[_]] = (FtConfigSerializer.serializers ++ GroupRekeyConfigSerializer.serializers)
}