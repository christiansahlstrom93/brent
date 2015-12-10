package business.beans.adhandlers;

import business.beans.javahelpers.Server;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class AdHandler extends Server {

    public boolean adAdd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city) {
        int id = -1;

        try {
            String args = "SELECT userid FROM Users WHERE email ='" + email + "';";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            if (getResultSet().next()) {
                id = getResultSet().getInt("userid");
            }

            try {
                String query = "INSERT into ads (imageurl,ownermail,pricetyp,price,title,adtext,firstname,lastname,phonenumber,ownerid,city)"
                        + " VALUES('" + imageurl + "','" + ownermail + "','" + pricetype + "'," + price + ",'" + title + "','" + adText + "','" + firstname + "','" + lastname + "','" + phonenumber + "'," + id + ",'" + city + "');";

                setStatement(getConn().createStatement());
                getStatement().executeUpdate(query);
                return true;
            } catch (Exception e) {
                System.out.println("Error i insert " + e);
            }

        } catch (Exception e) {
            System.out.println("ERROR I USERINFO " + e);
        }
        return false;
    }

    public JSONArray getAds(String location, String product) {

        JSONArray ja = new JSONArray();
        JSONObject jo;

        try {
            String args = "SELECT * from ads where city = '" + location + "'";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            while (getResultSet().next()) {
                jo = new JSONObject();
                jo.put("headermessage", "Du letar efter " + product + " i " + location);
                jo.put("title", getResultSet().getString("title"));
                jo.put("description", getResultSet().getString("adtext"));
                jo.put("price", (int) getResultSet().getDouble("price") + ":-");
                jo.put("location", getResultSet().getString("city"));
                jo.put("imageURL", getResultSet().getString("imageurl"));
                jo.put("phone", getResultSet().getString("phonenumber"));
                jo.put("firstname", getResultSet().getString("firstname"));
                jo.put("lastname", getResultSet().getString("lastname"));
                jo.put("email", getResultSet().getString("ownermail"));
                jo.put("pricetype", getResultSet().getString("pricetyp"));
                jo.put("ownerid",getResultSet().getInt("ownerid"));
                jo.put("salesmessage","Hyrs ut utav " + getResultSet().getString("firstname"));
                if (getResultSet().getString("pricetyp").toLowerCase().equals("day")) {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/dag");
                } else {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/timme");
                }
                
                ja.put(jo);
            }
        } catch (Exception e) {
            System.out.println("ERROR I getads " + e);
        }

        return ja;
    }

}
