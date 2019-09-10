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
