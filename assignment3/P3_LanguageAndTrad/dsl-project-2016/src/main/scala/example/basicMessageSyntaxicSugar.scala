package main.scala.example

import main.scala.code.MessageBuilder
import main.scala.code.DSLconstants._

/**
  * Created by Cyril on 04-04-16.
  */
object basicMessageSyntaxicSugar {

  def main(args: Array[String]) {

    val toAddr: String = "abcd@gmail.com"
    val fromAddr: String = "web@gmail.com"

    val message = MessageBuilder()

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

    message send()
  }
}

/*
 * TODO
 * gerer gras, italique, ...
 * Dossier ou tout les mails sont exécutés
 * Script pour éviter le main bidule
 * Settings
 * try catch des erreurs + vérifier adresses en fonction des settings
 * And cc_to ... (pour avoir tout sur la même ligne)
 * Virer les parenthèses pour les unary
 * headers
 * reply, reply to
 * Attachments
 */
