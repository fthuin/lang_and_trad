/*
import main.scala.dslcode.MessageBuilder
import main.scala.constant.DefaultDSLconstants._
import main.scala.settings.DefaultMailSettings

val toAddr: String = "abcd@gmail.com"
val fromAddr: String = "web@gmail.com"

val message = MessageBuilder(DefaultMailSettings)

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
*/