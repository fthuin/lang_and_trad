package main.scala.dslcode

import java.io.InputStream
import java.util.Properties
import javax.mail.{Message => LibraryMessage}
import javax.mail.internet.{MimeMessage => LibraryMimeMessage}
import javax.mail.internet.InternetAddress
import javax.mail.Session
import javax.mail.{Address, Transport}

import main.scala.settings.{DefaultMailSettings, MailSettings}
import main.scala.utilities.settingsRelatedUtils
import main.scala.utilities.staticUtils

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Cyril on 04-04-16.
  *
  * This class regroup all the mail operator defined in our DSL
  */

trait MessageInterface{

  //Class Variables

  var settings: MailSettings = DefaultMailSettings
  var utilInstance = new settingsRelatedUtils(settings)
  var mailText : StringBuilder = new StringBuilder()
  var message = settings.getMessage()

  //FROM handling

  def from(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    message.setFrom(new InternetAddress(addrs(0)))
    if(addrs.length > 1){
      var tab: Array[Address] = addrs.map(elem => new InternetAddress(elem)).toArray
      tab = tab.drop(1) //Remove first element of the array (drop n element from beginning)
      message.addFrom(tab)
    }
  }

  def clear_from(): Unit ={
    message.setFrom(null : String) //Makes from field empty, fake smtp fills with some bullshit, but in mail it's clean
  }

  def add_from(addr: String*): Unit ={
    val tab: Array[Address] = utilInstance.ArrayStringSplitter(addr.toArray).map(elem => new InternetAddress(elem)).toArray
    message.addFrom(tab)
  }

  //TO handling

  def to(addr: String*) = {
    this.clear_to()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem)))
  }

  def clear_to(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.TO, null : String)
  }

  def add_to(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem)))
  }

  //CC handling

  def cc_to(addr: String*) = {
    this.clear_cc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem)))
  }

  def clear_cc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.CC, null : String)
  }

  def add_cc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem)))
  }

  //BCC handling

  def bcc_to(addr: String*) = {
    this.clear_bcc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem)))
  }

  def clear_bcc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.BCC, null : String)
  }

  def add_bcc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem)))
  }

  //Subject handling

  def set_subject(subject: String) = {
    message.setSubject(subject)
  }

  //Text handling

  def set_text(txt: String) = {
    mailText = new StringBuilder(txt)
    message.setContent(txt, "text/html")
  }

  def add_text(txt: String) = {
    //TODO faire un check si le dernier character est un espace ou une fin de ligne, ne pas rajouter d'espace
    mailText.append(txt)
    message.setContent(mailText.toString(), "text/html")
  }

  def add_line(s: String) = {
    this.add_text(s + "\n")
  }

  def add(c: Char) = {
    this.add_text(c.toString)
  }

  def add(s: String) = {
    this.add_text(s)
  }

  //Send message

  def send(): Unit ={
    try {
      if (settings.errorAllowedForSending) {
        Transport.send(message)
        System.out.println("Sent message successfully....")
      }
      else System.out.println("Some error remain, no message was sent....")
    }
    catch{
      case e:javax.mail.MessagingException => {
        if (e.getNextException().toString().contains("No MimeMessage content")) {
          println("Impossible to send, no message content")
        }
        else {
          e.printStackTrace()
        }
      }
    }
  }

  //Clear all message content

  def clearAll(): Unit ={
    mailText = new StringBuilder()
    message = settings.getMessage()
  }

  //Select settings

  def selectMailSettings(newSettings: MailSettings): Unit ={
    settings = newSettings
    utilInstance =  new settingsRelatedUtils(settings)
    message = settings.getMessage()
    mailText = new StringBuilder()
  }

  // Custom repeat loop
  def repeat(command: (String, String, String) => Unit) = new {
    def foreach(tuples: => ArrayBuffer[(String, String, String)]): Unit = {
      for ((part1, part2, part3) <- tuples) {
        command(part1, part2, part3)
      }
    }
  }

}

/*
 * FACTORY METHOD
 *
 * This object as no other purpose but to call the correct constructor of the java class to create our MimeMessage object.
 * The created object as the MessageInterface trait methods, thus implementing the DSL
 */
object MessageBuilder {

  //DSL basic constructor

  def apply(): LibraryMimeMessage = {
    apply(DefaultMailSettings)
  }

  def apply(settings: MailSettings) : LibraryMimeMessage = {
    if (settings.auth) {
      val props: Properties = new java.util.Properties()
      props.put("mail.smtp.auth", settings.auth.toString)
      props.put("mail.smtp.starttls.enable", settings.tls.toString)
      props.put("mail.smtp.host",  settings.SMTPhost)
      props.put("mail.smtp.port", settings.SMTPport.toString)
      apply(props, settings.username, settings.password)
    } else {
      apply(settings.SMTPhost, settings.SMTPport)
    }
  }

  def apply(host : String, port : Int): LibraryMimeMessage = {

    val properties: Properties = System.getProperties
    properties.put("mail.smtp.host", host)
    properties.put("mail.smtp.port", port.toString)

    apply(properties)
  }

  def apply(properties: Properties): LibraryMimeMessage = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(session)
  }

  //DSL constructor with basic security feature

  def apply(username: String, password : String): LibraryMimeMessage = {
    apply(username, password, DefaultMailSettings)
  }

  def apply(username: String, password: String, settings: MailSettings): LibraryMimeMessage = {
    apply(settings.SMTPhost, settings.SMTPport, username, password)
  }

  def apply(host : String, port : Int, username: String, password : String): LibraryMimeMessage = {

    val properties: Properties = System.getProperties
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.host", host)
    properties.put("mail.smtp.port", port.toString)
    properties.put("mail.smtp.starttls.enable", "true"); //To enable transport layer security

    apply(properties, username, password)
  }

  def apply(properties: Properties, username: String, password : String): LibraryMimeMessage = {
    val session: Session = Session.getInstance(properties, staticUtils.getAuthenticator(username, password))
    apply(session)
  }

  //Basic javamail constructors

  def apply(libraryMimeMessage: LibraryMimeMessage): LibraryMimeMessage = new LibraryMimeMessage(libraryMimeMessage)
  def apply(session: Session): LibraryMimeMessage = new LibraryMimeMessage(session)
  def apply(session: Session, inputStream: InputStream): LibraryMimeMessage = new LibraryMimeMessage(session, inputStream)

  /*
  def apply(settings: MailSettings) : LibraryMimeMessage = {
    apply(settings, settings.SMTPhost, settings.SMTPport)
  }

  def apply(settings: MailSettings, host: String, port: Int) : LibraryMimeMessage = {
    val properties: Properties = System.getProperties
    properties.put("mail.smtp.host", host)
    properties.put("mail.smtp.port", port.toString)
    apply(settings, properties)
  }

  def apply(settings: MailSettings, properties: Properties) : LibraryMimeMessage = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(settings, session)
  }

  def apply(settings: MailSettings, session: Session) : LibraryMimeMessage = {
    new LibraryMimeMessage(session)
  }
  */
}