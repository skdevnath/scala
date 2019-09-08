package Lru

import scala.collection.mutable

/**
  * @author sandip@plumewifi.com
  */
object LruAlgo {
  val lruMap = mutable.HashMap.empty[Int, Int]    // Page-ID and Address map
  var fifo = mutable.ListBuffer.empty[Int]
  val FIFO_SIZE = 3

  def findPage(pageIdIn: Int): Int = {
    val fifoInitialSize = fifo.size
    fifo -= pageIdIn  // Sandip: What if the element doesn't exists ? no problem tried on command line scala , no exception
    fifo.prepend(pageIdIn)
    val fifoNewSize = fifo.size
    if (fifoNewSize > fifoInitialSize) {
      lruMap +=  (pageIdIn -> 33333) // 3333 should keep changing based on address to map, right now faking
    }

    // Trim FIFO
    if (fifoNewSize >= FIFO_SIZE )  {
      fifo = fifo.dropRight(fifoNewSize - FIFO_SIZE) // Keep first FIFO_SIZE elements, drop all last elements, they re not LRU
    }
    println("FIFO: " + fifo + " Map: " + lruMap)
    lruMap(pageIdIn)
  }
}
