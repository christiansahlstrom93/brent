/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import javax.ejb.Local;
import org.json.JSONArray;

/**
 *
 * @author Christian
 */
@Local
public interface AdBeanLocal {

    JSONArray getAdListData(String location, String product);

    String loginCheck(String username, String password);

    boolean addAd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation);

    boolean updateAd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation, int adId);

    boolean sendNotification(String s, String rec, int id, String smail, String date);
    
    JSONArray getNotifications(String mail);

}
