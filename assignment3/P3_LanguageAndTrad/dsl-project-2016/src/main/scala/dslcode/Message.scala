package main.scala.dslcode

import java.io.InputStream
import java.util.Properties
import javax.mail.internet.{InternetAddress, MimeMessage => LibraryMimeMessage}
import javax.mail.search.FlagTerm
import javax.mail.{Message => LibraryMessage, _}
import main.scala.settings.{DefaultMailSettings, MailSettings}
import main.scala.utilities.{settingsRelatedUtils, staticUtils}
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Cyril on 04-04-16.
  *
  * This interface regroups all variables and methods needed to
  * have a good DSL build upon the JavaMail library. Each new email
  * object should extends this trait.
  */

trait MessageInterface{

  /* Class variables */
  var settings: MailSettings = DefaultMailSettings
  var utilInstance = new settingsRelatedUtils(settings)
  var mailText : StringBuilder = new StringBuilder()
  var message = settings.getMessage()
  var messages: Array[LibraryMessage] = new Array(0)
  var unread: Array[LibraryMessage] = new Array(0)

  /** Sets one or more address as sender. Might not work if the settings don't allow authentication.
    * This method removes previously added sender addresses if any.
    * @param addr the addresses to set
    */
  def from(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    message.setFrom(new InternetAddress(addrs(0)))
    if (addrs.length > 1) {
      var tab: Array[Address] = addrs.map(elem => new InternetAddress(elem)).toArray
      tab = tab.drop(1) //Remove first element of the array (drop n element from beginning)
      message.addFrom(tab)
    }
  }

  /** Removes all sender addresses previously set.
    */
  def clear_from(): Unit ={
    message.setFrom(null : String) //Makes from field empty, fake smtp fills with some bullshit, but in mail it's clean
  }

  /** Adds one or more address as sender addresses
    *
    * @param addr the addresses to add
    */
  def add_from(addr: String*): Unit ={
    val tab: Array[Address] = utilInstance.ArrayStringSplitter(addr.toArray).map(elem => new InternetAddress(elem)).toArray
    message.addFrom(tab)
  }

  /** Sets one or more recipients of the message
    *
    * @param addr the addresses to set
    */
  def to(addr: String*) = {
    this.clear_to()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem)))
  }

  /** Removes all recipient addresses previously set.
    */
  def clear_to(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.TO, null : String)
  }

  /** Adds one or more recipient addresses for the message
    *
    * @param addr the addresses to add as recipients
    */
  def add_to(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.TO, new InternetAddress(elem)))
  }

  /** Sets one or more addresses as carbon copy recipients for the message
    *
    * @param addr the addresses to set as carbon copy recipients
    */
  def cc_to(addr: String*) = {
    this.clear_cc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem)))
  }

  /** Removes all carbon copy recipients addresses set if any.
    */
  def clear_cc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.CC, null : String)
  }

  /** Adds one or more addresses as carbon copy recipients for the message
    *
    * @param addr the addresses to add as carbon copy recipients
    */
  def add_cc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.CC, new InternetAddress(elem)))
  }

  /** Sets one or more addresses as blind carbon copy recipients for the message
    *
    * @param addr the addresses to set as blind carbon copy recipients
    */
  def bcc_to(addr: String*) = {
    this.clear_bcc()
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem)))
  }

  /** Removes all addresses set as blind carbon copy recipients
    */
  def clear_bcc(): Unit ={
    message.setRecipients(LibraryMessage.RecipientType.BCC, null : String)
  }

  /** Adds one or more addresses as blind carbon copy recipients for the message
    *
    * @param addr the addresses to add as blind carbon copy recipients
    */
  def add_bcc(addr: String*) = {
    val addrs = utilInstance.ArrayStringSplitter(addr.toArray)
    addrs.foreach(elem => message.addRecipient(LibraryMessage.RecipientType.BCC, new InternetAddress(elem)))
  }

  /** Sets the subject of the message
    *
    * @param subject the subject to set
    */
  def set_subject(subject: String) = {
    message.setSubject(subject)
  }

  /** Sets text as html content for the message
    *
    * @param txt the text to set as content
    */
  def set_text(txt: String) = {
    mailText = new StringBuilder(txt)
    message.setContent(txt, "text/html")
  }

  /** The text to concatenate to the current content of the message
    *
    * @param txt the text to add
    */
  def add_text(txt: String) = {
    var content = txt
    if (! (content.endsWith(" ") || content.endsWith("\n"))) content = content.concat(" ")
    mailText.append(content)
    message.setContent(mailText.toString(), "text/html")
  }

  /** Adds the content of a string and a line break
    *
    * @param s the string to add
    */
  def add_line(s: String) = {
    this.add_text(s + "\n")
  }

  /** Adds a character to the content
    *
    * @param c the character to add
    */
  def add(c: Char) = {
    this.add_text(c.toString)
  }

  /** Adds a String to the current content of the message
    *
    * @param s the string to add
    */
  def add(s: String) = {
    this.add_text(s)
  }

  /** Sends the current message
    */
  def send(): Unit ={
    try {
      if (settings.errorAllowedForSending) {
        Transport.send(message)
        val recipients: String = message.getAllRecipients.flatMap(x => x.toString).mkString(", ")
        println("Message sent successfully on " + message.getSentDate + " from " + message.getSender.toString + " to " + recipients)

      }
      else println("Some error remain, no message was sent....")
    }
    catch {
      case e:javax.mail.MessagingException => {
        if (e.getNextException().toString().contains("No MimeMessage content")) println("Impossible to send, no message content")
        else e.printStackTrace()
      }
      case e:java.lang.NullPointerException => println("Message sent succesfully.")
    }
  }

  /** Removes all information linked to the current message
    */
  def clearAll(): Unit ={
    mailText = new StringBuilder()
    message = settings.getMessage()
  }

  /** Sets settings for sending mails
    *
    * @param newSettings the settings to set
    */
  def selectMailSettings(newSettings: MailSettings): Unit ={
    settings = newSettings
    utilInstance =  new settingsRelatedUtils(settings)
    message = settings.getMessage()
    mailText = new StringBuilder()
  }

  /** Custom repeat-foreach loop that takes a one-parameter-instructions and a tuple with the argument
    *
    * @param command the instructions in which parameters must apply
    */
  def repeat(command: (String) => Unit) = new {
    def foreach(tuples: => ArrayBuffer[(String)]): Unit = {
      for ((part1) <- tuples) {
        command(part1)
      }
    }
  }

  /** Custom repeat-foreach loop that takes a two-parameters-instructions and a tuple with the arguments
    *
    * @param command the instructions in which parameters must apply
    */
  def repeat(command: (String, String) => Unit) = new {
    def foreach(tuples: => ArrayBuffer[(String, String)]): Unit = {
      for ((part1, part2) <- tuples) {
        command(part1, part2)
      }
    }
  }

  /** Custom repeat-foreach loop that takes a three-parameters-instructions and a tuple with the arguments
    *
    * @param command the instructions in which parameters must apply
    */
  def repeat(command: (String, String, String) => Unit) = new {
    def foreach(tuples: => ArrayBuffer[(String, String, String)]): Unit = {
      for ((part1, part2, part3) <- tuples) {
        command(part1, part2, part3)
      }
    }
  }

  /** Custom repeat-foreach loop that takes a four-parameters-instructions and a tuple with the arguments
    *
    * @param command the instructions in which parameters must apply
    */
  def repeat(command: (String, String, String, String) => Unit) = new {
    def foreach(tuples: => ArrayBuffer[(String, String, String, String)]): Unit = {
      for ((part1, part2, part3, part4) <- tuples) {
        command(part1, part2, part3, part4)
      }
    }
  }

  /** Sets the settings used for IMAP (to be able to read messages)
    *
    * @param settings the settings to add
    * @return a method to close access to the resources
    */
  def IMAPsettings(settings: MailSettings): () => Unit = {
    try {
      val props: Properties = new Properties()
      props.put("mail.imap.host", settings.IMAPhost)
      props.put("mail.imap.port", settings.IMAPport.toString)
      props.put("mail.store.protocol", "imaps")
      props.put("mail.imap.starttls.enable", settings.tls.toString)
      val session: Session = Session.getDefaultInstance(props)
      // Create the pop3 store object and connect with the pop server
      val store: Store = session.getStore("imaps")
      store.connect(settings.IMAPhost, settings.username, settings.password)
      // Create the folder object and open it
      val folder: Folder = store.getFolder("INBOX")
      folder.open(Folder.READ_WRITE)
      messages = folder.getMessages()
      unread = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false))
      def close(): Unit = {
        folder.close(false)
        store.close()
      }
      return close
    }
    catch {
      case e:NoSuchProviderException => println("POPsettings: No such provider")
      case e:MessagingException => println("POPsettings: Error in message processing")
      case e:Exception => println("POPsettings: An unhandled error happened...");
    }
    return () => {}
  }

  /** Display the messages in the console one by one, asking the user
    * if he wants to continue after each message.
    *
    * @param messages the messages to be shown
    */
  def read(messages: Array[LibraryMessage]): Unit = {
    for (m <- messages) {
      println("--------------------------------------------")
      println("Subject: " + m.getSubject)
      println("From: " + m.getFrom.mkString(","))
      if (m.getDataHandler.getContentType.toLowerCase.contains("text/plain")) println(m.getContent.toString)
      else println("The content of this message is too complex to be shown.")
      println("Press q to quit or any key to continue, then press Enter.")
      val ret: String = scala.io.StdIn.readLine() //waits for any key to be pressed
      if (ret.equals("q")) return
    }
    println("There are no more messages to show.");
  }

  /** Sets the given messages as 'read' or 'seen'.
    *
    * @param messages the messages to be set as read
    */
  def setRead(messages: Array[LibraryMessage]): Unit = {
    for (m <- messages) {
      m.setFlags(new Flags(Flags.Flag.SEEN), true)
    }
  }
}

/*
// An other way to define the repeat-foreach loop
class Repeat(block: => Unit) {
  def foreach(block: Unit): Unit = ???
}

object repeat {
  def apply(block: => Unit): Repeat = {
    new Repeat(block)
  }
}
*/

/*
 * FACTORY METHOD
 *
 * This object as no other purpose but to call the correct constructor of the java class to create our MimeMessage object.
 * The created object as the MessageInterface trait methods, thus implementing the DSL
 */
object MessageBuilder {

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
      props.put("mail.imap.host", settings.IMAPhost)
      props.put("mail.imap.port", settings.IMAPport.toString)
      props.put("mail.store.protocol", "imaps")
      props.put("mail.imap.starttls.enable", settings.tls.toString)
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
    properties.put("mail.imap.host", host)
    properties.put("mail.imap.port", port.toString)
    properties.put("mail.store.protocol", "imaps")
    properties.put("mail.imap.starttls.enable", "true")
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

}