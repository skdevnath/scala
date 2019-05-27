object ZKCuratorMain {
  def main(args: Array[String]): Unit = {
    println("Inside main")
    val testZKCurator = new TestZKCurator
    testZKCurator.testZKCurator
  }
}
