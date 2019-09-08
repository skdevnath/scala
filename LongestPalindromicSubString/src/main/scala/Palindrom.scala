/**
  * @author sandip@plumewifi.com
  *
  *         input = ab
  *         output = a (note: a or single char is palindrom)
  *
  *         input abba
  *         output abba
  *
  *
  *         Input: "babad"
            Output: "bab"
            Note: "aba" is also a valid answer.
  https://leetcode.com/problems/longest-palindromic-substring/
  */
object Palindrom {
    def main(args: Array[String]): Unit = {
      if (args.length == 1) {
        println(s"Input: ${args(0)}")
        val palindromOut = longestPalindrome(args(0))
        println(s"Found longest palendrom: ${palindromOut}")
      } else {
        println(s"expect only one input, got ${args.length}")
      }
    }

    def longestPalindrome(word: String): String = {
      var longestPalindrome = ""
      val len = word.length
      if (len >= 1) {
        longestPalindrome = word.charAt(0).toString
      }
      // println(s"len: ${len}")
      for (s <- 0 until len) {
        for (e <- (s + 1) until len) {
          // println(s"substrings: ${word.substring(s, e + 1)}")
          if (isPalindrom(word, s, e)) {
            // println(" It is palindrom")
            if ((e - s + 1) > longestPalindrome.length) {
              longestPalindrome = word.substring(s, e + 1)
            }
          }
        }

      }
      longestPalindrome
    }

    def isPalindrom(word: String, start: Int, end: Int): Boolean = {
      var s = start
      var e = end
      if (e - s > 0) {
        while ((s < e) && word.charAt(s).toLower == word.charAt(e).toLower) {
          e -= 1
          s += 1
        }
        if (s < e) {
          false
        } else {
          // s == e (in odd size palindrome) and s > e (in even size of word case)
          true
        }

      } else {
        false
      }
    }
}
