package main.scala.settings

import javax.mail.internet.{MimeMessage => LibraryMimeMessage}

import main.scala.dslcode.MessageBuilder

/**
  * Created by Cyril on 05-04-16.
  */
trait MailSettings{

  //This variable contains the url of the SMTP host
  val SMTPhost = "localhost"

  //This variable contains the port that must be used to reach the SMTP host
  val SMTPport = 2525

  val IMAPhost = "localhost"
  val IMAPport = 993
  val MailStoreType = "pop3"

  //This variable contains a scala regex that checks the validity of email addresses
  val EmailValidityRegex = """([\w\.]+)@([\w\.]+)""" //http://stackoverflow.com/questions/13912597/validate-email-one-liner-in-scala

  //Put this variable at true will skip all addresses error and send email anyway.
  //Putting it to false will stop the execution once an error is detected.
  val ShouldSkipAddressesOnError = true

  //Put this variable at true will generate an exception in case of address error, stopping the execution.
  //This setting will be ignored if ShouldSkipAddressesOnError is put to true
  val ShouldGenerateExceptionOnError = false

  //Putting this variable to false will generate an error instead of sending the message
  var errorAllowedForSending = true

  val auth = false
  val username = ""
  val password = ""
  val tls = false

  //Default message
  def getMessage(): LibraryMimeMessage = {
    MessageBuilder(this)
  }
}
