package HashDb

import scala.collection.mutable.ListBuffer

class MessageBEntry(
    attributeIndex: Int,
    originPlace: String,
    listBuffer: Option[ListBuffer[MessageBEntry]] = None)
