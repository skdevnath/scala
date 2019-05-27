package datatype

import SanJson.{ JsonSerializable, JsonSerializableCompanion }


final case class TypeSourceConfig(
    id: String,
    name: String,
    weight: Double,
    canSkipVoting: Boolean) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = TypeSourceConfig
}

object TypeSourceConfig extends JsonSerializableCompanion {
  val unknown: TypeSourceConfig = {
    TypeSourceConfig(
      id = "",
      name = "Unknown",
      weight = 1,
      canSkipVoting = false)
  }
}
