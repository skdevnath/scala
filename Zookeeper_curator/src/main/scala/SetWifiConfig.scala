import org.apache.curator.framework.CuratorFramework
import org.apache.zookeeper.KeeperException
import org.apache.zookeeper.data.Stat

import scala.util.{ Failure, Success, Try }

object setWifiConfig {
  def setWifiConfig(client: CuratorFramework, path: String): Try[Stat] = {
    val wifiConfig = s"""{ "fastTransition": "enable", "groupRekey": "auto"}""".replaceAll("\\s+", "")
    println(s"Setting WifiConfig(${path}) = ${wifiConfig}")
    val returnTry = Try(client.setData().forPath(path, wifiConfig.getBytes))
    returnTry match {
      case Success(stat) => println(s"Setting success ${stat}")
      case Failure(e) => e match {
        case ke: KeeperException.NoNodeException => {
          println(s"Setting failed ${ke}")
          val createTry = Try(client.create.creatingParentsIfNeeded.forPath(path, wifiConfig.getBytes()))
          createTry match {
            case Success(cstat) => println(s"Node creation, succeed ${cstat}")
            case Failure(cKe) => println(s"Node creation failed ${cKe}")
          }
        }
        case _ => println(s"Setting failed ${e}")
      }
    }
    returnTry
  }
}
