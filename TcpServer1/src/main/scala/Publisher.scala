
import java.net.InetSocketAddress
import akka.actor.{Actor, ActorSystem, Props}
import akka.io._
import akka.io.Tcp._
import _root_.Message.{MessageA, MessageEnvalop, MessageReader}
import akka.serialization.SerializationExtension
import akka.util.ByteString

class ConnectionManager(address: String, port: Int) extends Actor {
  import context.system
  val inetSocketAddress = new InetSocketAddress(address, port)
  IO(Tcp) ! Bind(self, inetSocketAddress)

  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")
      // Sandip: remove following
      val messagesA = MessageReader.getMessageA
      messagesA.foreach(a => println(MessageA.toString(a)))
    case Connected(remote, local) =>
      println("Connected")
      val handler = context.actorOf(Props[ConnectionHandler])
      println(s"New connnection: $local -> $remote")
      sender() ! Register(handler)
  }
}

class ConnectionHandler extends Actor {
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      println("Server: data rx:" + decoded)
      val messagesA = MessageReader.getMessageA
      val messageEnvalop = MessageEnvalop(messagesA = messagesA.toSeq, messagesB = Seq.empty)

      val serialization = SerializationExtension(Server.system)
      val serializer = serialization.findSerializerFor(messageEnvalop)
      val bytes = serializer.toBinary(messageEnvalop)

      sender() ! Write(ByteString(bytes))
     // sender() ! Write(ByteString(s"You told us: $decoded"))
    case message: ConnectionClosed =>
      println("Connection closed")
      context stop self
  }
}

object Server extends App {
  println("Strating server/publisher")
  val system = ActorSystem()
  val tcpserver = system.actorOf(Props(classOf[ConnectionManager], "localhost", 8080))
}

