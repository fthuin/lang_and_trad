def apply(host : String, port : Int, username: String, password : String): LibraryMimeMessage = {

  val properties: Properties = System.getProperties
  properties.put("mail.smtp.auth", "true")
  properties.put("mail.smtp.host", host)
  properties.put("mail.smtp.port", port.toString)
  properties.put("mail.smtp.starttls.enable", "true");
  properties.put("mail.imap.host", host)
  properties.put("mail.imap.port", port.toString)
  properties.put("mail.store.protocol", "imaps")
  properties.put("mail.imap.starttls.enable", "true")
  apply(properties, username, password)
}
