package logic.ProcessGame.Combinations;

import java.util.ArrayList;

public interface CheckCombination {

    boolean isCombination(ArrayList<Integer>[] cardsBySuits, int[] cards);

    int getHighCard();
}
