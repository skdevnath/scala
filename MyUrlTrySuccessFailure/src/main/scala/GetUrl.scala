package myUrl

import scala.util.{Try, Failure, Success}
import java.net.URL

case class GetUrl(url: String) {

  def parseUrl: Try[URL] = {
    Try(new URL(url))
  }
}
