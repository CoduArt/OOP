import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


class Viewport extends JPanel {
    public static final int VIEWPORT_WIDTH = 980;
    public static final int VIEWPORT_HEIGHT = 760;
    private final PlayingCard testCard = new PlayingCard(Location.PLAYER_ONE_CARD_1, 1);
    private final PlayingCard testCard2 = new PlayingCard( Location.PLAYER_ONE_CARD_2,, 2);


    public Viewport() {
        setBackground(new Color(24, 113, 45));
        setPreferredSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

    }

    public void update(float delta) {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayingCard(g, testCard);
        drawPlayingCard(g, testCard2);

    }


    private void drawPlayingCard(Graphics g, PlayingCard card) {
        g.setColor(Color.BLACK);
        g.drawRoundRect(card.getCoordX(), card.getCoordY(), card.getWight(), card.getHeight(), 15, 15);
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