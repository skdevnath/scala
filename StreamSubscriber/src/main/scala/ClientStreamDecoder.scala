package chatapp.client

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorSystem, Props, Kill}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString


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
          println("Server response:" + data.decodeString("US-ASCII"))
        case _: ConnectionClosed =>
          connection ! "connection closed"
          context stop self
      }
  }
}

object Client extends App {
  val system = ActorSystem()
  val tcpclient = system.actorOf(Props(classOf[ClientActor], new InetSocketAddress("localhost", 8080), system))
}
