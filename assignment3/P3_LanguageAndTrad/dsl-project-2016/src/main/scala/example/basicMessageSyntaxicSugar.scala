package main.scala.example

import main.scala.dslcode.MessageInterface
import main.scala.constant.DefaultDSLconstants._

/**
  * Created by Cyril on 04-04-16.
  */
object basicMessageSyntaxicSugar extends MessageInterface{

  def main(args: Array[String]) {

    val toAddr: String = "abcd@gmail.com"
    val fromAddr: String = "web@gmail.com"

    from(fromAddr + ", Hello@GB.com" and "myLittlePony@ponyland.fr")
    add_from("dash@ponyland.fr", "rainbow@ponyland.fr")
    add_from("he-man#musclor.hello")
    add_from("he-man#musclor.hello", "superman@batman.robin")

    to("poney@furry.com", toAddr)
    add_to("mario@luigi.com") //TODO and cc_to "bidule"
    add_to("nope.poke")
    add_to("abc, def, ghi, jkl", "mno", "pqr, stu   ", "vwxyz")

    cc_to("azerty" and "beatrice")
    add_cc("uiop")

    bcc_to("qsdfghj")
    add_bcc("klm")

    set_subject("My subject")

    set_text("<h1>Hello</h1>" in bold and italic)
    add(space)
    add_text("poney" in italic)
    add(new_line)
    add(space)
    add_line("Hello new york !" in bold)
    add("J'aimes les gentils ")
    add_text("petit poney")

    send
  }
}

/*
 * TODO easy
 *
 * Dossier ou tout les mails sont exécutés, with a bulksend method
 * reply, reply to
 * Attachments
 * headers
 *
 * TODO hard
 *
 * Script pour éviter le main bidule //TODO savoir si c'est possible
 *
 * TODO but I don't see how
 *
 * Virer les parenthèses pour les unary
 *
 */
