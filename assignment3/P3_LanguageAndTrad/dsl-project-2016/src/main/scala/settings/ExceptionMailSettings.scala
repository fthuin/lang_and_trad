package main.scala.settings

/**
  * Created by Cyril on 05-04-16.
  */
object ExceptionMailSettings extends MailSettings{

  //The default object is the same as the trait
  override val ShouldSkipAddressesOnError = false
  override val ShouldGenerateExceptionOnError = true
}
