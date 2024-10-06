public class PlayingCard {
    private int coordX;
    private int coordY;
    private int wight = 125;
    private int height = 175;
    private int turn;
    private int denomination;


    public PlayingCard(Location location, int turn) {
        this.turn = turn;
    }

    private void findLocation(Location location) {
        switch (location) {
            case PLAYER_ONE_CARD_1:
                wight *= (int) 1.2;
                height *= (int) 1.2;
                coordX = (int) (Viewport.VIEWPORT_WIDTH / 2 - Viewport.VIEWPORT_WIDTH * 0.10) + getWight() / 2;
                coordY = (int) (Viewport.VIEWPORT_HEIGHT * 0.7);
            case PLAYER_ONE_CARD_2:
                wight *= (int) 1.2;
                height *= (int) 1.2;
                coordX = (int) (Viewport.VIEWPORT_WIDTH / 2 + Viewport.VIEWPORT_WIDTH * 0.10) + getWight() / 2;
                coordY = (int) (Viewport.VIEWPORT_HEIGHT * 0.7);
        }
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getTurn() {
        return turn;
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
