package main.scala.utilities

import javax.mail.{Authenticator, PasswordAuthentication}

/**
  * Created by Cyril on 05-04-16.
  */
object staticUtils {

  /*
   * Return an Authenticator object created using the provided username and password.
   */
  def getAuthenticator(username: String, password : String): javax.mail.Authenticator = {
    new Authenticator() {
      override def getPasswordAuthentication(): PasswordAuthentication = {
        new PasswordAuthentication(username, password)
      }
    }
  }
}
