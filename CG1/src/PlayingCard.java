public class PlayingCard {
    private int coordX;
    private int coordY;
    private int wight;
    private int height;
    private int turn;
    private double size;
    private int denomination;


    public PlayingCard(int coordX, int coordY, int size) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.size = size;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public double getSize() {
        return size;
    }

    public int getWight() {
        if (turn == 1 || turn == 3) return wight;
        return height;
    }

    public int getHeight() {
        if (turn == 1 || turn == 3) return height;
        return wight;
    }

    public int getDenomination() {
        return denomination;
    }
}
