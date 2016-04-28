package main.scala.utilities

import main.scala.settings.MailSettings

import scala.Array._

/**
  * Created by Cyril on 05-04-16.
  */
class settingsRelatedUtils(mailSetting: MailSettings){

  /*
   * Depending on settings, either stop execution after printing the error
   * Or simply continue the execution, skipping the address, after printing the error
   */
  def printErrorLine(error: String, addr: String){
    if (mailSetting.ShouldSkipAddressesOnError) println(error + ": '" + addr + "' is not a valid address ! This address was skipped")
    else if (mailSetting.ShouldGenerateExceptionOnError) {
      mailSetting.errorAllowedForSending = false
      throw new Exception(error + ": '" + addr + "' is not a valid address !")
    }
    else {
      mailSetting.errorAllowedForSending = false
      println(error + ": '" + addr + "' is not a valid address ! Please correct this error to send the message")
    }
  }

  /*
   * This method checks the validity of an email address using the regex in the DefaultSettings.
   * If the address is valid true is returned, else false will be returned
   */
  def checkAddressValidity(s: String): Boolean = {
    try {
      if (!s.matches(mailSetting.EmailValidityRegex)) throw new Exception("Error")
    }
    catch {
      case e:Exception => {
        val tab = e.getStackTrace.reverse
        for (i <- tab.indices) {
          if (tab(i).toString.contains("Message.scala:")) {
            printErrorLine(tab(i-1).toString, s)
            return false
          }
        }
        return false
      }
    }
    true
  }

  /*
   * This methods splits a string containing a succession of addresses.
   * It also check the validity of all addresses found.
   */
  def splitString(s : String): Array[String] = {
    s.split(",").map(s => s.trim()).filter(s => !s.equals("") && checkAddressValidity(s))
  }

  /*
   * Uses splitString on an array of string, returns an array containing all produced strings.
   */
  def ArrayStringSplitter(tab : Array[String]): Array[String] = {

    var result = Array[String]()
    for (elem <- tab){
      result = concat(result, splitString(elem))
    }
    result
  }
}
