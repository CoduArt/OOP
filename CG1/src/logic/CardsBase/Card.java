package logic.CardsBase;

public class Card {
    private int denomination;
    private int suit;

    public Card(int denomination, int suit) {
        this.denomination = denomination;
        this.suit = suit;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getSuit() {
        return suit;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }
}
