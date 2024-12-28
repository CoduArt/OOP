package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public enum NamesOfCombinations {
    FLASH_ROYAL(10, new FlushRoyal()),
    STRAIGHT_FLASH(9, new StraightFlush()),
    FOUR_OF_A_KIND(8, new FourOfAKind()),
    FULL_HOUSE(7, new FullHouse()),
    FLUSH(6, new Flush()),
    STRAIGHT(5, new Straight()),
    THREE_OF_A_KIND(4, new ThreeOfAKind()),
    TWO_PAIR(3, new TwoPair()),
    PAIR(2, new Pair()),
    HIGH_CARD(1, new HighCard());

    NamesOfCombinations(int number, CheckCombination combination) {
        this.number = number;
        this.combination = combination;
    }

    public int gerNumber() {
        return number;
    }

    public boolean test(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        return combination.isCombination(cardsBySuits, cards);
    }

    public int getHighCard() {
        return combination.getHighCard();
    }

    private int number;
    CheckCombination combination;
}
