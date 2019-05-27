package SanJson

object json {
  private object PackageImports {
    type Serializer[A] = org.json4s.Serializer[A]

    type JInt = org.json4s.JInt
    val JInt = org.json4s.JInt

    type JString = org.json4s.JString
    val JString = org.json4s.JString

    val JNothing = org.json4s.JNothing

    val JNull = org.json4s.JNull

    type JValue = org.json4s.JValue

    type CustomSerializer[A] = org.json4s.CustomSerializer[A]

    type JsonSerializableCompanion = SanJson.JsonSerializableCompanion

    val Json4s = enumeratum.Json4s
  }
}
