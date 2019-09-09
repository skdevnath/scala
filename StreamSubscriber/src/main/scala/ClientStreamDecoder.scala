package chatapp.client

import java.net.InetSocketAddress
import akka.actor.{Actor, ActorSystem, Kill, Props}
import akka.io.Tcp._
import _root_.Message.{MessageA, MessageEnvelope}
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
          val messageEnvalop = maybeT[MessageEnvelope](serializer.fromBinary(data.toByteBuffer.array(), manifest = Some(classOf[MessageEnvelope])))

          var respStr = StringBuilder.newBuilder
          messageEnvalop.foreach(x => x.messagesA.foreach(a => respStr.append(MessageA.toString(a))))
          println("Server response:" + respStr)
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
