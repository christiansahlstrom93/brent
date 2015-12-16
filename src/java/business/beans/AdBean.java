package business.beans;

import business.beans.adhandlers.AdHandler;
import business.beans.javahelpers.LoginCheck;
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
            JSONArray jsona = adHandler.getAds(location, product);
            adHandler.getConn().close();
            return jsona;
        } catch (Exception ex) {
            System.out.println("e " + ex);
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
            l.getConn().close();
        } catch (Exception ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    @Override
    public boolean addAd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation) {
        boolean state = false;
        try {
            AdHandler adHandler = new AdHandler();
            adHandler.connectToServer();
            state = adHandler.adAdd(email, imageurl, pricetype, ownermail, price, title, adText, firstname, lastname, phonenumber, city, imgorientation);
            adHandler.getConn().close();
        } catch (Exception ex) {
            System.out.println("Fel i bönan " + ex);
        }
        return state;
    }

   @Override
    public boolean updateAd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation,int adId) {
        boolean state = false;
        try {
            AdHandler adHandler = new AdHandler();
            adHandler.connectToServer();
            state = adHandler.updateAdd(email, imageurl, pricetype, ownermail, price, title, adText, firstname, lastname, phonenumber, city, imgorientation,adId);
            adHandler.getConn().close();
        } catch (Exception ex) {
            System.out.println("Fel i bönan " + ex);
        }
        return state;
    }
}
