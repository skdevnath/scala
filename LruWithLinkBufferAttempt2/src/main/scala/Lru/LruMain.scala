package Lru

/**
  * @author sandip@plumewifi.com
  */
object LruMain {
  def main(args: Array[String]): Unit = {
    println("Inside main")
    LruAlgo.findPage(1)
    LruAlgo.findPage(2)
    LruAlgo.findPage(3)
    LruAlgo.findPage(4)
    LruAlgo.findPage(5)
    LruAlgo.findPage(4)
  }

}
