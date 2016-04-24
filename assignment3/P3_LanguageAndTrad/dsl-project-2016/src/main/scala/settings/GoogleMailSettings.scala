package main.scala.settings

/**
  * Created by florian on 23/04/16.
  */
object GoogleMailSettings extends MailSettings {
  override val SMTPhost = "smtp.gmail.com"
  override val SMTPport = 587
  override val auth = true
  override val tls = true

  override val username = "grosbras2"
  override val password = "toset"

}
