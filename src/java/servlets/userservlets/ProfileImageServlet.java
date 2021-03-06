/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.userservlets;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import servlets.filehandlers.PictureCompressor;

/**
 *
 * @author Christian
 */
@WebServlet("/profile")
@MultipartConfig
public class ProfileImageServlet extends HttpServlet {
    public static String CHRILLEPATH = "/glassfish4/glassfish/domains/domain1/applications/Brent_Images/images/profile/";

    public static String returnPath = ":8080/Brent_Images/images/profile/";
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
        
           Part filePart = request.getPart("file");

        InputStream is = filePart.getInputStream();

        try {
            FileOutputStream fos = new FileOutputStream(CHRILLEPATH + "mfile.jpg");
            try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                int count;
                byte[] buffer = new byte[4096];
                while ((count = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, count);
                }

                bos.flush();
            }
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
        //TODO ändra path
        BufferedImage image = ImageIO.read(new File(CHRILLEPATH + "mfile.jpg"));

        try {
            PrintWriter out = response.getWriter();
            out.print(PictureCompressor.resize(image,CHRILLEPATH,returnPath));
        } catch (Exception ex) {
            System.out.println("error" +ex);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
