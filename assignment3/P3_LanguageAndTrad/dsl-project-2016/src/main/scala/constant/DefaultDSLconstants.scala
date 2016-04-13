package main.scala.constant

import main.scala.dslcode.MessageBuilder
import main.scala.settings.DefaultMailSettings

/**
  * Created by Cyril on 04-04-16.
  */
object DefaultDSLconstants {

  //Char constants

  val space = ' '
  val new_line = '\n'

  //Style constants

  val italic = 0
  val bold = 1

  //String improvements to put in bold or other stuff TODO move elsewhere

  implicit class StringImprovements(s: String) {

    def in(value : Int): String ={
      if(value == italic){
        return "<i>" + s + "</i>"
      }
      else if(value == bold){
        return "<strong>" + s + "</strong>"
      }
      else{
        return s
      }
    }

    def and(value : Int): String ={
      return this.in(value)
    }

    def and(value : String): String ={
      return s + ", " + value
    }
  }
}
