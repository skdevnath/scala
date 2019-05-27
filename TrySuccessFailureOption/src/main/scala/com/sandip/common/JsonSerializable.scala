package com.sandip.common

import org.json4s._
import org.json4s.jackson.Serialization

trait JsonSerializable {
  implicit def jsonFormats = companion.formats

  def companion: JsonSerializableCompanion

  def toJson: String = Serialization.write(this)

  override def toString: String = this.getClass.getSimpleName + "(" + toJson + ")"
}

trait JsonSerializableCompanion {
  def serializers: Set[Serializer[_]] = Set.empty[Serializer[_]]

  // must be lazy to avoid initialization ordering
  implicit lazy val formats: Formats = Serialization.formats(NoTypeHints) ++ serializers
}