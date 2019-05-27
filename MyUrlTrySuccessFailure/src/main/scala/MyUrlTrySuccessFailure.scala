package myUrl

import scala.util.{ Try, Success, Failure }

object MyUrlTrySuccessFailure {
  def main(args: Array[String]): Unit = {
    println("In main")
    val wikiUrl = new GetUrl("https://en.wikipedia.org/wiki/Main_Page")
    wikiUrl.parseUrl match {
      case Success(url) => println(s"Success: ${url}")
      case Failure(f) => println(s"Failure: ${f}")
    }
  }
}
