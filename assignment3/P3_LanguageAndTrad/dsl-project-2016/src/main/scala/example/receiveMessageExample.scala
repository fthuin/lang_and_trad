package main.scala.example

import main.scala.dslcode.MessageInterface
import main.scala.settings._

/**
  * Created by florian on 28/04/16.
  */
object receiveMessageExample extends MessageInterface {

  def main(args: Array[String]) {
    val close = IMAPsettings(GoogleMailSettings)

    //read(messages)

    read(unread)
    setRead(unread)

    close()
  }
}
