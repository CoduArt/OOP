package logic.CardsBase;

public enum Location {
    PLAYER_ONE_CARD_1(1, GameParams.DOWN),
    PLAYER_ONE_CARD_2(2, GameParams.DOWN),
    PLAYER_TWO_CARD_1(3, GameParams.LEFT),
    PLAYER_TWO_CARD_2(4, GameParams.LEFT),
    PLAYER_THREE_CARD_1(5, GameParams.UP),
    PLAYER_THREE_CARD_2(6, GameParams.UP),
    PLAYER_FOUR_CARD_1(7, GameParams.RIGHT),
    PLAYER_FOUR_CARD_2(8, GameParams.RIGHT),
    TABLE_CARD_1(9, GameParams.CENTER),
    TABLE_CARD_2(10, GameParams.CENTER),
    TABLE_CARD_3(11, GameParams.CENTER),
    TABLE_CARD_4(12, GameParams.CENTER),
    TABLE_CARD_5(13, GameParams.CENTER);

    private int number;
    private int turn;

    Location(int number, int turn) {
        this.number = number;
        this.turn = turn;
    }

    public int getNumber() {
        return number;
    }

    public int getTurn() {
        return turn;
    }
}
