package SanJson

import common.datatype.GroupRekeyConfig
import enumeratum.Json4s
import org.json4s.Serializer


object GroupRekeyConfigSerializer extends JsonSerializableCompanion {
  override val serializers: Set[Serializer[_]] = Set(Json4s.serializer(GroupRekeyConfig))
}
