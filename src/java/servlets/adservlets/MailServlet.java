/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.adservlets;

import Javahelpers.MailHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.CustomJsonParser;
import org.json.JSONException;

/**
 *
 * @author Christian
 */
@WebServlet(name = "MailServlet", urlPatterns = {"/MailServlet"})
public class MailServlet extends HttpServlet {

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
        try {
            processRequest(request, response);
            
            CustomJsonParser jsonParser = new CustomJsonParser("sender", request);
            PrintWriter out = response.getWriter();
            String sender = jsonParser.getString();
            System.out.println("Sender " + sender);
            jsonParser.setKey("recepeint");
            String recepeint = jsonParser.getString();
            System.out.println("RE" + recepeint);
            jsonParser.setKey("name");
            String name = jsonParser.getString();
            jsonParser.setKey("msg");
            String msg = jsonParser.getString();
            msg += "\n\n\nMin email är: " + sender;
            String content = name + " är intresserad av din annons";
            out.println(new MailHandler("brentannons@gmail.com",recepeint,name,msg,content).sendMail());
        } catch (JSONException ex) {
            Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
