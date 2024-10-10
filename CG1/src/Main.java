import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


class Viewport extends JPanel {
    public static final int VIEWPORT_WIDTH = 1280;
    public static final int VIEWPORT_HEIGHT = 960;
    private Deck playingDeck = new Deck();
    private ArrayList<PlayingCard> cardList = new ArrayList<>();



    public Viewport() {
        setBackground(new Color(14, 105, 33));
        setPreferredSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        dealsCards();
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
            drawPlayingCard(g, card);
        }

    }


    private void drawPlayingCard(Graphics g, PlayingCard card) {
        if (card.isOpened()) {
            g.setColor(new Color(0xFFBBA9AF, true));
            g.fillRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
            paintCardDenomination(g, card);
        } else {
            g.setColor(new Color(0xFF9B8690, true));
            g.fillRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
            g.setColor(new Color(0xFF880E0E, true));
            g.fillRoundRect((int) (card.getCoordX() + card.getWight() * 0.05),
                    (int) (card.getCoordY() + card.getHeight() * 0.05),
                    (int) (card.getWight() * 0.9),
                    (int) (card.getHeight() * 0.9), 15, 15);
            g.setColor(Color.BLACK);
            g.drawRoundRect((int) (card.getCoordX() + card.getWight() * 0.05),
                    (int) (card.getCoordY() + card.getHeight() * 0.05),
                    (int) (card.getWight() * 0.9),
                    (int) (card.getHeight() * 0.9), 15, 15);
        }

        g.setColor(Color.BLACK);
        g.drawRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
    }

    private void paintCardDenomination(Graphics g, PlayingCard card) {
        Image img = new ImageIcon("src/CardsPack/CardsWithGreen2.jpg").getImage();
//        g.drawImage(img, card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), null);
        g.drawImage(img, card.getCoordX(), card.getCoordY(), card.getCoordX() + card.getWight(),
                card.getCoordY() + card.getHeight(), 60, 61 , 197, 274, null);
    }

}



public class Main extends JFrame {
    private Timer timer;
    private Viewport viewport = new Viewport();

    public Main() {
        super("Hello, graphics!");

        // Запрещаем масштабирование
        setResizable(false);
        // Останавливаем процесс приложения при закрытии окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Добавляем рисуемую область на окно
        getContentPane().add(viewport);
        // Перемасштабируем все элементы, чтобы их размер
        // соответствовал заданному
        pack();

        // Создаём таймер, который будет "тикать" раз в 33 миллисекунды.
        // В каждом тике вызываем обновление сцены (для анимации)
        // и перерисовку сцены.
        timer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewport.update(1.0f / 33.0f);
                viewport.repaint();
            }
        });

        // Показываем окно на экране
        setVisible(true);

        // Запускаем таймер
        timer.start();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}