package datatype

import SanJson.{ JsonSerializable, JsonSerializableCompanion }
import org.json4s.Serializer


final case class S3ConfigContainer(sources: IndexedSeq[TypeSourceConfig]) extends JsonSerializable {
  override def companion: JsonSerializableCompanion = S3ConfigContainer

  private val sourceById: Map[String, TypeSourceConfig] = sources.view.map(config => config.id -> config).toMap

  def getSourceConfig(source: TypeSource): TypeSourceConfig = {
    sourceById.getOrElse(source.entryName, TypeSourceConfig.unknown)
  }
}

object S3ConfigContainer extends JsonSerializableCompanion {
  override def serializers: Set[Serializer[_]] = TypeSourceConfig.serializers

  val empty: S3ConfigContainer = {
    S3ConfigContainer(sources = IndexedSeq.empty[TypeSourceConfig])
  }
}
