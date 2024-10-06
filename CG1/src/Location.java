public enum Location {
    PLAYER_ONE_CARD_1(1),
    PLAYER_ONE_CARD_2(2),
    PLAYER_TWO_CARD_1(3),
    PLAYER_TWO_CARD_2(4),
    PLAYER_THREE_CARD_1(5),
    PLAYER_THREE_CARD_2(6),
    PLAYER_FOUR_CARD_1(7),
    PLAYER_FOUR_CARD_2(8),
    TABLE_CARD_1(9),
    TABLE_CARD_2(10),
    TABLE_CARD_3(11),
    TABLE_CARD_4(12),
    TABLE_CARD_5(13);

    public int number;

    Location(int number) {
        this.number = number;
    }
}
