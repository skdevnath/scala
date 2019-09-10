package HashDb

import scala.collection.mutable.ListBuffer

case class MessageAEventEntry(
    group: String,
    eventId: Int,
    attributeIndex: Int,
    var listBuffer: Option[ListBuffer[MessageAEventEntry]] = None)
