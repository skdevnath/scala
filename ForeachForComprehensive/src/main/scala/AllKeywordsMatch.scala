import scala.annotation.tailrec

/**
  * @author sandip@plumewifi.com
  */

object AllKeywordsMatch {
  private def valuesToCheckForKeyword(values: Seq[String], keyword: String): Seq[String] = {
    for {
      value <- values
      if !value.toLowerCase.contains(keyword.toLowerCase)
    } yield {
      value
    }
  }

  // Note: mapping shouldn't have substring i.e. one key withing another key entirely
  private def scoreAllKeywordsMatch_sandip(values: Seq[String], keywords: Seq[String], minScore: Int): Int  = {
    if ((keywords.size > 0) && (values.size > 0) && (values.size >= keywords.size)) {
      // Check if all keys in the values
      // We need to make sure that
      // If one value is matched, we don't want to iterate that again
      var valuesToCheck = values

      val keysMatched = keywords.map { keyword =>
        val oldValueSize = valuesToCheck.size
        valuesToCheck = valuesToCheckForKeyword(valuesToCheck, keyword)
        val newValueSize = valuesToCheck.size
        (oldValueSize > newValueSize)
      }
      println(s"keysMatched: $keysMatched")
      val allDidntMatch = keysMatched.contains(false)
      println(s"allDidntMatch: $allDidntMatch")

      if (allDidntMatch) 0 else 1
    } else {
      0
    }
  }

  // Suggested by Metod
  @tailrec private def scoreAllKeywordsMatch(values: Seq[String], keywords: Seq[String], minScore: Int): Int = {
    keywords match {
      case keyword :: tail =>
        println(s"keywrd case: $keyword")
        val lowerCaseKeyword = keyword.toLowerCase
        val filteredValues = values.filterNot(_.toLowerCase.contains(lowerCaseKeyword))

        println(s" filteredValues.length:${filteredValues.length}, values.length): ${values.length}")
        if (filteredValues.length < values.length) {
          scoreAllKeywordsMatch(filteredValues, tail, 0)
        } else {
          println("return 0")
          0
        }

      case _ =>
        println(s"default case, values: $values")
        //if (values.isEmpty) 1 else {
         // println("return 0 from default")
          //0
        //}
        1
    }
  }

  def test: Unit = {
    // expect score 1
    //List(logsink.devices.nest.com, home.nest.com),
    // values: List(logsink.devices.nest.com, czfe26-front01-iad01.transport.home.nest.com, 2wpad.mpth.wildfire.exchange, wpad.wildfire.exchange, ipv4.connman.net, 3wpad.mpth.wildfire.exchange, 4wpad.mpth.wildfire.exchange)
    val keywords = Seq("logsink.devices.nest.com", "home.nest.com")
    val values = Seq("logsink.devices.nest.com",
      "czfe26-front01-iad01.transport.home.nest.com",
      "wpad.mpth.wildfire.exchange",
      "wpad.wildfire.exchange",
      "ipv4.connman.net")
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score} expect score 1")

    test1
    test2
    test3
    test4
    test5
  }

  def test1: Unit = {
    // expect score 0
    val keywords = Seq("logsink1.devices.nest.com", "home.nest.com")
    val values = Seq("logsink.devices.nest.com",
      "czfe26-front01-iad01.transport.home.nest.com",
      "wpad.mpth.wildfire.exchange",
      "wpad.wildfire.exchange",
      "ipv4.connman.net")
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score} expect score 0")
  }

  def test2: Unit = {
    // expect score 0
    val keywords = Seq()
    val values = Seq(
      "wpad.wildfire.exchange",
      "ipv4.connman.net")
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score}, expect score 0")
  }

  def test3: Unit = {
    // expect score 0
    val keywords = Seq("tesla.com")
    val values = Seq()
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score}, expect score 0")
  }

  def test4: Unit = {
    // expect score 1
    val keywords = Seq("tesla.com")
    val values = Seq("servicetesla.com")
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score} expect score 1")
  }

  def test5: Unit = {
    // expect score 0
    val keywords = Seq("tesla.com",
                       "model3.com")
    val values = Seq("servicetesla.com")
    val score = scoreAllKeywordsMatch(values, keywords, minScore = 0)
    println(s"\nKEYWORDS: $keywords, VALUES: ${values}, SCORE:${score}")
    println(s"Score: ${score} expect score 0")
  }
}
