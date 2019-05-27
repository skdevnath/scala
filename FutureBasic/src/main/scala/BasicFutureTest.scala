import scala.concurrent.Future
import scala.util.{ Failure, Success }
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author sandip@plumewifi.com
  */
class BasicFutureTest {

}

object BasicFutureTest {
  def sleep(millis: Long) = {
    Thread.sleep(millis)
  }

  // Busy work ;)
  def doWork(index: Int) = {
    sleep((math.random * 1000).toLong)
    index
  }

  def test() = {
    1 to 5 foreach { index =>
      val future = Future {
        doWork(index)
      }
      future onComplete {
        case Success(idx) => println(s"Got idx:${idx}")
        case Failure(e) =>println(s"Got exception:${e}")
      }
    }

    Thread.sleep(2000)
  }

}
