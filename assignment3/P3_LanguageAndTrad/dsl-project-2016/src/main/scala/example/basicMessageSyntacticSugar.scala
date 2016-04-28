package main.scala.example

import main.scala.constant.DefaultDSLconstants._
import main.scala.dslcode.MessageInterface
import main.scala.settings._

/**
  * Created by Cyril on 04-04-16.
  */
object basicMessageSyntacticSugar extends MessageInterface{

  def main(args: Array[String]) {

    selectMailSettings(DefaultMailSettings)

    from("sascha.vancauwelaert@uclouvain.be" and "pierre.schaus@uclouvain.be")
    add_from("cyril.devogelaere@student.uclouvain.be", "florian.thuin@student.uclouvain.be")

    to("leslie.lamport@latex.com")
    add_to("martin.odersky@scala.ch")

    cc_to("alan.kay@smalltalk.com" and "dan.ingalls@smalltalk.com")
    add_cc("adele.goldberg@smalltalk.com")

    bcc_to("yukihiro.Matsumoto@ruby.com")
    add_bcc("barack.obama@whitehouse.gov")

    set_subject("Good news everyone!")

    set_text("<h1>Hello guys</h1>" in bold and italic)
    add(space)
    add_text("and girl" in italic)
    add(new_line)
    add_line("Our students made a DSL to send emails. It's great. Try it." in bold)
    add("For real. This is a revolution.")
    add_text("Not compatible with the old iPhone.")

    send
  }
}