package main.scala.example

import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import java.util.Properties
import main.scala.code.MimeMessageBuilder
import main.scala.code.Constants._

/**
  * Created by Cyril on 04-04-16.
  */
object basicMessageSyntaxicSugar {

  def main(args: Array[String]) {

    val toAddr: String = "abcd@gmail.com"
    val fromAddr: String = "web@gmail.com"
    val host: String = "localhost"

    val properties: Properties = System.getProperties

    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", "2525")

    val session: Session = Session.getDefaultInstance(properties)

    try {

      val message = MimeMessageBuilder(session)

      message from fromAddr
      message to toAddr
      message to "poney@furry.com"

      message set_subject "My subject"
      message set_text "<h1>Hello</h1>"
      message add_text "poney"
      message add new_line
      message add space
      message add_text "petit poney"

      Transport.send(message)
      System.out.println("Sent message successfully....")
    }
    catch {

      case mex: MessagingException => {
        mex.printStackTrace
      }
    }
  }

}
