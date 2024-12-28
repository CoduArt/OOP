package logic;

import graphics.DrawingClass;
import logic.CardsBase.GameParams;
import logic.CardsBase.PlayingCard;
import logic.ProcessGame.Player;
import logic.ProcessGame.Stages;
import logic.Trackers.Button;
import graphics.ImageWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class Viewport extends JPanel {
    Stages stage;


    public Viewport(Stages stage) {
        this.stage = stage;
        setBackground(GameParams.BG_COLOR);
        setPreferredSize(new Dimension(GameParams.VIEWPORT_WIDTH, GameParams.VIEWPORT_HEIGHT));
        stage.startNewGame();
    }

    public void update() throws InterruptedException {
        stage.update();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PlayingCard card: stage.cardList) {
            DrawingClass.drawPlayingCard(g, card);
        }
        for (Player player: stage.players) {
            DrawingClass.drawMoneysTrackers(g, player);
            DrawingClass.drawLastAction(g, player);
            DrawingClass.drawWinner(g, player);
        }
        if (stage.buttons.isVisible()) {
            for (logic.Trackers.Button button : stage.buttons.getButtons()) {
                DrawingClass.drawButton(g, button);
            }
        }
        DrawingClass.drawPot(g, stage.pot);
    }

}



public class Main extends JFrame {
    private Timer timer;
    public Stages stage = new Stages();
    public Viewport viewport = new Viewport(stage);

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
                if (stage.buttons.isVisible()) {
                    for (Button button: stage.buttons.getButtons()) {
                        if(button.inButton(e.getX(), e.getY())) {
                            button.run(stage);
                            viewport.repaint();
                            stage.buttons.setVisible(false);
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