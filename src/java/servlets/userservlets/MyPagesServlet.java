package servlets.userservlets;

import business.beans.UserBeanLocal;
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
@WebServlet(name = "MyPagesServlet", urlPatterns = {"/MyPagesServlet"})
public class MyPagesServlet extends HttpServlet {

    @EJB
    private UserBeanLocal userBean;

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
        processRequest(request, response);
        try {
            processRequest(request, response);

            CustomJsonParser jsonParser = new CustomJsonParser("email", request);
            PrintWriter out = response.getWriter();
            String email = jsonParser.getString();
            JSONObject mainObj = new JSONObject();
            mainObj.put("data",userBean.getUserCredentials(email));    
            mainObj.put("ads", userBean.getMyPagesInfo(email));
            out.print(mainObj);

        } catch (JSONException ex) {
            Logger.getLogger(MyPagesServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
