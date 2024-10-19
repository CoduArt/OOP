import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bot {
    private static Image standImage = new ImageIcon("src/CardsPack/CardsWithGreen2.jpg").getImage();

    public static void main(String[] args) throws IOException {
        rotate(standImage, 270);
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage buff = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buff.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return buff;
    }

    public static Image rotate(Image image, int angle) throws IOException {
        BufferedImage bufImg = toBufferedImage(image);
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = bufImg.getWidth(), h = bufImg.getHeight();
        int neww, newh;
        if ((angle / 90) % 2 == 1) {
            neww = h;
            newh = w;
        } else {
            neww = w;
            newh = h;
        }
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2 , h/ 2);
        g.drawRenderedImage(bufImg, null);
        g.dispose();
        ImageIO.write(result, "png", new File("saved.png"));
        return result;
    }
}


