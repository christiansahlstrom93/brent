/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.beans.AdBeanLocal;
import business.beans.UserBeanLocal;
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
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUserServlet"})
public class CreateUserServlet extends HttpServlet {

    @EJB
    private UserBeanLocal userBean;

  

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
            CustomJsonParser jsonParser = new CustomJsonParser("firstname", request);
            PrintWriter out = response.getWriter();
            String firstname = jsonParser.getString();
            System.out.println("************");
            System.out.println("firstname " + firstname);
            jsonParser.setKey("lastname");
            String lastname = jsonParser.getString();
            System.out.println("lastname " + lastname + "");
            System.out.println("************");
            jsonParser.setKey("phone");
            String phone = jsonParser.getString();
            System.out.println("phone " + phone + "");
            System.out.println("************");
            jsonParser.setKey("personnumber");
            String personnumber = jsonParser.getString();
            System.out.println("p number " + personnumber + "");
            System.out.println("************");
            jsonParser.setKey("mail");
            String mail = jsonParser.getString();
            System.out.println("mail " + mail + "");
            System.out.println("************");
            jsonParser.setKey("city");
            String city = jsonParser.getString();
            System.out.println("city " + city + "");
            System.out.println("************");
            jsonParser.setKey("postalcode");
            String postalcode = jsonParser.getString();
            System.out.println("postal code " + postalcode + "");
            System.out.println("************");
            jsonParser.setKey("address");
            String address = jsonParser.getString();
            System.out.println("postal code " + postalcode + "");
            System.out.println("************");
            jsonParser.setKey("password");
            String password = jsonParser.getString();
            System.out.println("password " + password + "");
            System.out.println("************");

            String result = userBean.createUser(lastname,firstname, mail,password, phone,  address,  city,  postalcode);
            out.print(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
