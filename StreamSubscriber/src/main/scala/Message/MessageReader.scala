package Message

import net.liftweb
import net.liftweb.json.DefaultFormats
import scala.io.Source

object MessageReader {
  val messageAFilename = "/Users/sandip/tmp/scala/TcpServer1/src/main/resources/MessageA.json"
  val msgAfileContents = Source.fromFile(messageAFilename).getLines.mkString
  def getMessageA: Array[MessageA] = {
    implicit val formats = DefaultFormats
    val jValue = liftweb.json.parse(msgAfileContents)
    jValue.extract[Array[MessageA]]
  }
}
