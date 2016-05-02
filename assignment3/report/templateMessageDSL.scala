object templateMessageDSL extends MessageInterface {
    def main(args: Array[String]) {
        /* Select pre-defined user settings */
        selectMailSettings(DefaultMailSettings)
        /* Create the data for the template */
        var parts = ArrayBuffer[(String, String, String)]()
        parts += (("cyril.devogelaere@poneycity.be", "Cyril de Vogelaere", "friday, avril 23 at 2pm"))
        parts += (("lingi2132@ucl.be", "Pierre Schaus", "friday, avril 23 at 3pm"))
        parts += (("master@github.com", "Github Master", "friday, avril 23 at 4pm"))
        /* A custom loop that will take one by one each tuple to create a mail with the given information*/
        repeat {(email,name,date) => {
            clearAll // Clear all information from previous mail
            from("florian.cyril@lingi2132.group") // Set a from address
            to(email) // Set a recipient
            add_line("Hello " + name + ",") // Add text ending with a terminating line
            add_text("Your oral exam for the course LINGI2132 will take place at BARB12 on ")
            add_line(date in bold)
            add_line("See you!")
            send // Send the mail
            }
        } foreach parts
    }
}
