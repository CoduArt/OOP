package logic.CardsBase;

public class PlayingCard extends Card {
    private final int RANGE_TABLE_CARDS = (int) ((0.75 * GameParams.VIEWPORT_WIDTH - 0.25 * GameParams.VIEWPORT_WIDTH) / 5);
    private int coordX;
    private int coordY;
    private int wight = 150;
    private int height = 195;
    private int turn;
    private int opened = -1;


    public PlayingCard(Location location, Card card) {
        super(card.getDenomination(), card.getSuit());
        this.turn = location.getTurn();
        findLocation(location);
    }

    private void findLocation(Location location) {
        switch (location) {
            case PLAYER_ONE_CARD_1:
                opened = 1;
                definitionLocationVertical(-1, 0.88);
                break;
            case PLAYER_ONE_CARD_2:
                opened = 1;
                definitionLocationVertical(1, 0.88);
                break;
            case PLAYER_TWO_CARD_1:
                definitionLocationHorizontal(-1, 0.07);
                break;
            case PLAYER_TWO_CARD_2:
                definitionLocationHorizontal(1, 0.07);
                break;
            case PLAYER_THREE_CARD_1:
                definitionLocationVertical(1, 0.12);
                break;
            case PLAYER_THREE_CARD_2:
                definitionLocationVertical(-1, 0.12);
                break;
            case PLAYER_FOUR_CARD_1:
                definitionLocationHorizontal(1, 0.9);
                break;
            case PLAYER_FOUR_CARD_2:
                definitionLocationHorizontal(-1, 0.9);
                break;
            case TABLE_CARD_1:
                definitionLocationCentral(1);
                break;
            case TABLE_CARD_2:
                definitionLocationCentral(2);
                break;
            case TABLE_CARD_3:
                definitionLocationCentral(3);
                break;
            case TABLE_CARD_4:
                definitionLocationCentral(4);
                break;
            case TABLE_CARD_5:
                definitionLocationCentral(5);
                break;
        }
    }

    private void definitionLocationVertical(int alpha, double beta) {
        coordX = (int) (GameParams.VIEWPORT_WIDTH / 2 + alpha * (GameParams.VIEWPORT_WIDTH * 0.075) - wight / 2);
        coordY = (int) (GameParams.VIEWPORT_HEIGHT *  beta - height / 2);
    }

    private void definitionLocationHorizontal(int alpha, double beta) {
        coordX = (int) (GameParams.VIEWPORT_WIDTH * beta - wight / 2);
        coordY = (int) (GameParams.VIEWPORT_HEIGHT / 2 + alpha * (GameParams.VIEWPORT_HEIGHT * 0.1) - height / 2);
    }

    private void definitionLocationCentral(int number) {
        coordX = (int) (GameParams.VIEWPORT_WIDTH * 0.26 + (number - 1) * RANGE_TABLE_CARDS);
        coordY = GameParams.VIEWPORT_HEIGHT / 2 - height / 2;
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
        if (turn % 2 == 1) return wight;
        return height;
    }

    public int getHeight() {
        if (turn % 2 == 1) return height;
        return wight;
    }

    public int isOpened() {
        return opened;
    }

    public void setOpened(int open) {
        opened = open;
    }
}

