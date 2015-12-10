/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import business.beans.javahelpers.CreateUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Ant
 */
@Stateless
public class UserBean implements UserBeanLocal {

    @Override
    public String createUser(String lastname, String firstname, String email, String password, String phonenumber, String address, String city, String areacode) {
        String result = "";
        try {
            CreateUser c = new CreateUser();
            c.connectToServer();
            result = c.createUser(lastname, firstname, email, password, phonenumber, address, city, areacode);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
