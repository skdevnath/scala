
import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props, Actor}
import akka.io._
import akka.io.Tcp._
import akka.util.ByteString

class TCPConnectionManager(address: String, port: Int) extends Actor {
  import context.system
  println("Sandip: Invoked TCPConnectionManager")
  val inetSocketAddress = new InetSocketAddress(address, port)
  println("Sandip: After inetSocketAddress")
  IO(Tcp) ! Bind(self, inetSocketAddress)
  println("Sandip: after bind")

  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")
    case Connected(remote, local) =>
      println("Sandip: Connected")
      val handler = context.actorOf(Props[TCPConnectionHandler])
      println(s"New connnection: $local -> $remote")
      sender() ! Register(handler)
  }
}

class TCPConnectionHandler extends Actor {
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      sender() ! Write(ByteString(s"You told us: $decoded"))
    case message: ConnectionClosed =>
      println("Connection has been closed")
      context stop self
  }
}

object Server extends App {
  println("Sandip: strating server/publisher")
  val system = ActorSystem()
  val tcpserver = system.actorOf(Props(classOf[TCPConnectionManager], "localhost", 8080))
}
