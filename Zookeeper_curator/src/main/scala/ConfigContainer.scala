import SanJson.{ JsonSerializable, JsonSerializableCompanion }


final case class ConfigContainer(
                                  upgradeService: UpgradeServiceConfig) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = ConfigContainer
}

object ConfigContainer extends JsonSerializableCompanion