import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private final int SPADES = 1;
    private final int CLUBS = 2;
    private final int DIAMONDS = 3;
    private final int HEARTS = 4;
    private final Random r = new Random();
    private ArrayList<Card> deckList = new ArrayList<>();

    public Card getCard() {
        return deckList.get(r.nextInt(deckList.size()));
    }

    public void updateDeck() {
        deckList.clear();
        for (int suit = 1; suit <= 4; suit++) {
            for (int den = 1; den <= 13; den++) {
                deckList.add(new Card(den, suit));
            }
        }
    }
}
