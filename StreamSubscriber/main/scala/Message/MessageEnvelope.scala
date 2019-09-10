package Message

/* Message envalop to have both kind of message in single channel/stream */
@SerialVersionUID(100L)
case class MessageEnvelope (messagesA: Seq[MessageA], messagesB: Seq[MessageB]) extends Serializable
