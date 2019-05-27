import common.datatype.{ FtConfig, GroupRekeyConfig, WifiConfig }
import datatype.S3ConfigContainer
import org.json4s.Formats
import org.json4s.jackson.Serialization

import scala.concurrent.Future
import scala.io.Source
import scala.util.{ Failure, Success }

import scala.concurrent.ExecutionContext.Implicits.global


object JsonTestMain {
  val myConfig = Source.fromFile("/Users/sandip/tmp/JSONSerializer/project/config.Sandip_DNS.txt").mkString
  private def loadConfig(): Future[S3ConfigContainer] = {
    implicit val formats: Formats = S3ConfigContainer.formats


    val requestTry = Future(Serialization.read[S3ConfigContainer](myConfig)(formats, implicitly))

    requestTry
  }

  def main(args: Array[String]): Unit = {
    println("Inside Json test main")
    val jsonStr = s"""{"fastTransition":"enable","groupRekey":"auto"}}""".replaceAll("\\s+", "")

    val ft: FtConfig = FtConfig.Disable
    val gtk: GroupRekeyConfig = GroupRekeyConfig.Disable
    val wifiConfig = WifiConfig(fastTransition = ft, groupRekey = gtk, testInt = 23)
    //implicit val formts = FtConfigSerializer.formats
    //import FtConfigSerializer.formats
    import WifiConfig.formats
    println(s"ft = ${Serialization.write(ft)}")
    println(s"groupRekey = ${Serialization.write(gtk)}")
    println(s"wifiConfig = ${wifiConfig.toJson}")

    //val deSerialWifiConfig = Serialization.read[WifiConfig](jsonStr)
    //println(s"Desrialized one ${deSerialWifiConfig}")


    // Test person
    PersonTest.testPerson


    // Test map serializer
    val configMap = loadConfig()

    configMap onComplete {
      case Success(value) =>  println(s"Values: ${value}")
      case Failure(e) =>  println(s"failed in configMap de-serializer: ${e}")
    }

    Thread.sleep(2000)
  }

}
