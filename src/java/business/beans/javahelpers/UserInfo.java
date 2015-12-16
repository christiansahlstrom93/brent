/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans.javahelpers;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class UserInfo extends Server {

    public JSONArray getInfo(String email) {

        JSONArray ja = new JSONArray();
        JSONObject jo;

        try {
            String args;

            args = "SELECT * from ads where ownermail = '" + email + "'";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            while (getResultSet().next()) {
                jo = new JSONObject();
                String desc = getResultSet().getString("adtext");
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
                jo.put("ownerid", getResultSet().getInt("ownerid"));
                jo.put("adid", getResultSet().getInt("adid"));

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
                ja.put(jo);
            }

        } catch (Exception e) {
            System.out.println("ERROR I getads " + e);
        }

        return ja;
    }

}
