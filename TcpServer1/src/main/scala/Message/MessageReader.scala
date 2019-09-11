package Message

import net.liftweb
import net.liftweb.json.DefaultFormats
import scala.io.Source

object MessageReader {
  val messageAUnitTest1Part1Filename = "/Users/sandip/tmp/scala/TcpServer1/src/main/resources/MessageAUnitTest1_Part1.json"
  val messageBUnitTest1Part1Filename = "/Users/sandip/tmp/scala/TcpServer1/src/main/resources/MessageBUnitTest1_Part1.json"
  val messageAUnitTest1Part2Filename = "/Users/sandip/tmp/scala/TcpServer1/src/main/resources/MessageAUnitTest1_Part2.json"
  val messageBUnitTest1Part2Filename = "/Users/sandip/tmp/scala/TcpServer1/src/main/resources/MessageBUnitTest1_Part2.json"
  implicit val formats = DefaultFormats

  def readMessageAFromFile(file: String): Array[MessageA] = {
    val msgAfileContents = Source.fromFile(file).getLines.mkString
    val jValue = liftweb.json.parse(msgAfileContents)
    jValue.extract[Array[MessageA]]
  }

  def readMessageBFromFile(file: String): Array[MessageB] = {
    val msgBfileContents = Source.fromFile(file).getLines.mkString
    val jValue = liftweb.json.parse(msgBfileContents)
    jValue.extract[Array[MessageB]]
  }
}
