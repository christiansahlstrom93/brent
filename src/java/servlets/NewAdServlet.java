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
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
@WebServlet(name = "NewAdServlet", urlPatterns = {"/NewAdServlet"})
public class NewAdServlet extends HttpServlet {

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
            String name = jsonParser.getString();
            System.out.println("************");
            System.out.println("NAME: " + name);
            System.out.println("************");

            JSONObject mainObj = new JSONObject();
            mainObj.put("credentials", adBean.getUserCredentials(name));
            out.print(mainObj);

        } catch (Exception e) {

        }
    }
}
