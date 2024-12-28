package graphics;

import logic.CardsBase.GameParams;
import logic.CardsBase.PlayingCard;
import logic.ProcessGame.Player;

import java.awt.*;

public class DrawingClass {
    public static void drawWinner(Graphics g, Player player) {
        if (player.isWinner()) {
            ImageWork.paintWinner(g, player);
        }
    }

    public static void drawLastAction(Graphics g, Player player) {
        if (player.getLastAction() != null) {
            ImageWork.paintLastAction(g, player);
        }
    }

    public static void drawPot(Graphics g, Integer pot) {
        g.setColor(Color.BLACK);
        g.drawString(pot.toString() + "$",
                GameParams.VIEWPORT_WIDTH / 2 - ImageWork.STATIC_FONT.getSize() / 4 - pot.toString().length() * ImageWork.STATIC_FONT.getSize() / 4,
                GameParams.VIEWPORT_HEIGHT / 2 + GameParams.VIEWPORT_HEIGHT / 7);
    }

    public static void drawButton(Graphics g, logic.Trackers.Button button) {
        g.setColor(new Color(0x413535));
        g.fillRoundRect(button.getX(), button.getY(), button.getWight(), button.getHeight(), 15, 15);
        g.setFont(ImageWork.STATIC_FONT);
        g.setColor(new Color(0x000000));
        g.drawRoundRect(button.getX(), button.getY(), button.getWight(), button.getHeight(), 15, 15);
        g.drawString(button.getNameOfAction(),
                button.getX() + button.getWight() / 2 - ImageWork.STATIC_FONT.getSize() / 4 - (button.getNameOfAction().length() - 1) * ImageWork.STATIC_FONT.getSize() / 4,
                button.getY() + button.getHeight() / 2 + ImageWork.STATIC_FONT.getSize() / 3);
    }

    public static void drawPlayingCard(Graphics g, PlayingCard card) {
        if (card.isOpened() == 1) {
            ImageWork.paintCardImage(g, card);
        } else if (card.isOpened() == -1) {
            ImageWork.paintCardShirt(g, card);
            g.setColor(Color.BLACK);
            g.drawRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
        }
//        graphics.ImageWork.paintCardImage(g, card);
    }

    public static void drawMoneysTrackers(Graphics g, Player player) {
        ImageWork.paintChip(g, player.getChipTracker());
        ImageWork.paintBetTracker(g, player);
    }
}
