package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class FullHouse implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        int countWithThree = 0;
        int countWithTwo = 0;
        int maxDen = 0;
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 3) {
                countWithThree++;
                maxDen = Math.max(maxDen, i + 2);
            }else if (cards[i] == 2) {
                countWithTwo++;
            }
        }
        if ((countWithThree > 0 && countWithTwo > 0) || (countWithThree > 1)) {
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
