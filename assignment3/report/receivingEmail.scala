object receiveMessageExample extends MessageInterface {

    def main(args: Array[String]) {
        val close = IMAPsettings(GoogleMailSettings)
        read(unread) // read the unread messages (in the console)
        setRead(unread) // set all unread messages as read
        read(messages) // read every messages (read and unread)
        close() // close all resources
    }
}
