
import java.net.InetSocketAddress
import akka.actor.{Actor, ActorSystem, Props}
import akka.io._
import akka.io.Tcp._
import _root_.Message.{MessageA, MessageB, MessageEnvelope, MessageReader}
import akka.serialization.SerializationExtension
import akka.util.ByteString

class ConnectionManager(address: String, port: Int) extends Actor {
  import context.system
  import MessageReader._
  val inetSocketAddress = new InetSocketAddress(address, port)
  IO(Tcp) ! Bind(self, inetSocketAddress)

  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")

    case Connected(remote, local) =>
      println("Connected")
      val handler = context.actorOf(Props[ConnectionHandler])
      println(s"New connnection: $local -> $remote")
      sender() ! Register(handler)
  }
}

class ConnectionHandler extends Actor {
  import MessageReader._
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      println("Server: data rx:" + decoded)
      var messageEnvalop = MessageEnvelope(messagesA = readMessageAFromFile(messageAUnitTest1Part1Filename), messagesB = readMessageBFromFile(messageBUnitTest1Part1Filename))
      serializeAndSendToSender(messageEnvalop)

      /* Now send 2nd sets of messages first B and then A*/
      messageEnvalop = MessageEnvelope(messagesB = readMessageBFromFile(messageBUnitTest1Part2Filename))
      serializeAndSendToSender(messageEnvalop)

      messageEnvalop = MessageEnvelope(messagesA = readMessageAFromFile(messageAUnitTest1Part2Filename))
      serializeAndSendToSender(messageEnvalop)
    case message: ConnectionClosed =>
      println("Connection closed")
      context stop self
  }

  private def serializeAndSendToSender(messageEnvelope: MessageEnvelope): Unit = {
    val serialization = SerializationExtension(Server.system)
    val serializer = serialization.findSerializerFor(messageEnvelope)
    var bytes = serializer.toBinary(messageEnvelope)

    // Print msg for debug purpose
    println("\nSending messages:")
    println("MessageA:")
    messageEnvelope.messagesA.foreach(a => println(MessageA.toString(a)))
    println("MessageB:")
    messageEnvelope.messagesB.foreach(b => println(MessageB.toString(b)))


    sender() ! Write(ByteString(bytes))
  }
}

object Server extends App {
  println("Strating server/publisher")
  val system = ActorSystem()
  val tcpserver = system.actorOf(Props(classOf[ConnectionManager], "localhost", 8080))
}

