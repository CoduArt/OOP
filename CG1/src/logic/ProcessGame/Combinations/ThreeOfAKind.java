package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class ThreeOfAKind implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 3) {
                highestCard = i + 2;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getHighCard() {
        return highestCard;
    }

    int highestCard;
}
