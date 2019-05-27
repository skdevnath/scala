object MapTest {

  def main(args: Array[String]) : Unit = {
    println("In main")
    val names: List[List[String]] =
      List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Westheide"))
    println("Org:" + names)

    // Map's function, which takes String agrument and returns String (but upper),
    // however we can return some other datatype too.
    println("Toupper using map:" + names.map(_.map(_.toUpperCase))) // two level of map
    // results in List(List("JOHN", "JOHANNA", "DANIEL"), List(), List("DOE", "WESTHEIDE"))
    // Following map converts to bool, expected output
    // List(List(true, true, false), List(), List(false, false))
    println("Looking for \"Jo\" sub string map:" + names.map(_.map(_.contains("Jo")))) // two level of map
    // Note: map doesn't filter elements, usage of filter for inner List output:List(List(John, Johanna), List(), List())
    println("Looking for \"Jo\" sub string map with filter:" + names.map(_.filter(_.contains("Jo")))) // two level of map
    // Note: Let's remove empty List from above output:
    println("Looking for \"Jo\" sub string remove empty lists:" + names.map(_.filter(_.contains("Jo"))).filter(x =>
      x.size > 0)) // two level of map


    // flatMap takes a function, which returns list, it can return a different type too, here
    // It takes String, but returns list of boolean (strings starts with J)
    println("Just flatten List[String]: " + names.flatMap(x => x.map(e => e.startsWith("J"))))
    // results in List(true, true, false, false, false)

    // Usage of flatten, just make list of same type from nested Lists
    println("Use of flatten to flatten list of list: " + names.flatten)
    // results List(John, Johanna, Daniel, Doe, Westheide)

    // usage of flatten with map
    println("Use of flatten with map: " + names.flatten.map(_.toUpperCase))
    // result List(JOHN, JOHANNA, DANIEL, DOE, WESTHEIDE)

    println("flatmap and then map each element to different datatype or value of same type: " + names.flatMap(_.map(_.toUpperCase)))
    // results in List("JOHN", "JOHANNA", "DANIEL", "DOE", "WESTHEIDE")
  }
}
