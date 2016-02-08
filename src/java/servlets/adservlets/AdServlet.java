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
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
@WebServlet(name = "AdServlet", urlPatterns = {"/AdServlet"})
public class AdServlet extends HttpServlet {
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
            CustomJsonParser jsonParser = new CustomJsonParser("location", request);
            PrintWriter out = response.getWriter();
            String location = jsonParser.getString();
            System.out.println("************");
            System.out.println("LOCATION " + location);
            jsonParser.setKey("search");
            String search = jsonParser.getString();
            System.out.println("SEARCH WORD " + search + "");
            System.out.println("************");

            JSONObject mainObj = new JSONObject();
            //Sending back the json array gathered from the business logic
            mainObj.put("ads", adBean.getAdListData(location, search));
            out.print(mainObj);

        } catch (Exception e) {

        }
    }

}
