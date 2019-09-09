package Message

/*
{
       "group": "Update",
       "events": [
           {
               "eventId": 123,
               "attributeIndex": 54
           },
           {
               "eventId": 234,
               "attributeIndex": 78
           }
       ]
}
 */

@SerialVersionUID(100L)
case class Event(eventId: Int, attributeIndex: Int) extends Serializable

@SerialVersionUID(100L)
case class MessageA(group: String, events: Array[Event]) extends Serializable

object MessageA {
  def toString(messageA: MessageA): String = {
    val stringBuilder = StringBuilder.newBuilder
    stringBuilder.append(s"\n{ group: ${messageA.group}")
    messageA.events.foreach{ e =>
      stringBuilder.append(s"\n\t { eventId: ${e.eventId}, attributeIndex: ${e.attributeIndex}},")
    }
    stringBuilder.append("}\n")
    stringBuilder.toString
  }
}
