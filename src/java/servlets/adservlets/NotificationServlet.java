package servlets.adservlets;

import business.beans.AdBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.CustomJsonParser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
@WebServlet(name = "NotificationServlet", urlPatterns = {"/NotificationServlet"})
public class NotificationServlet extends HttpServlet {

    @EJB
    private AdBeanLocal adBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            CustomJsonParser jsonParser = new CustomJsonParser("get", request);
            String state = jsonParser.getString();
            System.out.println("STATE " + jsonParser.getString());
            if (state.equals("false")) {
                PrintWriter out = response.getWriter();
                jsonParser.setKey("sender");
                String sender = jsonParser.getString();
                System.out.println("Sender " + sender);
                jsonParser.setKey("recepeint");
                String recepeint = jsonParser.getString();
                System.out.println("RE" + recepeint);
                jsonParser.setKey("email");
                String email = jsonParser.getString();
                jsonParser.setKey("id");
                int id = Integer.parseInt(jsonParser.getString());
                System.out.println("MAIL " + email + " ID " + id);
                out.print(adBean.sendNotification(sender, recepeint, id, email));
            } else {

                try {
                    PrintWriter out = response.getWriter();
                    jsonParser.setKey("mail");
                    String mail = jsonParser.getString();
                    JSONObject mainObj = new JSONObject();
                    mainObj.put("notifications",adBean.getNotifications(mail));
                    System.out.println(mainObj);
                    out.print(mainObj);

                } catch (JSONException ex) {
                    System.out.println("ERROR I NOT " + ex);
                }
            }

        } catch (JSONException ex) {
            Logger.getLogger(NotificationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
