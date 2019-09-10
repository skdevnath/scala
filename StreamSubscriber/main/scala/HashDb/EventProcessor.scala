package HashDb

import com.sun.net.httpserver.Authenticator.Failure
import com.sun.tools.javac.comp.Infer.GraphStrategy.NodeNotFoundException
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
  * @author sandip@plumewifi.com
  */
object EventProcessor {
  val messageAEventHashMap =  mutable.HashMap.empty[Int, MessageAEventEntry]
  val messageBHashMap =  mutable.HashMap.empty[Int, MessageBEntry]

  def processMessage(messageA: MessageA): Unit = {
    val messageAEvents = messageA.events.map { event =>
      MessageAEventEntry(
          group = messageA.group,
          eventId = event.eventId,
          attributeIndex = event.attributeIndex
      )
    }

    messageAEvents.foreach { messageAEvent =>
      val eventAEntryTry = Try(messageAEventHashMap(messageAEvent.attributeIndex))
      eventAEntryTry match {
        case Success(foundAEntry) =>
          /*
           * 1. Make new entry as hash entry.
           * 2. Look into MessageB HashMap
           * 3. If entry found in B then write new message on stdout (can be a stream or any other i/o).
           * 4. If entry is not found in B then copy ref of foundEntry.listBuffer to new entry messageAEvent.listBuffer.
           *    Null out foundEntry.listBuffer. Append new entry(messageAEvent) to messageAEvent.listBuffer.
           */
          messageAEventHashMap(messageAEvent.attributeIndex) = messageAEvent
          Try(messageBHashMap(messageAEvent.attributeIndex)) match {
            case Success(foundBEntry) =>
              println(s"{\t\"group\": ${foundAEntry.group},")
              println(s"\n\t\"eventId\": ${foundAEntry.eventId},")
              println(s"\n\t\"originPlace: ${foundBEntry.originPlace}")
              println("}")
            case Failure(f) =>
              messageAEvent.listBuffer = foundAEntry.listBuffer
              messageAEvent.listBuffer += messageAEvent
              foundAEntry.listBuffer = None
          }

        case Failure(f) => println(s"Failure: ${f}") //Sandip:
          /*
           * 1. Make new entry as hash entry.
           * 2. Look into MessageB HashMap.
           * 3. If entry found in B, then write new message on stdout.
           * 4. Take other elements from end of MessageBEntry.listBuffer (Latest) and write new msg in stdout.
           * 5. Once all elements of MessageBEntry.listBuffer over. Clear it MessageBEntry.listBuffer = None.
           * 6. If entry not found in B, then add this message only messageAEvent.listBuffer
           */
          messageAEventHashMap(messageAEvent.attributeIndex) = messageAEvent
          Try(messageBHashMap(messageAEvent.attributeIndex)) match {
            case Success(foundBEntry) =>
              foundBEntry.listBuffer.reverse.foreach { bEntry =>
                println(s"{\t\"group\": ${bEntry.group},")
                println(s"\n\t\"eventId\": ${bEntry.eventId},")
                println(s"\n\t\"originPlace: ${bEntry.originPlace}")
                println("}")
              }
              foundBEntry.listBuffer = None
            case Failure(f) =>
              messageAEvent.listBuffer += messageAEvent
          }
      }
    }
    messageAEventHashMap
  }

  def processMessage(messageB: MessageB): Unit = {
    messageBTry = Try(messageBHashMap(messageB.attributeIndex))
    messageBTry match {
      case Success(foundBEntry) =>
         /*
          * 1. Make new entry as hash entry.
          * 2. Look into MessageAEvent HashMap
          * 3. If entry found in A then write new message on stdout (can be a stream or any other i/o).
          * 4. If entry is not found in A then copy ref of foundBEntry.listBuffer to new entry messageB.listBuffer.
          *    Null out foundEntry.listBuffer. Append new entry(messageAEvent) to messageAEvent.listBuffer.
          */
        messageBHashMap(messageB.attributeIndex) = messageB
        Try(messageAEventHashMap(messageB.attributeIndex)) match {
          case Success(foundAEntry) =>
            println(s"{\t\"group\": ${foundAEntry.group},")
            println(s"\n\t\"eventId\": ${foundAEntry.eventId},")
            println(s"\n\t\"originPlace: ${foundBEntry.originPlace}")
            println("}")
          case Failure(f) =>
            messageB.listBuffer = foundBEntry.listBuffer
            messageB.listBuffer += messageB
            foundBEntry.listBuffer = None
        }
      case Failure(f) => println(s"Failure: ${f}") //Sandip:
        /*
         * 1. Make new entry as hash entry.
         * 2. Look into MessageA HashMap.
         * 3. If entry found in A, then write new message on stdout.
         * 4. Take other elements from end of MessageAEntry.listBuffer (Latest) and write new msg in stdout.
         * 5. Once all elements of MessageAEntry.listBuffer over. Clear it MessageBEntry.listBuffer = None.
         * 6. If entry not found in A, then add this message only messageB.listBuffer
         */
        messageBHashMap(messageB.attributeIndex) = messageB
        Try(messageAEventHashMap(messageB.attributeIndex)) match {
          case Success(foundAEntry) =>
            foundAEntry.listBuffer.reverse.foreach { aEntry =>
              println(s"{\t\"group\": ${aEntry.group},")
              println(s"\n\t\"eventId\": ${aEntry.eventId},")
              println(s"\n\t\"originPlace: ${messageB.originPlace}")
              println("}")
            }
            foundAEntry.listBuffer = Node
          case Failure(f)  =>
            messageB.listBuffer += messageB
        }


    }
  }
}
