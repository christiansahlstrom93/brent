/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import javax.ejb.Local;

/**
 *
 * @author Ant
 */
@Local
public interface UserBeanLocal {

    String createUser(String username, String firstname, String email, String password, String phonenumber, String address, String city, String areacode);

}
