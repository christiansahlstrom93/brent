package business.beans;

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
                jo.put("description", "Fin cabbe till ett billigt pris. INDEX: " + i);
                jo.put("price", i);
                jo.put("location", location);
                jo.put("url", "http://www.wikstrands.com/Userfiles/Bild/huddig-1260.jpg");
                jo.put("imageURL", "http://www.maserati.com/mediaObject/examples/models/granCabrio/100730M_MY2013_2/resolutions/res-l964x10000/100730M_MY2013_2.jpg");
                ja.put(jo);
            }
            return ja;
        } catch (JSONException ex) {
        }

        return null;
    }

    @Override
    public JSONArray getUserCredentials(String usrName) {
        JSONArray ja = new JSONArray();
        try {
            JSONObject jo;
            jo = new JSONObject();
            jo.put("firstname", "Morgan");
            jo.put("lastname", "Persson");
            jo.put("mail", "supermorgan@live.se");
            jo.put("phone", "0733624439");
            jo.put("location", "Malmö");
            ja.put(jo);
            return ja;
        } catch (JSONException ex) {
        }

        return null;
    }
}
