package myJavaInetAddressTest
import java.net.{ Inet4Address, Inet6Address, InetAddress }

import scala.util.{ Failure, Success, Try }

/**
  * @author sandip@plumewifi.com
  */

object ConvertToOvsdbStr {
  def convert(redirectOpt: Option [String]): Option[String] = {
    redirectOpt.map { redirect =>
      val inetAddrTry = Try(InetAddress.getByName(redirect))
      inetAddrTry match {
        case Success(inet) => inet
          inet match {
            case x:Inet6Address => "4A-" + redirect
            case x:Inet4Address => "A-" + redirect
          }
        case Failure(e) =>
          "C-" + redirect
      }
    }
  }
}
