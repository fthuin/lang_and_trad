package main.scala.code

/**
  * Created by Cyril on 05-04-16.
  */
object DefaultMailSettings {

  val SMTPhost = "localhost"
  val SMTPport = 2525
  val EmailValidityRegex = """(\w+)@([\w\.]+)""" //http://stackoverflow.com/questions/13912597/validate-email-one-liner-in-scala
}
