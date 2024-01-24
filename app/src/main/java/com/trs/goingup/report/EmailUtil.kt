package com.trs.goingup.report

import java.io.OutputStream
import java.util.Properties
import javax.activation.DataHandler
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource

class EmailUtil {
    companion object {

        fun sendEmail(
            toEmail: InternetAddress,
            subject: String,
            body: String? = null,
            attachments: List<Pair<String, ByteArrayDataSource>>? = null
        ) {
            val host = "smtp.gmail.com"
            val port = 465
            val user = ""
            val password = ""
            val fromEmail = InternetAddress("")

            val props = Properties()
            props["mail.transport.protocol"] = "smtp"
            props["mail.smtp.host"] = host
            props["mail.smtp.port"] = port.toString()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.socketFactory.port"] = port.toString()
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.socketFactory.fallback"] = "false"

            val authenticator = object: Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(user, password)
                }
            }

            val session = Session.getDefaultInstance(props, authenticator)
            val content = MimeMultipart()

            body?.let {
                content.addBodyPart(MimeBodyPart().apply {
                    setText(body)
                })
            }

            attachments?.forEach { (fn, ds) ->
                content.addBodyPart(MimeBodyPart().apply {
                    fileName = fn
                    dataHandler = DataHandler(ds)
                })
            }

            val message = MimeMessage(session)
            message.setFrom(fromEmail)
            message.setRecipient(Message.RecipientType.TO, toEmail)
            message.setSubject(subject)
            message.setContent(content)

            Transport.send(message)
        }
    }
}