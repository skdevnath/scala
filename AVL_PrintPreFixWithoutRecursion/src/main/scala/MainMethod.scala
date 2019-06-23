package  com.sandip.printPrefixWithoutRecursion
import scala.collection.mutable

/**
  * @author sandip@plumewifi.com
  */

object MainMethod {
  case class Node (left: Node, right: Node, value: Int)

  val nine = Node(left = null, right = null, value = 9)
  val seven = Node(left = null, right = null, value = 7)
  val four = Node(left = null, right = null, value = 4)
  val two = Node(left = null, right = null, value = 2)

  val three = Node(left = two, right = four, value = 3)
  val eight = Node(left = seven, right = nine, value = 8)

  val six = Node(left = three, right = eight, value = 6)

  val root = six


  def main(args: Array[String]) {
    var ok = true
    while (ok) {
      val ln = readLine()
      ok = ln != null
      if (ok) println(ln)
      println(s"root: ${root.value}")
      var testStack = mutable.Stack[Node](root)
      while (testStack.nonEmpty) {
        val toProcess = testStack.pop()
        println(s"${toProcess.value}, ")
        if (toProcess.right != null) testStack.push(toProcess.right)
        if (toProcess.left != null) testStack.push(toProcess.left)
      }
    }
  }
}

