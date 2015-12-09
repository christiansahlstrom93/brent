/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ant
 */
public class LoginCheck {

    String result = "";

    public String Login(String username, String usrPassword) throws ClassNotFoundException {
        try {
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String url = "jdbc:mysql://109.74.1.55:3306/Brent";
            String user = "root";
            String password = "r6vzpvjn";

            try {
                con = DriverManager.getConnection(url, user, password);
                st = con.createStatement();
                rs = st.executeQuery("SELECT password, firstname FROM Users WHERE email ='" + username + "';");
                if (rs.next()) {
                    System.out.println(rs.getString(1));

                    if (rs.getString("password").equals(usrPassword)) {
                        result = rs.getString("firstname");
                    }else{
                    result = "failed";
                    }
                } else {
                    result = "failed";
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            System.out.println(result + "     =resultatet");
        } catch (InstantiationException ex) {
            Logger.getLogger(LoginCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LoginCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

}
