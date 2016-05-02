package main.scala.example

import main.scala.dslcode.MessageBuilder
import main.scala.constant.DefaultDSLconstants._
import main.scala.settings.DefaultMailSettings

/**
  * Created by Cyril on 04-04-16.
  */
object basicMessageSyntaxicSugar {

  def main(args: Array[String]) {

    val toAddr: String = "abcd@gmail.com"
    val fromAddr: String = "web@gmail.com"

    implicit val message = MessageBuilder(DefaultMailSettings)

    message from (fromAddr + ", Hello@GB.com")
    message add_from "myLittlePony@ponyland.fr"
    message add_from ("dash@ponyland.fr", "rainbow@ponyland.fr")
    message add_from "he-man#musclor.hello"
    message add_from ("he-man#musclor.hello", "superman@batman.robin")

    message to ("poney@furry.com", toAddr)
    message add_to "mario@luigi.com" //and cc_to "bidule"
    message add_to "nope.poke"
    message add_to ("abc, def, ghi, jkl", "mno", "pqr, stu   ", "vwxyz")

    message cc_to "azerty"
    message add_cc "uiop"

    message bcc_to "qsdfghj"
    message add_bcc "klm"

    message set_subject "My subject"

    message set_text "<h1>Hello</h1>"
    message add_text "poney"
    message add new_line
    message add space
    message add_text "petit poney"

    message send
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
 * gerer gras, italique, ...
 * And cc_to ... (pour avoir tout sur la même ligne)
 * Script pour éviter le main bidule //TODO savoir si c'est possible
 *
 * TODO but I don't see how
 *
 * Virer les parenthèses pour les unary
 *
 */
