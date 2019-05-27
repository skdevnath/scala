package com.sandip.common

//import com.sandip.common.json.PackageImports.{ CustomSerializer, JValue, Serializer }
import json.PackageImports.{ CustomSerializer, JValue, Serializer }
import org.json4s.JsonAST.JString


final class InetAddressSerializer private extends CustomSerializer[InetAddress](format => (
  InetAddressSerializer.deserialize,
  InetAddressSerializer.serialize
))

object InetAddressSerializer extends JsonSerializableCompanion {
  override val serializers: Set[Serializer[_]] = Set(new InetAddressSerializer)

  val deserialize: PartialFunction[JValue, InetAddress] = {
    case JString(value) => InetAddress(value).get
  }

  val serialize: PartialFunction[Any, JValue] = {
    case InetAddress(value) => JString(value)
  }
}
