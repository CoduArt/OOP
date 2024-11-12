package Trackers;

public class Button {
    private int x;
    private int y;
    private int wight;
    private int height;
    String action;

    public Button(int x, int y, int wight, int height, String action) {
        this.x = x;
        this.y = y;
        this.wight = wight;
        this.height = height;
        this.action = action;
    }

    public boolean inButton(int x, int y) {
        return x > this.x && x < this.x + wight &&
                y > this.y + height && y < this.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWight() {
        return wight;
    }

    public int getHeight() {
        return height;
    }

    public String getAction() {
        return action;
    }
}
