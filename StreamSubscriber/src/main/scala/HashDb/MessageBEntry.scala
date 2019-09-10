package HashDb

import scala.collection.mutable.ListBuffer

case class MessageBEntry(
    attributeIndex: Int,
    originPlace: String,
    var listBuffer: Option[ListBuffer[MessageBEntry]] = None)
