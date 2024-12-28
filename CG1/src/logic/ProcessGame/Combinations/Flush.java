package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class Flush implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        boolean flag = false;
        for (ArrayList<Integer> list: cardsBySuits) {
            if (list.size() >= 5) {
                flag = true;
                highestCard = list.get(list.size() - 1);
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
