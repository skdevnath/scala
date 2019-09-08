import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import java.net.InetSocketAddress // implicitly used by IO(Tcp)


object Client {
  def props(remote: InetSocketAddress, replies: ActorRef) =
    Props(classOf[Client], remote, replies)

  def main(args: Array[String]) = {

    val system = ActorSystem("HelloSystem")
    val listener = system.actorOf(Props(classOf[Listener]))
    val clientActor = system.actorOf(props(new InetSocketAddress("localhost", 8080), listener))
  }
}

class Listener extends Actor {
  def receive = {
    case "hello" => println("Listener: hello back at you")
    case _ => println("Listener: huh?:" + _)
  }
}


class Client(remote: InetSocketAddress, listener: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Connect(remote)

  def receive = {
    case CommandFailed(_: Connect) =>
      listener ! "connect failed"
      context.stop(self)

    case c @ Connected(remote, local) =>
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context.become {
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          listener ! data
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed"
          context.stop(self)
      }
  }
}
