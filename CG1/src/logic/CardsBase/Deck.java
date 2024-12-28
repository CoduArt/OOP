package logic.CardsBase;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deckList = new ArrayList<>();

    public Card getCard() {
        return deckList.remove(1);
    }

    public void updateDeck() {
        deckList.clear();
        for (int suit = 1; suit <= 4; suit++) {
            for (int den = 1; den <= 13; den++) {
                deckList.add(new Card(den, suit));
            }
        }
        Collections.shuffle(deckList);
    }
}
