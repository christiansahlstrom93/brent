package servlets.adservlets;

import business.beans.AdBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "EditAdServlet", urlPatterns = {"/EditAdServlet"})
public class EditAdServlet extends HttpServlet {
    @EJB
    private AdBeanLocal adBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        try {
            String firstname;
            String lastname;
            String email;
            String imageurl;
            String pricetype;
            double price;
            String title;
            String adText;
            String phone;
            String city;
            String imgorientation;
            int adid;

            processRequest(request, response);
            CustomJsonParser parser = new CustomJsonParser("mail", request);
            email = parser.getString();
            parser.setKey("firstname");
            firstname = parser.getString();
            parser.setKey("lastname");
            lastname = parser.getString();
            parser.setKey("adtext");
            adText = parser.getString();
            parser.setKey("pricetype");
            pricetype = parser.getString();
            parser.setKey("price");
            price = Double.parseDouble(parser.getString());
            parser.setKey("title");
            title = parser.getString();
            parser.setKey("phone");
            phone = parser.getString();
            parser.setKey("imageurl");
            imageurl = parser.getString();
            parser.setKey("place");
            city = parser.getString();
            parser.setKey("imgorientation");
            imgorientation = parser.getString();
            parser.setKey("adid");
            adid = Integer.parseInt(parser.getString());

            imageurl = imageurl.replace("\\", "\\\\\\");
            boolean state = adBean.updateAd(email, imageurl, pricetype, email, price, title, adText, firstname, lastname, phone, city, imgorientation, adid);

            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("state", state);
            out.print(json);

        } catch (JSONException ex) {
            System.out.println("Mauritz " + ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
