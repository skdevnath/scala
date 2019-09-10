package sandip.client

import HashDb.EventProcessor
import java.net.InetSocketAddress
import akka.actor.{Actor, ActorSystem, Kill, Props}
import akka.io.Tcp._
import _root_.Message.{MessageA, MessageB, MessageEnvelope}
import akka.io.{IO, Tcp}
import akka.serialization.SerializationExtension
import akka.util.ByteString
import scala.util.Try


class ClientActor(address: InetSocketAddress, actorSystem: ActorSystem) extends Actor {

  IO(Tcp)(actorSystem) ! Connect(address)

  def receive = {
    case CommandFailed(command: Tcp.Command) =>
      println("Failed to connect to " + address.toString)
      self ! Kill
      actorSystem.terminate()
    case Connected(remote, local) =>
      println("Successfully connected to " + address)
      val connection = sender()
      connection ! Register(self)
      // Send a sample sandip message
      connection ! Write(ByteString("Just for test"))
      context become {
        case Received(data) =>
          // Get the Serialization Extension
          val serialization = SerializationExtension(Client.system)
          val serializer = serialization.findSerializerFor(MessageEnvelope)
          // Turn it back into an object
          val messageEnvalopOpt = maybeT[MessageEnvelope](serializer.fromBinary(data.toByteBuffer.array(), manifest = Some(classOf[MessageEnvelope])))
          var respStr = StringBuilder.newBuilder
          messageEnvalopOpt.foreach { messageEnvalop =>
            respStr.append("\nMessageA:")
            messageEnvalop.messagesA.foreach(a => respStr.append(MessageA.toString(a)))
            respStr.append("\nMessageB:")
            messageEnvalop.messagesB.foreach(b => respStr.append(MessageB.toString(b)))
            println("Server response:" + respStr)

            println("Output:")
            messageEnvalop.messagesA.foreach(a => EventProcessor.processMessage(a))
            messageEnvalop.messagesB.foreach(b => EventProcessor.processMessage(b))
          }
        case _: ConnectionClosed =>
          connection ! "connection closed"
          context stop self
      }
  }

  @inline def maybeT[T] ( a: Any /*Option[T]*/ ) : Option[T]= Try(a.asInstanceOf[T]).toOption

}

object Client extends App {
  val system = ActorSystem()
  val tcpclient = system.actorOf(Props(classOf[ClientActor], new InetSocketAddress("localhost", 8080), system))
}
