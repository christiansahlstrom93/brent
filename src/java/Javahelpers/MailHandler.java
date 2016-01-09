/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Javahelpers;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import servlets.adservlets.MailServlet;
import static sun.security.jgss.GSSUtil.login;

/**
 *
 * @author Christian
 */
public class MailHandler {

    public String sender, recipient, name, msg,content;

    public MailHandler(String sender, String recipient, String name,String msg,String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.name = name;
        this.msg = msg;
        this.content = content;
    }

    public String sendMail() throws UnsupportedEncodingException {

        try {
            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            System.out.println(this.sender);
            SmtpAuthenticator authentication = new SmtpAuthenticator(this.sender,"mauritztheman");
            javax.mail.Message msg = new MimeMessage(Session
                    .getInstance(props, authentication));
            Address mailsender = new InternetAddress(this.sender, this.name);
            Address receiver = new InternetAddress(this.recipient);
            Session session = Session.getInstance(props);
            msg.setContent(this.msg, "text/plain");
            msg.setFrom(mailsender);
            msg.setRecipient(Message.RecipientType.TO, receiver);
            msg.setSubject(this.content);
            Transport transport = session.getTransport("smtp");
            transport.connect(this.sender, "mauritztheman");
            Transport.send(msg);
            return "SUCCESS";
        } catch (MessagingException ex) {
            Logger.getLogger(MailHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "FAIL";
    }
}
