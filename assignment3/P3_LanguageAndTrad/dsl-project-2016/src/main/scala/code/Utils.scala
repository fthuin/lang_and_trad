package main.scala.code

import javax.mail.{Authenticator, PasswordAuthentication}
import DefaultMailSettings._
import Array._

/**
  * Created by Cyril on 05-04-16.
  */
object Utils {

  def printErrorLine(error: String, addr: String){
    //TODO Skip or not according to settings
    val msg: String = error + ": '" + addr + "' is not a valid address ! This address was skipped"
    println(msg)
  }

  def checkAddressValidity(s: String): Boolean= {
    try{
      if(!s.matches(EmailValidityRegex)) throw new Exception("Error")
    }
    catch {
      case e:Exception => {
        val tab = e.getStackTrace.reverse
        for(i <- 0 until tab.length){
          if(tab(i).toString.contains("Message.scala:")){
            printErrorLine(tab(i-1).toString, s)
            return false
          }
        }
        return false
      }
    }
    return true
  }

  def splitString(s : String): Array[String] = {
    return s.split(",").map(s => s.trim()).filter(s => !s.equals("") && checkAddressValidity(s))
  }

  def ArrayStringSplitter(tab : Array[String]): Array[String] = {

    var result = Array[String]()
    for(elem <- tab){
      result = concat(result, splitString(elem))
    }
    return result
  }

  def getAuthenticator(username: String, password : String): javax.mail.Authenticator ={
    new Authenticator() {
      override def getPasswordAuthentication(): PasswordAuthentication = {
        new PasswordAuthentication(username, password)
      }
    }
  }
}
