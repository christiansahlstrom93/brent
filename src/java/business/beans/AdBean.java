/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
@Stateless
public class AdBean implements AdBeanLocal {

    @Override
    public JSONArray getAdListData(String location, String product) {
        JSONArray ja = new JSONArray();
        try {
            JSONObject jo;

            for (int i = 0; i < 10; i++) {
                jo = new JSONObject();
                jo.put("headermessage", "Du letar efter " + product + " i " + location);
                jo.put("title", product);
                jo.put("description", "Mycket fin borr i sina bästa dagar. Uthyres till svenskar endast."
                        + "Sänder kramar. MVH Jimmy." + i);
                jo.put("price", i);
                jo.put("location", location);
                jo.put("url", "http://www.wikstrands.com/Userfiles/Bild/huddig-1260.jpg");
                jo.put("imageURL", "http://www.maserati.com/mediaObject/examples/models/granCabrio/100730M_MY2013_2/resolutions/res-l964x10000/100730M_MY2013_2.jpg");
                ja.put(jo);
            }
            return ja;
        } catch (JSONException ex) {
            Logger.getLogger(AdBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
