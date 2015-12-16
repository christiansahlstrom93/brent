/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans.javahelpers;

/**
 *
 * @author Ant
 */
public class LoginCheck extends Server {

    public String Login(String username, String usrPassword) throws ClassNotFoundException {
        String result = "failed";
        try {
            String args = "SELECT password, firstname FROM Users WHERE email ='" + username + "';";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            if (getResultSet().next()) {
                if (getResultSet().getString("password").equals(usrPassword)) {
                    result = getResultSet().getString("firstname");
                } else {
                    result = "failed";
                }
            }

        } catch (Exception e) {

        }
        return result;
    }

}
