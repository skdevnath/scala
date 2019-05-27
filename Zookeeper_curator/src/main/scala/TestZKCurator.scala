import com.sandip.common._

import org.apache.curator.framework.{ CuratorFramework, CuratorFrameworkFactory }
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.framework.recipes.cache.{ NodeCache, NodeCacheListener, PathChildrenCache, TreeCache }
import org.apache.curator.x.async.{ AsyncCuratorFramework, AsyncEventException }
import org.json4s.jackson.Serialization

import scala.util.{ Failure, Success, Try }
/*
import org.apache.curator.x.async.AsyncCuratorFramework
import org.apache.curator.x.async.AsyncEventException
import org.apache.curator.x.async.WatchMode
import org.apache.zookeeper.WatchedEvent
import java.util.concurrent.CompletionStage
*/


class TestZKCurator {
  val zkServers = "zookeeper-development-01.shared.us-west-2.aws.plume.tech:2181," +
                  "zookeeper-development-02.shared.us-west-2.aws.plume.tech:2181," +
                  "zookeeper-development-03.shared.us-west-2.aws.plume.tech:2181"
  def testZKCurator: Unit = {
    val client = CuratorFrameworkFactory.newClient(zkServers, new ExponentialBackoffRetry(1000, 3))
    client.start()

    /* get /development/us-west-2/dev/padev1/services/upgrade */
    val upgradePath = "/development/us-west-2/dev/padev1/services/upgrade"
    val wifiPath = "/development/us-west-2/dev/padev1/controller/config/wifi"
    //val upgradeValue = client.getData.forPath(upgradePath).toString
    //println(f"Upgrade = {}", upgradeValue)
    val wifiConfigReadTry = Try {
      val wifiConfData = new String(client.getData.forPath(wifiPath))
      println(f"Initial WifiConf data = ${wifiConfData}")
      wifiConfData
    }

    wifiConfigReadTry match {
      case Success(stat) => println(s"Get of wifiConfig success ${stat}")
      case Failure(e) => println(s"Get of wifiConfig failed ${e}")
    }

    setWifiConfig.setWifiConfig(client, wifiPath)


    // Actual Cache lister used in the final code
    def addWifiNodeCacheListener(client: CuratorFramework): Unit = {
      // Using Receipe library
      val nodeCache = new NodeCache(client, wifiPath)
      println("Before NodeCache getListenable for Wifi")
      nodeCache.getListenable.addListener(new NodeCacheListener {
        @Override
        def nodeChanged = {
          Try {
            val dataFromZNode = nodeCache.getCurrentData
            val wifiConfig = new String(dataFromZNode.getData) // This should be some new data after it is changed in the Zookeeper ensemble
            println(s"Wifi: ${wifiConfig}")

            implicit val formats = WifiConfig.formats

            val wifiConfigTry = Try(Serialization.read[WifiConfig](wifiConfig)) match {
              case Success(result: WifiConfig) => Some(result)

              case Failure(e) =>
                println(s"Failed to parse upgrade service config: ${e.getMessage}")
                None
            }
            println(s"Converted value: ${wifiConfigTry.lastOption}")
          }
        }
      })
      nodeCache.start
    }
    /* Test above API */
    addWifiNodeCacheListener(client)


    def testUpgradeNodeCacheLister: Unit = {
      println("Before NodeCache getListenable for Upgarde")
      // Using Receipe library
      val nodeCache = new NodeCache(client, upgradePath)
      nodeCache.getListenable.addListener(new NodeCacheListener {
        @Override
        def nodeChanged = {
          try {
            val dataFromZNode = nodeCache.getCurrentData
            val newData = new String(dataFromZNode.getData) // This should be some new data after it is changed in the Zookeeper ensemble
            println(s"{}", newData)
          } catch {
            case ex: Exception => println("Exception while fetching properties from zookeeper ZNode, reason " + ex.getCause)
          }
        }

      })
      nodeCache.start
      println("After NodeCache getListenable for Upgrade")
    }



    client.getConnectionStateListenable().addListener(
      (c, event) => {
        if (event.getClass != null) {
          //println("type=" + event.getType() + " path=" + event.getData().getPath())
        } else {
          //println(s"type = ${event.getType}")
        }
      }
    )

    /* Async */
    def getAsyncCurator(client: CuratorFramework): AsyncCuratorFramework = {
      AsyncCuratorFramework.wrap(client)
    }

    val asynClient = getAsyncCurator(client)


    // NOTE USED NOW : First version of implementation
    // 1. Used Asyc client
    // 2. TreeCache
    // Both should have been independent, not nested.
    def registerWatcher(asynClient: AsyncCuratorFramework): Unit = {
      val watchedEvent = asynClient.watched().checkExists().forPath(wifiPath).event()
      watchedEvent.thenAccept(event => {
        println(s"Async client Get type:${event.getType}, Path: ${event.getPath}, Event: ${event}")

        // Get the value
        val  cache: TreeCache = TreeCache.newBuilder(client, wifiPath).setCacheData(false).build()
        cache.getListenable.addListener((c, event) => {
          if (event.getData != null)
            println(s"${Thread.currentThread()}type=" + event.getType + " path=" + event.getData.getPath + "value" + new String(event.getData.getData))
          else
            println(s"${Thread.currentThread()}ttype=" + event.getType)
        })
        cache.start()


        // Reset the watcher
        println("Resetting the watcher")
        registerWatcher(asynClient)
      })
      watchedEvent.exceptionally(e => {
        e.printStackTrace()
        registerWatcher(asynClient) // Can cause infine loop ?
        return
      })
    }

    println("Going to sleep now")
    Thread.sleep(1000000)
  }
}

