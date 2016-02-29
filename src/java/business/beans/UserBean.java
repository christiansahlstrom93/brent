/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import business.beans.javahelpers.CreateUser;
import business.beans.javahelpers.UserInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.json.JSONArray;

/**
 *
 * @author Ant
 */
@Stateless
public class UserBean implements UserBeanLocal {

    //adding user to DB
    @Override
     public boolean createUser(String lastname, String firstname, String email, String password, String phonenumber, String address, String city, String areacode , String imgURL, String orientation) {
      boolean result = false;
        try {
            CreateUser c = new CreateUser();
            c.connectToServer();
            result = c.createUser(lastname, firstname, email, password, phonenumber, address, city, areacode,imgURL,orientation);
            c.getConn().close();
        } catch (Exception ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //Getting information for profile
    @Override
    public JSONArray getMyPagesInfo(String email) {

        try {
            JSONArray jSONArray;
            UserInfo userInfo = new UserInfo();
            userInfo.connectToServer();
            jSONArray = userInfo.getInfo(email);
            userInfo.getConn().close();
            return jSONArray;
        } catch (Exception ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Getting user data
    @Override
    public JSONArray getUserCredentials(String usrName) {
        try {
            JSONArray jSONArray;
            business.beans.usercredentials.UserInfo userInfo = new business.beans.usercredentials.UserInfo();
            userInfo.connectToServer();
            jSONArray = userInfo.getUserCredentials(usrName);
            userInfo.getConn().close();
            return jSONArray;
        } catch (Exception ex) {
        }

        return null;
    }
    @Override
    public String checkEmail(String email) {
                String result = "";
        try {
            CreateUser c = new CreateUser();
            c.connectToServer();
            result = c.emailCheck(email);
            c.getConn().close();
        } catch (Exception ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
   }

