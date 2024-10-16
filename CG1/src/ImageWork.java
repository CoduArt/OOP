import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWork {
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

    public static Image rotate(Image image, double angle) {
        BufferedImage bufImg = toBufferedImage(image);
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = bufImg.getWidth(), h = bufImg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(bufImg, null);
        g.dispose();
        return result;
    }

    public static void paintCardImage(Graphics g, PlayingCard card) {
        Image img = new ImageIcon("src/CardsPack/CardsWithGreen2.jpg").getImage();
//        g.drawImage(img, 100, 100, 100, 100, null);
        img = rotate(img, (90 * (card.getTurn() - 1)) % 360);
//        g.drawImage(img, 0, 0, 100, 100, null);
        int[] start = buildStart(img, card);
//        start[0] += img.getWidth(null) / 2;
//        start[1] += img.getHeight(null) / 2;
        paintImage(g, img, card.getCoordX(), card.getCoordY(), card.getCoordX() + card.getWight(), card.getCoordY() + card.getHeight(),
                start[0], start[1], start[0] + ImageCardCoordination.getWight(card.getTurn()), start[1] + ImageCardCoordination.getHeight(card.getTurn()));

    }

    private static int[] buildStart(Image img, PlayingCard card) {
        int[] start = new int[2];
        switch (card.getTurn()) {
            case 5, 1 -> {
                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2;
                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y);
            }
            case 2 -> {
//                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y);
//                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2;
            }
            case 3 -> {
                start[0] = img.getWidth(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X)) - ImageCardCoordination.WEIGHT;
                start[1] = img.getHeight(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y) + card.getDenomination() / 2) - ImageCardCoordination.HEIGHT;
            }
            case 4 -> {
                start[0] = img.getWidth(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y)) - ImageCardCoordination.HEIGHT;
                start[1] = img.getHeight(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2) - ImageCardCoordination.WEIGHT;
            }
        }
        return start;
    }

    private static void paintImage(Graphics g, Image img, int VX1, int VY1, int VX2, int VY2, int IX1, int IY1, int IX2, int IY2) {
        g.drawImage(img, VX1, VY1, VX2, VY2,
                IX1, IY1 , IX2, IY2, null);
    }
}

class ImageCardCoordination {
    final static int GLOBAL_INDENT = 60;
    final static int INDENT_X = 26;
    final static int INDENT_Y = 50;
    final static int HEIGHT = 276;
    final static int WEIGHT = 197;

    public static int getWight(int turn) {
        if (turn % 2 == 1) return WEIGHT;
        return HEIGHT;
    }

    public static int getHeight(int turn) {
        if (turn % 2 == 1) return HEIGHT;
        return WEIGHT;
    }
}
