/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import org.json.JSONObject;

/**
 *
 * @author Christian
 */
@WebServlet(name = "SaveAdServlet", urlPatterns = {"/SaveAdServlet"})
public class SaveAdServlet extends HttpServlet {

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
            System.out.println("HAPP");
            CustomJsonParser parser = new CustomJsonParser("firstname", request);

            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("name", parser.getString());
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
