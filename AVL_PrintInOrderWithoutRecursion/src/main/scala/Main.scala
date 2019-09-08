import scala.collection.mutable

/**
  * @author sandip
  */
object Main extends App {

  case class Node(value:Int, left: Option[Node], right: Option[Node])

  // Print Infix/In-order - Use stack. Used scla ListBuffer for this.
  // Sample data
  /*
             7
       4          9
     3    5    8    10
           6
  Output:
    In Main
     3
     4
     5
     6
     7
     8
     9
     10
   */

  val six = Node(value = 6, left = None, right = None)
  val three = Node(value = 3, left = None, right = None)
  val eight = Node(value = 8, left = None, right = None)
  val ten = Node(value = 10, left = None, right = None)
  val five = Node(value = 5, left = None, right = Some(six))
  val four = Node(value = 4, left = Some(three), right = Some(five))
  val nine = Node(value = 9, left = Some(eight), right = Some(ten))
  val seven = Node(value = 7, left = Some(four), right = Some(nine))
  val root = seven

  val stack = mutable.ListBuffer.empty[Node]

  def travelLeftMost(currentOption: Option[Node]) = {
    var privateCurrentOption = currentOption
    while (privateCurrentOption != None) {
      val privateCurrent = privateCurrentOption.get
      stack.prepend(privateCurrent)
      privateCurrentOption = privateCurrent.left
    }
  }

  def inOrderTraversal(tree: Option[Node]) = {
    travelLeftMost(tree)

    println("Print left most, scala way (doesn't work, stack collection dynamic update by travelLeftMost() doesn't get reflected):")
    for {
     node <- stack
    } {
      println(" " + node.value + " ")
      node.right.foreach(x => travelLeftMost(_))
    }

    /* Print left most, old progmmaing way */
    println("Print left most, old progmmaing way (Works):")
    while (stack.nonEmpty) {
      val leftMost = stack.remove(0)
      println(" " + leftMost.value + " ")
      if (leftMost.right != None) {
        travelLeftMost(leftMost.right)
      }
    }

  }

  startup()

  def startup() = {
    println("In Main")
    inOrderTraversal(Some(root))
  }
}
