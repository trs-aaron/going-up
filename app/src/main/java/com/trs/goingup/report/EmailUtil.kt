package com.trs.goingup.report

import java.util.Properties
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage

class EmailUtil {

    fun sendEmail() {
        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        //props.setProperty("mail.host", "")
        props["mail.smtp.host"] = ""
        props["mail.smtp.port"] = "smtp.gmail.com"
        //props["mail.smtp.auth"] = "true"
        props["mail.smtp.socketFactory.port"] = "465"
        //props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        //props["mail.smtp.socketFactory.fallback"] = "false"
        //props.setProperty("mail.smtp.quitwait", "false")

        val session = Session.getDefaultInstance(props, null)
        val message = MimeMessage(session)

        Transport.send(message)
    }
}