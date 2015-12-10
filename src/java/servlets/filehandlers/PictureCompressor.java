/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.filehandlers;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import static servlets.FileHandlerServlet.CHRILLEPATH;

/**
 *
 * @author Ant
 */
public class PictureCompressor {

    public static String resize(BufferedImage image) throws IOException {
        Random random = new Random();
       
        String path = "mfile" + random.nextInt(40302030)+".jpeg";
        
        BufferedImage temp = null;

        if (image.getWidth() > image.getHeight()) {
            temp = resizeImage(image, 711, 400);
        } else {
            temp = resizeImage(image, 311, 400);
        }
        File file = new File(CHRILLEPATH+path);

        ImageIO.write(temp, "jpg", file);
        
        return "http://194.47.40.116:8080/Brent/"+ path;
    }

    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;

    }

}
