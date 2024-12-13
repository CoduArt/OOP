import CardsBase.GameParams;
import CardsBase.PlayingCard;
import ProcessGame.Player;
import ProcessGame.Stages;
import Trackers.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class Viewport extends JPanel {


    public Viewport() {
        setBackground(GameParams.BG_COLOR);
        setPreferredSize(new Dimension(GameParams.VIEWPORT_WIDTH, GameParams.VIEWPORT_HEIGHT));
        Stages.startNewGame();
    }

    public void update() throws InterruptedException {
        if (Stages.hasAWinner != -1) {
            Thread.sleep(5000);
            Stages.players.get(Stages.hasAWinner).setWinner(false);
            Stages.startNewTurn();
        } else if (Stages.currentPlayer != 0) {
            Stages.buttons.setVisible(false);
            Player cur = Stages.players.get(Stages.currentPlayer);
            if (cur.isCanDo()) {
                Thread.sleep(1500);
                Stages.players.get(Stages.currentPlayer).doAction(Stages.MaxBet, Stages.openCards);
                repaint();
                Thread.sleep(1000);
            } else {
                Stages.nextPlayer();
            }
//            if (Stages.currentPlayer == Stages.lastBetPlayer) {
//                Thread.sleep(900);
//                if (Stages.nextStage()) {
//                    for (Player player: Stages.players) {
//                        player.setLastAction(null);
//                    }
////                    repaint();
//                    Thread.sleep(4000);
//                }
//            }
            Stages.isEndOfTurn();
        } else if (Stages.players.get(0).isCanDo()){
            Stages.buttons.setVisible(true);
        } else {
            Stages.nextPlayer();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PlayingCard card: Stages.cardList) {
            drawingClass.drawPlayingCard(g, card);
        }
        for (Player player: Stages.players) {
            drawingClass.drawMoneysTrackers(g, player);
            drawingClass.drawLastAction(g, player);
            drawingClass.drawWinner(g, player);
        }
        if (Stages.buttons.isVisible()) {
            for (Trackers.Button button : Stages.buttons.getButtons()) {
                drawingClass.drawButton(g, button);
            }
        }
        drawingClass.drawPot(g, Stages.pot);
    }

}

class drawingClass {

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

    public static void drawButton(Graphics g, Trackers.Button button) {
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
//        ImageWork.paintCardImage(g, card);
    }

    public static void drawMoneysTrackers(Graphics g, Player player) {
        ImageWork.paintChip(g, player.getChipTracker());
        ImageWork.paintBetTracker(g, player);
    }
}



public class Main extends JFrame {
    private Timer timer;
    public Viewport viewport = new Viewport();

    public Main() {
        super("Hello, graphics!");

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(viewport);
        pack();

        timer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewport.update();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                viewport.repaint();
            }
        });
        viewport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Stages.buttons.isVisible()) {
                    for (Button button: Stages.buttons.getButtons()) {
                        if(button.inButton(e.getX(), e.getY())) {
                            button.run();
                            viewport.repaint();
                            Stages.buttons.setVisible(false);
                            break;
                        }
                    }
                }
            }
        });

        setVisible(true);
        timer.start();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}