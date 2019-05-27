package SanJson

//import SanJson.json.PackageImports.Serializer
import common.datatype.FtConfig
import enumeratum.Json4s
import org.json4s.Serializer

object FtConfigSerializer extends JsonSerializableCompanion {
  override val serializers: Set[Serializer[_]] = Set(Json4s.serializer(FtConfig))
}
