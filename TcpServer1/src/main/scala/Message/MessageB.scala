package Message

/*
    {
       "attributeIndex": 78,
       "origin": "Italy"
     }
     {
       "attributeIndex": 54,
       "origin": "Greece"
      }
 */
@SerialVersionUID(100L)
case class MessageB(attributeIndex: Int, origin: String) extends Serializable

object MessageB {
  def toString(messageB: MessageB) = {
    val stringBuilder = StringBuilder.newBuilder
    stringBuilder.append(s"{ attributeIndex: ${messageB.attributeIndex}, origin: ${messageB.origin}}")
    stringBuilder.toString
  }
}