package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class TwoPair implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        int count = 0;
        int maxDen = 0;
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 2) {
                count++;
                maxDen = Math.max(maxDen, i + 2);
            }
        }
        if (count >= 2) {
            highestCard = maxDen;
            return true;
        }
        return false;
    }

    @Override
    public int getHighCard() {
        return highestCard;
    }

    int highestCard;
}
