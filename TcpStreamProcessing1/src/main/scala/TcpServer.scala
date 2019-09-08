import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import java.net.InetSocketAddress // implicitly used by IO(Tcp)

class Server extends Actor {

  import Tcp._
  import context.system

  println("in Server")
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8080))
  println("After Bind")

  def receive = {
    case b @ Bound(localAddress) =>
      context.parent ! b

    case CommandFailed(_: Bind) => context.stop(self)

    case c @ Connected(remote, local) =>
      val handler = context.actorOf(Props[SimplisticHandler])
      val connection = sender()
      connection ! Register(handler)
  }

}

class SimplisticHandler extends Actor {
  import Tcp._
  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed     => context.stop(self)
  }
}

object ServerApp {

  def main(args: Array[String]) = {

    println("in main")
    val system = ActorSystem("HelloSystem")
    val serverActorRef = system.actorOf(Props(classOf[Server]))
  }
}

