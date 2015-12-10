package business.beans;

import business.beans.adhandlers.AdHandler;
import business.beans.javahelpers.LoginCheck;
import business.beans.usercredentials.UserInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.json.JSONArray;

/**
 *
 * @author Christian
 */
@Stateless
public class AdBean implements AdBeanLocal {

    @Override
    public JSONArray getAdListData(String location, String product) {
        try {
            AdHandler adHandler = new AdHandler();
            adHandler.connectToServer();
            return adHandler.getAds(location, product);
        } catch (Exception ex) {
        }

        return null;
    }

    @Override
    public JSONArray getUserCredentials(String usrName) {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.connectToServer();
            return userInfo.getUserCredentials(usrName);
        } catch (Exception ex) {
        }

        return null;
    }

    @Override
    public String loginCheck(String username, String password) {
        String result = "";

        try {
            LoginCheck l = new LoginCheck();
            l.connectToServer();
            result = l.Login(username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    @Override
    public boolean addAd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber,String city) {
        try {
            AdHandler adHandler = new AdHandler();
            adHandler.connectToServer();
            return adHandler.adAdd(email, imageurl, pricetype, ownermail, price, title, adText, firstname, lastname, phonenumber,city);
        } catch (Exception ex) {
            System.out.println("Fel i bönan " + ex);
        }
        return false;
    }
}
