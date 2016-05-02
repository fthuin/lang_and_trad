package example

import main.scala.dslcode.MessageInterface
import main.scala.constant.DefaultDSLconstants._
import main.scala.settings.DefaultMailSettings

import scala.collection.mutable.ArrayBuffer

/**
  * Created by florian on 28/04/16.
  */
object messageTemplate extends MessageInterface {
  def main(args: Array[String]) {
    selectMailSettings(DefaultMailSettings)
    var parts = ArrayBuffer[(String, String, String)]()
    parts += (("cyril.devogelaere@poneycity.be", "Cyril de Vogelaere", "friday, avril 23 at 2pm"))
    parts += (("lingi2132@ucl.be", "Pierre Schaus", "friday, avril 23 at 3pm"))
    parts += (("master@github.com", "Github Master", "friday, avril 23 at 4pm"))

    repeat {
      (email, name, date) => {
        clearAll
        from("florian.cyril@lingi2132.group")
        to(email)
        add_line("Hello " + name + ",")
        add_text("Your oral exam for the course LINGI2132 will take place at BARB12 on ")
        add_line(date in bold)
        add_line("See you!")
        send
      }
    } foreach parts
  }
}
