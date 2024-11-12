import CardsBase.GameParams;
import CardsBase.PlayingCard;
import Trackers.ChipTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWork  {
    private static final Image CARD_SHIRT_IMAGE = new ImageIcon("src/CardsPack/Shirt.png").getImage();
    private static final Image STAND_IMAGE = new ImageIcon("src/CardsPack/CardsWithGreen2.jpg").getImage();
    private static final Image CHIP_IMAGE = new ImageIcon("src/CardsPack/chip3.png").getImage();
    private static final Font STATIC_FONT = new Font(null, Font.PLAIN, 20);

    public static void paintCardShirt(Graphics g, PlayingCard card) {
        g.drawImage(CARD_SHIRT_IMAGE, card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), null);
    }

    public static void paintChip(Graphics g, ChipTracker chipTracker) {
        g.drawImage(CHIP_IMAGE, chipTracker.getX(), chipTracker.getY(),chipTracker.getChipWeight(), chipTracker.getChipHeight(),
                GameParams.BG_COLOR, null);
        g.setFont(STATIC_FONT);
        g.drawString(chipTracker.getMoney().toString() + "$", getChipCenterX(chipTracker), getChipCenterY(chipTracker));
    }

    private static int getChipCenterX(ChipTracker chipTracker) {
        int x = chipTracker.getX() + chipTracker.getChipWeight() / 2 - STATIC_FONT.getSize() / 4;
        x -= chipTracker.getMoney().toString().length() * STATIC_FONT.getSize() / 4;
        return x;
    }

    private static int getChipCenterY(ChipTracker chipTracker) {
        return chipTracker.getY() + chipTracker.getChipHeight() / 2 + STATIC_FONT.getSize() / 3;
    }

    public static void paintBetTracker(Graphics g, Player player) {
        g.setFont(STATIC_FONT);
        g.drawString(player.getBet().toString() + "$", getMoneyTrackerX(player.getHandCards()[0], player.getHandCards()[1], player.getBet()),
                getMoneyTrackerY(player.getHandCards()[0], player.getHandCards()[1]));
    }

    private static int getMoneyTrackerX(PlayingCard card1, PlayingCard card2, Integer bet) {
        int turn = card1.getTurn() == 1 || card1.getTurn() == 4 ? -1 : 1;
        int center = (card1.getCoordX() + card2.getCoordX() + card2.getWight()) / 2 - STATIC_FONT.getSize() / 4;
        center += card1.getTurn() % 2 == 1 ? 0 : (card1.getHeight() / 2 + STATIC_FONT.getSize() * 3) * turn;
        center -= bet.toString().length() * STATIC_FONT.getSize() / 4;
        return center;
    }

    private static int getMoneyTrackerY(PlayingCard card1, PlayingCard card2) {
        int turn = card1.getTurn() == 1 || card1.getTurn() == 4 ? -1 : 1;
        int center = (card1.getCoordY() + card2.getCoordY() + card2.getHeight()) / 2 + STATIC_FONT.getSize() / 3;
        center += card1.getTurn() % 2 == 0 ? 0 : (card1.getHeight() / 2 + STATIC_FONT.getSize() * 2) * turn;
        return center;
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

    public static Image rotate(Image image, int angle) {
        BufferedImage bufImg = toBufferedImage(image);
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
        return result;
    }

    public static void paintCardImage(Graphics g, PlayingCard card) {
        Image img;
        if (card.getTurn() != 1 && card.getTurn() != 3 && card.getTurn() != 5) {
            img = rotate(STAND_IMAGE, (90 * (card.getTurn() - 1)) % 360);
        } else {
            img = STAND_IMAGE;
        }
        int[] start = buildStart(img, card);
        paintImage(g, img, card.getCoordX(), card.getCoordY(), card.getCoordX() + card.getWight(), card.getCoordY() + card.getHeight(),
                start[0], start[1], start[0] + ImageCardCoordination.getWight(card.getTurn()), start[1] + ImageCardCoordination.getHeight(card.getTurn()));

    }

    private static int[] buildStart(Image img, PlayingCard card) {
        int[] start = new int[2];
        switch (card.getTurn()) {
            case GameParams.CENTER, GameParams.DOWN, GameParams.UP -> {
                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2;
                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y);
            }
            case GameParams.LEFT -> {
                start[0] = img.getWidth(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y)) - ImageCardCoordination.HEIGHT;
                start[1] = ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2;
            }
            case GameParams.RIGHT -> {
                start[0] = ImageCardCoordination.GLOBAL_INDENT + (card.getSuit() - 1) * (ImageCardCoordination.HEIGHT + ImageCardCoordination.INDENT_Y);
                start[1] = img.getHeight(null) - (ImageCardCoordination.GLOBAL_INDENT + (card.getDenomination() - 1) * (ImageCardCoordination.WEIGHT + ImageCardCoordination.INDENT_X) + card.getDenomination() / 2) - ImageCardCoordination.WEIGHT;
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
