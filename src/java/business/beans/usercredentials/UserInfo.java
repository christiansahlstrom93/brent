
package business.beans.usercredentials;

import business.beans.javahelpers.Server;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class UserInfo extends Server {

    public JSONArray getUserCredentials(String email) {
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();

        try {
            String args = "SELECT * FROM Users WHERE email ='" + email + "';";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            if (getResultSet().next()) {
                jo.put("firstname", getResultSet().getString("firstname"));
                jo.put("lastname", getResultSet().getString("lastname"));
                jo.put("mail", getResultSet().getString("email"));
                jo.put("phone", getResultSet().getString("phonenumber"));
                jo.put("city", getResultSet().getString("city"));
                jo.put("firstname", getResultSet().getString("firstname"));
                ja.put(jo);
            }

        } catch (Exception e) {
            System.out.println("ERROR I USERINFO " + e);
        }
        
        return ja;
    }

}
