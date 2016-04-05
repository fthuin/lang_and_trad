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

trait MessageInterface extends LibraryMimeMessage {

  //Class Variables

  var settings: MailSettings = DefaultMailSettings
  var utilInstance = new settingsRelatedUtils(settings)
  var mailText : StringBuilder = new StringBuilder()

  //FROM handling

  def from(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    this.setFrom(new InternetAddress(addrs(0)))
    if(addrs.length > 1){
      var tab: Array[Address] = addrs.map(elem => new InternetAddress(elem)).toArray
      tab = tab.drop(1) //Remove first element of the array (drop n element from beginning)
      this.addFrom(tab)
    }
  }

  def clear_from(): Unit ={
    this.setFrom(null : String) //Makes from field empty, fake smtp fills with some bullshit, but in mail it's clean
  }

  def add_from(addr: String*): Unit ={
    val tab: Array[Address] = utilInstance.ArrayStringSplitter(addr.toArray).map(elem => new InternetAddress(elem)).toArray
    this.addFrom(tab)
  }

  //TO handling

  def to(addr: String*) = {
    this.clear_to()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem))
    }
  }

  def clear_to(): Unit ={
    this.setRecipients(LibraryMessage.RecipientType.TO, null : String)
  }

  def add_to(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem))
    }
  }

  //CC handling

  def cc_to(addr: String*) = {
    this.clear_cc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem))
    }
  }

  def clear_cc(): Unit ={
    this.setRecipients(LibraryMessage.RecipientType.CC, null : String)
  }

  def add_cc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem))
    }
  }

  //BCC handling

  def bcc_to(addr: String*) = {
    this.clear_bcc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem))
    }
  }

  def clear_bcc(): Unit ={
    this.setRecipients(LibraryMessage.RecipientType.BCC, null : String)
  }

  def add_bcc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    for(elem <- addrs) {
      this.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem))
    }
  }

  //Subject handling

  def set_subject(subject: String) = {
    this.setSubject(subject)
  }

  //Text handling

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

  //Send message

  def send(): Unit ={
    Transport.send(this)
    System.out.println("Sent message successfully....")
  }
}

/*
 * This object as no other purpose but to call the correct constructor of the java class to create our MimeMessage object.
 * The created object as the MessageInterface trait methods, thus implementing the DSL
 */
object MessageBuilder {

  //DSL basic constructor

  def apply(): LibraryMimeMessage with MessageInterface = {

    apply(DefaultMailSettings.SMTPhost, DefaultMailSettings.SMTPport)
  }

  def apply(host : String, port : Int): LibraryMimeMessage with MessageInterface = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)

    apply(properties)
  }

  def apply(properties: Properties): LibraryMimeMessage with MessageInterface = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(session)
  }

  //DSL constructor with basic security feature

  def apply(username: String, password : String): LibraryMimeMessage with MessageInterface = {

    apply(DefaultMailSettings.SMTPhost, DefaultMailSettings.SMTPport, username, password)
  }

  def apply(host : String, port : Int, username: String, password : String): LibraryMimeMessage with MessageInterface = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)
    properties.put("mail.pop3s.starttls.enable", "true"); //To enable transport layer security

    apply(properties, username, password)
  }

  def apply(properties: Properties, username: String, password : String): LibraryMimeMessage with MessageInterface = {

    val session: Session = Session.getInstance(properties, staticUtils.getAuthenticator(username, password))
    apply(session)
  }

  //DSL with settings

  def apply(setting: MailSettings): LibraryMimeMessage with MessageInterface = {

    apply(setting, setting.SMTPhost, setting.SMTPport)
  }

  def apply(setting: MailSettings, host : String, port : Int): LibraryMimeMessage with MessageInterface = {

    val properties: Properties = System.getProperties
    properties.setProperty("mail.smtp.host", host)
    properties.setProperty("mail.smtp.port", port.toString)

    apply(setting, properties)
  }

  def apply(setting: MailSettings, properties: Properties): LibraryMimeMessage with MessageInterface = {
    val session: Session = Session.getDefaultInstance(properties)
    apply(setting, session)
  }

  //Basic javamail constructors

  def apply(libraryMimeMessage: LibraryMimeMessage): LibraryMimeMessage with MessageInterface = new LibraryMimeMessage(libraryMimeMessage) with MessageInterface
  def apply(session: Session): LibraryMimeMessage with MessageInterface = new LibraryMimeMessage(session) with MessageInterface
  def apply(session: Session, inputStream: InputStream): LibraryMimeMessage with MessageInterface = new LibraryMimeMessage(session, inputStream) with MessageInterface

  //Basic javamail constructor with custom setting

  def apply(setting: MailSettings, libraryMimeMessage: LibraryMimeMessage): LibraryMimeMessage with MessageInterface = {
    val obj = new LibraryMimeMessage(libraryMimeMessage) with MessageInterface
    obj.settings = setting
    obj.utilInstance = new settingsRelatedUtils(setting)
    obj
  }

  def apply(setting: MailSettings, session: Session): LibraryMimeMessage with MessageInterface = {
    val obj = new LibraryMimeMessage(session) with MessageInterface
    obj.settings = setting
    obj.utilInstance = new settingsRelatedUtils(setting)
    obj
  }

  def apply(setting: MailSettings, session: Session, inputStream: InputStream): LibraryMimeMessage with MessageInterface = {
    val obj = new LibraryMimeMessage(session, inputStream) with MessageInterface
    obj.settings = setting
    obj.utilInstance = new settingsRelatedUtils(setting)
    obj
  }
}