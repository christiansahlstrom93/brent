/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Javahelpers;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Christian
 */
public class SmtpAuthenticator extends Authenticator {

    String username, password;

    public SmtpAuthenticator(String user, String pass) {
        super();
        this.username = user;
        this.password = pass;
    }
    //Check authentication for password on email
    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}
