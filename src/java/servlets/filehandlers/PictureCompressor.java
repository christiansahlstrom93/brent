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
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Ant
 */
public class PictureCompressor {

    public static void compressAndShow(BufferedImage image, float quality) throws IOException {
        System.out.println(image);
        BufferedImage temp = null;
        // Get a ImageWriter for jpeg format.
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found");
        }
        ImageWriter writer = (ImageWriter) writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(32768);
        ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
        writer.setOutput(ios);

        if (image.getWidth() > image.getHeight()) {
            temp = resizeImage(image, 711, 400);
        } else {
            temp = resizeImage(image, 311, 400);
        }
        ios.flush(); // otherwise the buffer size will be zero!
        ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
        RenderedImage out = ImageIO.read(in);
        int size = bos.toByteArray().length;
        System.out.println(size);
        File file = new File("C:\\Users\\Ant\\Documents\\NetBeansProjects\\brent\\mFile.jpeg");
        FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        writer.write(null, new IIOImage(temp, null, null), param);
        output.close();
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
