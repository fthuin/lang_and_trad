object basicMessageSyntacticSugar extends MessageInterface {

  def main(args: Array[String]) {
    /* Select pre-defined user settings */
    selectMailSettings(DefaultMailSettings)
    // Set and add my email address as a sender
    from("Hello@GB.com" and "myLittlePony@ponyland.fr")
    add_from("dash@ponyland.com")
    /* Set and add the recipients */
    to("poney@furry.com", toAddr)
    add_to("mario@luigi.com")
    /* Set and add carbon copy */
    cc_to("azerty" and "beatrice")
    add_cc("uiop")
    /*Set or add blind carbon copy */
    bcc_to("qsdfghj")
    add_bcc("klm")
    /* Set the subject */
    set_subject("My subject")
    /* Set text content as HTML */
    set_text("<h1>Hello</h1>" in bold and italic)
    add(new_line)
    add(space)
    add_line("Hello new york !" in bold)

    /* Send the email */
    send
}
