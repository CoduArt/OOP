package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public class FlushRoyal implements CheckCombination{
    @Override
    public boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards) {
        for (ArrayList<Integer> list: cardsBySuits) {
            if (list.size() < 5){
                continue;
            }
            int count = 10;
            for (Integer i: list) {
                if (i == 10) {
                    count++;
                }
            }
            if (count == 15) {
                highestCard = 13;
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
