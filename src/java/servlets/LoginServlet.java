/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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

/**
 *
 * @author Ant
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            CustomJsonParser jsonParser = new CustomJsonParser("username", request);
            PrintWriter out = response.getWriter();
            String username = jsonParser.getString();
            System.out.println("************");
            System.out.println("username " + username);
            jsonParser.setKey("password");
            String password = jsonParser.getString();
            System.out.println("password " + password + "");
            System.out.println("************");

            String result = adBean.loginCheck(username, password);
            out.print(result);


            /*JSONObject mainObj = new JSONObject();
            mainObj.put("usr", adBean.loginCheck(username, password));
            out.print(mainObj);*/
        } catch (Exception e) {
            System.out.println("servlet error" + e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
