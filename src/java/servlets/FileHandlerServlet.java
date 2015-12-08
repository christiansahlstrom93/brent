package servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
@WebServlet("/upload")
@MultipartConfig
public class FileHandlerServlet extends HttpServlet {

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
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Christian\\Documents\\NetBeansProjects\\Brent\\mFile.jpg");
            try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                int count;
                byte[] buffer = new byte[4096];
                while ((count = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, count);
                }

                bos.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //TODO ändra path
        File f = new File("C:\\Users\\Christian\\Documents\\NetBeansProjects\\Brent\\mFile.jpg");
        Long lengthInBytes = f.length();
        Long lengthInKb = lengthInBytes / 1024;
        if (lengthInKb > 100) {
            BufferedImage i = ImageIO.read(new File("C:\\Users\\Christian\\Documents\\NetBeansProjects\\Brent\\mFile.jpg"));
            PictureCompressor.compressAndShow(i, 0.01f);
        } else {
            System.out.println("den var mindre");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
