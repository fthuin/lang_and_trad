package main.scala.code

import java.io.InputStream
import javax.mail.{Message => LibraryMessage}
import javax.mail.internet.{MimeMessage => LibraryMimeMessage}
import javax.mail.internet.InternetAddress
import javax.mail.Session
import main.scala.code.Constants.space


/**
  * Created by Cyril on 04-04-16.
  *
  * This class regroup all the mail operator defined in our DSL
  */

trait MessageInterface extends LibraryMimeMessage {

  var mailText : StringBuilder = new StringBuilder()

  def from(addr: String) = {
    this.setFrom(new InternetAddress(addr))
  }

  def to(addr: String) = {
    this.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(addr))
  }

  def set_subject(subject: String) = {
    this.setSubject(subject)
  }

  def set_text(txt: String) = {
    mailText = new StringBuilder(txt)
    this.setContent(txt, "text/html")
  }

  def add_text(txt: String) = {
    //TODO faire un check si le dernier character est un espace ou une fin de ligne, ne pas rajouter d'espace
    mailText.append(txt)
    this.setContent(mailText.toString(), "text/html")
  }

  def add(c: Char) = {
    this.add_text(c.toString)
  }
}

object MimeMessageBuilder {

  def apply(libraryMimeMessage: LibraryMimeMessage) = new LibraryMimeMessage(libraryMimeMessage) with MessageInterface
  def apply(session: Session) = new LibraryMimeMessage(session) with MessageInterface
  def apply(session: Session, inputStream: InputStream) = new LibraryMimeMessage(session, inputStream) with MessageInterface
}