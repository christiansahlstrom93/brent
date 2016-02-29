package business.beans.adhandlers;

import business.beans.javahelpers.Server;
import business.beans.usercredentials.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
public class NotificationHandler extends Server {

    public boolean sendNotification(String s, String rec, int id, String smail, String date) {
        
        try {

            String args = "select receiver from interests where adid = " + id + " AND opened = " + false;
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));
            
            while (getResultSet().next()) {
                return false;
            }
            

            String query = "INSERT into interests (interested,receiver,date,opened,adid,interestedemail)"
                    + " VALUES('" + s + "','" + rec + "','" + date + "'," + false + "," + id + ",'" + smail + "');";
            setStatement(getConn().createStatement());
            getStatement().executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error i insert " + e);
        }
        return false;
    }

    public JSONArray getNotification(String email) {

        JSONArray ja = new JSONArray();
        JSONObject jo;
        UserInfo userInfo = new UserInfo();
        AdHandler adHandler = new AdHandler();

        try {
            String args = "select * from interests where receiver = '" + email + "'";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));
            userInfo.setConn(getConn());
            adHandler.setConn(getConn());

            while (getResultSet().next()) {
                jo = new JSONObject();
                jo.put("sender", getResultSet().getString("interested"));
                jo.put("headline", getResultSet().getString("interested") + " vill hyra din ");
                jo.put("date", getResultSet().getString("date"));
                jo.put("opened", getResultSet().getBoolean("opened"));
                jo.put("adid", getResultSet().getInt("adid"));
                jo.put("interestedEmail", getResultSet().getString("interestedemail"));
                jo.put("userInfo", userInfo.getUserCredentials(getResultSet().getString("interestedemail")));
                jo.put("adinfo", adHandler.getAdBasic(getResultSet().getInt("adid")));
                ja.put(jo);
            }

        } catch (Exception e) {
            System.out.println("Error i insert " + e);
        }

        return ja;
    }
}
