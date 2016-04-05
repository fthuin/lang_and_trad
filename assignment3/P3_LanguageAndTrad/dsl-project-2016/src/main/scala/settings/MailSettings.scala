package main.scala.settings

/**
  * Created by Cyril on 05-04-16.
  */
trait MailSettings{

  //This variable contains the url of the SMTP host
  val SMTPhost = "localhost"

  //This variable contains the port that must be used to reach the SMTP host
  val SMTPport = 2525

  //This variable contains a scala regex that checks the validity of email addresses
  val EmailValidityRegex = """(\w+)@([\w\.]+)""" //http://stackoverflow.com/questions/13912597/validate-email-one-liner-in-scala

  //Put this variable at true will skip all addresses error and send email anyway.
  //Putting it to false will stop the execution once an error is detected.
  val ShouldSkipAddressesOnError = true
}
