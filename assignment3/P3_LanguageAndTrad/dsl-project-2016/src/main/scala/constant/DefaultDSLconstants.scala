package main.scala.constant

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

    //To put a string in bold, italic, ...
    //In case of invalid parameter, return string without changes
    def in(value : Int): String ={
      value match {
        case i : Int if i == italic => "<i>" + s + "</i>"
        case i : Int if i == bold => "<strong>" + s + "</strong>"
        case _ => s
      }
    }

    //In this case, just a disguised iin
    def and(value : Int): String ={
      return this.in(value)
    }

    //In this case concatenate the two values
    def and(value : String): String ={
      return s + ", " + value
    }
  }
}
