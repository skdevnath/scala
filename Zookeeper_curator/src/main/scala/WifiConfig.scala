package com.sandip.common

import SanJson.{ JsonSerializable, JsonSerializableCompanion }
import common.datatype.{ FtConfig, GroupRekeyConfig }

final case class WifiConfig(
                             fastTransition: FtConfig,
                             groupRekey: GroupRekeyConfig) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = WifiConfig
}

object WifiConfig extends JsonSerializableCompanion