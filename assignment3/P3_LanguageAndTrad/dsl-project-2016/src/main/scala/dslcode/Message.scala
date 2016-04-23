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
  var message = settings.getDefaultMessage()

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
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem))
    }
  }

  def clear_to(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.TO, null : String)
  }

  def add_to(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem))
    }
  }

  //CC handling

  def cc_to(addr: String*) = {
    this.clear_cc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem))
    }
  }

  def clear_cc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.CC, null : String)
  }

  def add_cc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem))
    }
  }

  //BCC handling

  def bcc_to(addr: String*) = {
    this.clear_bcc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem))
    }
  }

  def clear_bcc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.BCC, null : String)
  }

  def add_bcc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem))
    }
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
      if (settings.noMistakesBeforeSending) {
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

  //Clear everything
  def clearAll(): Unit ={
    message = settings.getDefaultMessage()
  }
}

/*
 * This object as no other purpose but to call the correct constructor of the java class to create our MimeMessage object.
 * The created object as the MessageInterface trait methods, thus implementing the DSL
 */
object MessageBuilder {

  //DSL basic constructor

  def apply(): LibraryMimeMessage = {

    apply(DefaultMailSettings.SMTPhost, DefaultMailSettings.SMTPport)
  }

  def apply(host : String, port : Int): LibraryMimeMessage = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)

    apply(properties)
  }

  def apply(properties: Properties): LibraryMimeMessage = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(session)
  }

  //DSL constructor with basic security feature

  def apply(username: String, password : String): LibraryMimeMessage = {

    apply(DefaultMailSettings.SMTPhost, DefaultMailSettings.SMTPport, username, password)
  }

  def apply(host : String, port : Int, username: String, password : String): LibraryMimeMessage = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)
    properties.put("mail.pop3s.starttls.enable", "true"); //To enable transport layer security

    apply(properties, username, password)
  }

  def apply(properties: Properties, username: String, password : String): LibraryMimeMessage = {

    val session: Session = Session.getInstance(properties, staticUtils.getAuthenticator(username, password))
    apply(session)
  }

  //DSL with settings

  def apply(setting: MailSettings): LibraryMimeMessage = {

    apply(setting, setting.SMTPhost, setting.SMTPport)
  }

  def apply(setting: MailSettings, host : String, port : Int): LibraryMimeMessage  = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)

    apply(setting, properties)
  }

  def apply(setting: MailSettings, properties: Properties): LibraryMimeMessage = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(setting, session)
  }

  //Basic javamail constructors

  def apply(libraryMimeMessage: LibraryMimeMessage): LibraryMimeMessage = new LibraryMimeMessage(libraryMimeMessage)
  def apply(session: Session): LibraryMimeMessage = new LibraryMimeMessage(session)
  def apply(session: Session, inputStream: InputStream): LibraryMimeMessage = new LibraryMimeMessage(session, inputStream)

  //Basic javamail constructor with custom setting

  def apply(setting: MailSettings, libraryMimeMessage: LibraryMimeMessage): LibraryMimeMessage = {
    new LibraryMimeMessage(libraryMimeMessage) with MessageInterface
  }

  def apply(setting: MailSettings, session: Session): LibraryMimeMessage = {
    new LibraryMimeMessage(session) with MessageInterface
  }

  def apply(setting: MailSettings, session: Session, inputStream: InputStream): LibraryMimeMessage = {
    new LibraryMimeMessage(session, inputStream)
  }
}