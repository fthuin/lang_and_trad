package main.scala.example

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Properties

/**
  * Created by Cyril on 04-04-16.
  */
object SendNoDSLMailScalaExample {

  def main(args: Array[String]) {

    val to: String = "abcd@gmail.com"
    val from: String = "web@gmail.com"
    val host: String = "localhost"

    val properties: Properties = System.getProperties

    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", "2525")

    val session: Session = Session.getDefaultInstance(properties)

    try {

      val message: MimeMessage = new MimeMessage(session)

      message.setFrom(new InternetAddress(from))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to))

      message.setSubject("This is the Subject Line!")
      message.setContent("<h1>This is actual message</h1>", "text/html")

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
