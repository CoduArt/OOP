package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class StraightFlush implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        for (ArrayList<Integer> list: cardsBySuits) {
            if (list.size() < 5){
                continue;
            }
            int count = 0;
            int lastCard = 0;
            for (Integer i: list) {
                if (i - lastCard != 1) {
                    count = 0;
                }
                lastCard = i;
                count++;
            }
            if (count >= 5) {
                highestCard = list.get(list.size() - 1);
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
