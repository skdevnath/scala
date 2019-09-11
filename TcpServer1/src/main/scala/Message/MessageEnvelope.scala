package Message

/* Message envalop to have both kind of message in single channel/stream */
@SerialVersionUID(100L)
case class MessageEnvelope (messagesA: Seq[MessageA] = Seq.empty[MessageA], messagesB: Seq[MessageB] = Seq.empty[MessageB]) extends Serializable
