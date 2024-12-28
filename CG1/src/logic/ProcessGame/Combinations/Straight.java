package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class Straight implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] > 0) {
                count++;
            } else {
                count = 0;
            }
            if (count >= 5) {
                highestCard = i + 2;
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int getHighCard() {
        return highestCard;
    }

    int highestCard;
}
