public enum Location {
    PLAYER_ONE_CARD_1(1, 1),
    PLAYER_ONE_CARD_2(2, 1),
    PLAYER_TWO_CARD_1(3, 2),
    PLAYER_TWO_CARD_2(4, 2),
    PLAYER_THREE_CARD_1(5, 3),
    PLAYER_THREE_CARD_2(6, 3),
    PLAYER_FOUR_CARD_1(7, 4),
    PLAYER_FOUR_CARD_2(8, 4),
    TABLE_CARD_1(9, 5),
    TABLE_CARD_2(10, 5),
    TABLE_CARD_3(11, 5),
    TABLE_CARD_4(12, 5),
    TABLE_CARD_5(13, 5);

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
