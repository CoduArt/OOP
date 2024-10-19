import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWork  {
    private static int[][] indents = new int[4][3];
    private static Image standImage = new ImageIcon("src/CardsPack/CardsWithGreen2.jpg").getImage();

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

    public static Image rotate(Image image, int angle) {
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
//        ImageIO.write(result, "png", new File("saved.png"));
        return result;
    }

    public static void paintCardImage(Graphics g, PlayingCard card) {
//        g.drawImage(img, 100, 100, 100, 100, null);
        Image img;
        if (card.getTurn() != 1 && card.getTurn() != 3 && card.getTurn() != 5) {
            img = rotate(standImage, (90 * (card.getTurn() - 1)) % 360);
        } else {
            img = standImage;
        }
//        g.drawImage(img, 0, 0, 100, 100, null);
        int[] start = buildStart(img, card);
//        start[0] += img.getWidth(null) / 2;
//        start[1] += img.getHeight(null) / 2;
        paintImage(g, img, card.getCoordX(), card.getCoordY(), card.getCoordX() + card.getWight(), card.getCoordY() + card.getHeight(),
                start[0], start[1], start[0] + ImageCardCoordination.getWight(card.getTurn()), start[1] + ImageCardCoordination.getHeight(card.getTurn()));

    }

    private static int[] buildStart(Image img, PlayingCard card) {
        int[] start = new int[2];
        int[] indent = findNewCenter(img, card);
        switch (card.getTurn()) {
            case 5, 1, 3 -> {
//                card = new PlayingCard(Location.PLAYER_TWO_CARD_1, new Card(1, 1));
                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2 + indent[0];
                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y) + indent[1];
            }
            case 2 -> {
                start[0] = img.getWidth(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y) + indent[0]) - ImageCardCoordination.HEIGHT;
                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2 + indent[1];
            }
//            case 3 -> {
//                card = new PlayingCard(Location.PLAYER_TWO_CARD_1, new Card(1, 1));
//                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2 + indent[0];
//                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y) + indent[1];
////                System.out.println(img.getHeight(null));
////                start[0] = 2687;
////                start[1] = 1960; //Проблема в Y координате
//            }
            case 4 -> {
                card = new PlayingCard(Location.PLAYER_TWO_CARD_1, new Card(1, 1));
                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y) + indent[0];
                start[1] = img.getHeight(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2 + indent[1]) - ImageCardCoordination.WEIGHT;
            }
        }
        return start;
    }

    private static int[] findNewCenter(Image img, PlayingCard card) {
        Color colorT;
        int turn = card.getTurn() != 5 ? card.getTurn() : 1;
        if (indents[turn - 1][2] != 0) {
            return indents[turn - 1];
        }
        for (int x = 0; x < img.getWidth(null); x++) {
            for (int y = 0; y < img.getHeight(null); y++) {
                colorT = new Color(toBufferedImage(img).getRGB(x,y), true);
                if (colorT.equals(Viewport.BG_COLOR)) {
                    indents[turn - 1][0] = x;
                    indents[turn - 1][1] = y;
                    indents[turn - 1][2] = card.getTurn();
                    return indents[turn - 1];
                }
            }
        }
        return null;
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
