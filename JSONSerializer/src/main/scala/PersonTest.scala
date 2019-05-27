import org.json4s._
import org.json4s.jackson.JsonMethods._

object PersonTest {
  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address, children: List[Child])
  def testPerson = {
    implicit val formats = org.json4s.DefaultFormats
    val json = parse("""
         { "name": "joe",
           "address": {
             "street": "Bulevard",
             "city": "Helsinki"
           },
           "children": [
             {
               "name": "Mary",
               "age": 5,
               "birthdate": "2004-09-04T18:06:22Z"
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """)
    val testP = json.extract[Person]
    println(s"testP: ${testP}")
  }

}
