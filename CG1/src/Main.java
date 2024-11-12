import CardsBase.Deck;
import CardsBase.GameParams;
import CardsBase.Location;
import CardsBase.PlayingCard;
import Trackers.Buttons;
import Trackers.ChipTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


class Viewport extends JPanel {
    private Buttons buttons = new Buttons();
    private Deck playingDeck = new Deck();
    private ArrayList<PlayingCard> cardList = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayer;



    public Viewport() {
        setBackground(GameParams.BG_COLOR);
        setPreferredSize(new Dimension(GameParams.VIEWPORT_WIDTH, GameParams.VIEWPORT_HEIGHT));
        startNewGame();
    }

    public void startNewGame() {
        dealsCards();
        players.clear();
        for (int i = 1; i < 8; i += 2) {
            players.add(new Player(null, null, new ChipTracker(cardList.get(i))));
        }
        currentPlayer = 4;
        startNewTurn();
    }

    public void startNewTurn() {
        dealsCards();
        int countCards = 1;
        int countPlayers = 0;
        while (countPlayers < players.size()) {
            players.get(countPlayers).newLayout(cardList.get(countCards - 1), cardList.get(countCards));
            countCards += 2;
            countPlayers++;
        }
        if (currentPlayer > 3) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
    }

     private void dealsCards() {
        playingDeck.updateDeck();
        cardList.clear();
         for (Location location: Location.values()) {
             cardList.add(new PlayingCard(location, playingDeck.getCard()));
         }
     }

    public void update(float delta) {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PlayingCard card: cardList) {
            drawingClass.drawPlayingCard(g, card);
        }
        for (Player player: players) {
            drawingClass.drawMoneysTrackers(g, player);
        }
        for (Trackers.Button button: buttons.getButtons()) {
            drawingClass.drawButton(g, button);
        }

    }

}

class drawingClass {
    private static final Font TEXT_FONT = new Font(null, Font.PLAIN, 20);

    public static void drawButton(Graphics g, Trackers.Button button) {
        g.setColor(new Color(0x413535));
        g.fillRoundRect(button.getX(), button.getY(), button.getWight(), button.getHeight(), 15, 15);
        g.setFont(TEXT_FONT);
        g.setColor(new Color(0x000000));
        g.drawRoundRect(button.getX(), button.getY(), button.getWight(), button.getHeight(), 15, 15);
        g.drawString(button.getAction(),
                button.getX() + button.getWight() / 2 - TEXT_FONT.getSize() / 4 - (button.getAction().length() - 1) * TEXT_FONT.getSize() / 4,
                button.getY() + button.getHeight() / 2 + TEXT_FONT.getSize() / 3);
    }

    public static void drawPlayingCard(Graphics g, PlayingCard card) {
        if (card.isOpened() == 1) {
            ImageWork.paintCardImage(g, card);
        } else if (card.isOpened() == -1) {
            ImageWork.paintCardShirt(g, card);
            g.setColor(Color.BLACK);
            g.drawRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
        }
    }

    public static void drawMoneysTrackers(Graphics g, Player player) {
        ImageWork.paintChip(g, player.getChipTracker());
        ImageWork.paintBetTracker(g, player);
    }
}



public class Main extends JFrame {
    private Timer timer;
    private Viewport viewport = new Viewport();

    public Main() {
        super("Hello, graphics!");

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(viewport);
        pack();

        timer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewport.update(1.0f / 33.0f);
//                viewport.repaint();
            }
        });
        viewport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(e);

            }
        });

        setVisible(true);
//        timer.start();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}