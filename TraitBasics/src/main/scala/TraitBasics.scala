/**
  * @author sandip@plumewifi.com
  */
object TraitBasics {
  def main(args: Array[String]): Unit = {
    println("test TraitBasics")

    // To declar anonymous class from trail
    // following doesn't work
    //val simpleTrait: SimpleTrait =  SimpleTrait

    // following too doesn't work
    //val simpleTrait: SimpleTrait =  new SimpleTrait

    // following too doesn't work
    // val simpleTrait: SimpleTrait =  SimpleTrait{}

    // following too works. You have to give "new" keyword and {} at the
    // end to tell that anonymous class is instantiated.
    // I understand {}, but not why "new" ?
    val simpleTrait: SimpleTrait =  new SimpleTrait{}
  }

}
