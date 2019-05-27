import SanJson.{ JsonSerializable, JsonSerializableCompanion }

final case class UpgradeServiceConfig(
                                       privateUrl: String,
                                       user: String,
                                       password:String) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = UpgradeServiceConfig
}

object UpgradeServiceConfig extends JsonSerializableCompanion
