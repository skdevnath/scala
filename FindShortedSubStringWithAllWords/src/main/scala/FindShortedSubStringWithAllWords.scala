package  com.sandip.findshortedsubstring
import scala.collection.mutable

/**
  * @author sandip@plumewifi.com
  */
object FindShortedSubStringWithAllWords {
  var shortestSentence = ""

  def main(args: Array[String]) {
    /* remove puctuations from the sentence */
    val testStr_1 = "The world is here. this is a life full of ups and downs. life is world."
    val testWords_1 = Array("life", "ups", "is", "world")
    val finalSentence = testStr_1.replace(".", "").replace(",", "").replace("!", "")
    println(s"Final string: ${finalSentence}")
    print("Final word to search: ")
    testWords_1.foreach(x => print(s"${x}, "))
    println("")
    val test_1_shortestSentence = getSortedSubString(testStr_1, getWordMap(testWords_1))
    println("Shortest string is: " + test_1_shortestSentence)
  }
  def getWordMap(words: Array[String]): mutable.Map[String, Boolean] = {
    val wordMap = mutable.Map.empty[String, Boolean]
    words.foreach { word =>
      wordMap += (word.toLowerCase -> false)
    }
    wordMap
  }

  def allWordsFoundInMap(wordMap: mutable.Map[String, Boolean]): Boolean = {
    !wordMap.exists(x => x._2 == false)
  }

  def getSortedSubString(sentence: String, wordMap: mutable.Map[String, Boolean]): String = {
    var startPtr = 0
    while (startPtr < sentence.length) {
      var nextWordFinder = startPtr
      var wordEndFinder = startPtr
      var allWordsFound = false

      while (!allWordsFound && (nextWordFinder < sentence.length)) {
        while (wordEndFinder < sentence.length && sentence.charAt(wordEndFinder) != ' ') {
          wordEndFinder += 1
        }

        val startOfNewWord = nextWordFinder
        val endOfNewWord = if (wordEndFinder >= sentence.length) (sentence.length - 1) else wordEndFinder
        val wordToCheck = sentence.substring(startOfNewWord, endOfNewWord)

        wordMap.get(wordToCheck.toLowerCase).foreach(x => wordMap(wordToCheck.toLowerCase()) = true)
        allWordsFound = allWordsFoundInMap(wordMap)

        /* increment to next char */
        wordEndFinder += 1
        nextWordFinder = wordEndFinder
      }

      if (allWordsFound) {
        if (wordEndFinder >= sentence.length) wordEndFinder = sentence.length
        val newSubString = sentence.substring(startPtr, wordEndFinder)
        if ((shortestSentence.length == 0) || shortestSentence.length > newSubString.length ) {
          shortestSentence = newSubString
        }
      }

      /* now start over from second word onward */
      wordEndFinder = startPtr
      while (wordEndFinder < sentence.length && sentence.charAt(wordEndFinder) != ' ') {
        wordEndFinder += 1
      }
      /* clear all bits */
      wordMap.foreach(x => wordMap(x._1) = false)
      startPtr = wordEndFinder + 1
    }
    shortestSentence
  }
}
