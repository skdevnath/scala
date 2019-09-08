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
      // Send a sample sandip message
      val connection = sender()
      connection ! Write(ByteString("Just for test"))
      connection ! Register(self)
      context become {
        case Received(data) =>
          println(data.decodeString("US-ASCII"))
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
