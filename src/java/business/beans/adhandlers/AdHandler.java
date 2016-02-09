package business.beans.adhandlers;

import Javahelpers.SearchFilter;
import business.beans.javahelpers.Server;
import business.beans.usercredentials.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class AdHandler extends Server {

    //code for adding ad
    public boolean adAdd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation) {
        int id = -1;

        try {
            String args = "SELECT userid FROM Users WHERE email ='" + email + "';";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            if (getResultSet().next()) {
                id = getResultSet().getInt("userid");
            }

            try {
                String query = "INSERT into ads (imageurl,ownermail,pricetyp,price,title,adtext,firstname,lastname,phonenumber,ownerid,city,imgorientation)"
                        + " VALUES('" + imageurl + "','" + ownermail + "','" + pricetype + "'," + price + ",'" + title + "','" + adText + "','" + firstname + "','" + lastname + "','" + phonenumber + "'," + id + ",'" + city + "','" + imgorientation + "');";

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

//code for getting ads
    public JSONArray getAds(String location, String product) {
        SearchFilter searchFilter = new SearchFilter();
        JSONArray ja = new JSONArray();
        JSONObject jo;
        UserInfo userInfo = new UserInfo();
        userInfo.setConn(getConn());

        try {
            String args = searchFilter.getAdQuery(location, product);
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            while (getResultSet().next()) {
                jo = new JSONObject();
                String desc = getResultSet().getString("adtext");
                int userID = getResultSet().getInt("ownerid");
                jo.put("headermessage", "Du letar efter " + product + " i " + location);
                jo.put("title", getResultSet().getString("title"));
                jo.put("description", desc);
                jo.put("price", (int) getResultSet().getDouble("price") + ":-");
                jo.put("location", getResultSet().getString("city"));
                jo.put("imageURL", getResultSet().getString("imageurl"));
                jo.put("phone", getResultSet().getString("phonenumber"));
                jo.put("firstname", getResultSet().getString("firstname"));
                jo.put("lastname", getResultSet().getString("lastname"));
                jo.put("email", getResultSet().getString("ownermail"));
                jo.put("pricetype", getResultSet().getString("pricetyp"));
                jo.put("ownerid", userID);
                jo.put("adid", getResultSet().getString("adid"));

                if (desc.length() > 30) {
                    jo.put("preDesc", desc.substring(0, 30) + "...");
                } else {
                    jo.put("preDesc", desc);
                }
                if (getResultSet().getString("pricetyp").toLowerCase().equals("day")) {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/dag");
                } else {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/timme");
                }
                jo.put("imgorientation", getResultSet().getString("imgorientation"));
                jo.put("userInfo", userInfo.getUserCredentials(userID));
                ja.put(jo);
            }
        } catch (Exception e) {
            System.out.println("ERROR I getads " + e);
        }

        return ja;
    }

    public JSONArray getAdBasic(int id) {
        SearchFilter searchFilter = new SearchFilter();
        UserInfo userInfo = new UserInfo();
        JSONArray ja = new JSONArray();
        JSONObject jo;

        try {
            String args = "select * from ads where adid = " + id;
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));
            userInfo.setConn(getConn());
            while (getResultSet().next()) {
                jo = new JSONObject();
                String desc = getResultSet().getString("adtext");
                int userID = getResultSet().getInt("ownerid");
                jo.put("title", getResultSet().getString("title"));
                jo.put("description", desc);
                jo.put("price", (int) getResultSet().getDouble("price") + ":-");
                jo.put("location", getResultSet().getString("city"));
                jo.put("imageURL", getResultSet().getString("imageurl"));
                jo.put("phone", getResultSet().getString("phonenumber"));
                jo.put("firstname", getResultSet().getString("firstname"));
                jo.put("lastname", getResultSet().getString("lastname"));
                jo.put("email", getResultSet().getString("ownermail"));
                jo.put("pricetype", getResultSet().getString("pricetyp"));
                jo.put("adid", getResultSet().getString("adid"));
                jo.put("ownerid", userID);
                jo.put("userInfo", userInfo.getUserCredentials(userID));
                
                if (desc.length() > 10) {
                    jo.put("preDesc", desc.substring(0, 10) + "...");
                } else {
                    jo.put("preDesc", desc);
                }
                if (getResultSet().getString("pricetyp").toLowerCase().equals("day")) {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/dag");
                } else {
                    jo.put("displayprice", (int) getResultSet().getDouble("price") + ":-/timme");
                }
                jo.put("imgorientation", getResultSet().getString("imgorientation"));
                ja.put(jo);
            }
        } catch (Exception e) {
            System.out.println("ERROR I getads " + e);
        }

        return ja;
    }

    //code fpor updating ad
    public boolean updateAdd(String email, String imageurl, String pricetype, String ownermail, double price, String title, String adText, String firstname, String lastname, String phonenumber, String city, String imgorientation, int adId) {

        try {
            String query = "UPDATE ads set ownermail = '" + email + "',imageurl = '" + imageurl + "',pricetyp = '" + pricetype + "'"
                    + ",price = " + price + ",title = '" + title + "',adtext = '" + adText + "',firstname = '" + firstname + "',lastname = '" + lastname + "',phonenumber = '" + phonenumber + "'"
                    + ",city = '" + city + "',imgorientation = '" + imgorientation + "' where adid = " + adId;

            setStatement(getConn().createStatement());
            getStatement().executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error i update " + e);
        }

        return false;
    }

}
