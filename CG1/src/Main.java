import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Требования к лабораторной работе
//  - Не использовать магические числа
//        drawRect(123, 123, 12, 15); - плохо
//        drawRect(HOUSE_POS_X, HOUSE_POS_Y, HOUSE_WIDTH, HOUSE_HEIGHT); - хорошо
//  - Выносить рисовку отдельных объектов в отдельные методы
//      - Рисовать всё внутри paintComponent - плохо.
//      - Желательно создавать отдельные методы для рисования.
//          public void drawHouse(Graphics g,
//              int posX, int posY, int width, int height, Color color);
//   - Для получения максимального балла по задаче нужна анимация.


class Viewport extends JPanel {
    private final int VIEWPORT_WIDTH = 980;
    private final int VIEWPORT_HEIGHT = 760;


    public Viewport() {
        setBackground(new Color(24, 113, 45));
        setPreferredSize(new Dimension(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

    }

    public void update(float delta) {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayingCard(g);

    }


    private void drawPlayingCard(Graphics g) {
        g.setColor(new Color(0x9E0E0E));
        g.fillRoundRect((int) (VIEWPORT_WIDTH / 6.5), VIEWPORT_HEIGHT/2, 150, 225, 20, 20);
        g.setColor(Color.BLACK);
        g.drawRoundRect(VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT/2, 150, 225, 20, 20);
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