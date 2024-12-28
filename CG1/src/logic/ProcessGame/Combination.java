package logic.ProcessGame;

import logic.CardsBase.Card;
import logic.CardsBase.PlayingCard;
import logic.ProcessGame.Combinations.NamesOfCombinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Combination {
    private int combination;
    private int highestCard;
    private int outs;
    private Player player;

    private void findOuts(ArrayList<Integer>[] cardsBySuits, int[] duplicateCards, PlayingCard card1, PlayingCard card2) {
        for (int i = 0; i < cardsBySuits.length; i++) {
            if (cardsBySuits[i].size() == 4) {
                outs += 7;
                if (card1.getSuit() - 1 == i) {
                    outs++;
                }
                if (card2.getSuit() - 1 == i) {
                    outs++;
                }
                break;
            }
        }
        int count = 0;
        for (int i = duplicateCards.length - 1; i >= 0; i--) {
            if (duplicateCards[i] != 0) {
                int den1 = card1.getDenomination() == 1 ? 12 : card1.getDenomination() - 2;
                int den2 = card2.getDenomination() == 1 ? 12 : card2.getDenomination() - 2;
                if (den1 == i || den2 == i) {
                    count++;
                    outs+= 4 - duplicateCards[i];
                    if (count == 2) {
                        break;
                    }
                } else {
                    outs--;
                }
            }
        }
        for (int i = 4; i < duplicateCards.length; i++) {
            int[] viewStraight = new int[]{
                duplicateCards[i - 4] == 0 ? 0:1,
                duplicateCards[i - 3] == 0 ? 0:1,
                duplicateCards[i - 2] == 0 ? 0:1,
                duplicateCards[i - 1] == 0 ? 0:1,
                duplicateCards[i] == 0 ? 0:1
            };
            if (Arrays.stream(viewStraight).sum() == 4) {
                outs += 4;
            }
        }
    }

    public Combination(final Player player, final ArrayList<PlayingCard> openCards) {
        this.player = player;
        findCombination(player.getHandCards()[0], player.getHandCards()[1], openCards);
    }

    private void findCombination(final PlayingCard card1, final PlayingCard card2, final ArrayList<PlayingCard> openCards) {
        ArrayList<PlayingCard> allCards = new ArrayList<>(openCards);
        allCards.add(card1);
        allCards.add(card2);
        ArrayList<Integer>[] cardsBySuits = new ArrayList[] {new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};
        int[] duplicateCards = new int[13];
        allCards.sort(Comparator.comparingInt(Card::getDenomination));
        for (PlayingCard card: allCards) {
            if (card.getDenomination() != 1) {
                cardsBySuits[card.getSuit() - 1].add(card.getDenomination());
                duplicateCards[card.getDenomination() - 2]++;
            } else {
                cardsBySuits[card.getSuit() - 1].add(14);
                duplicateCards[12]++;
            }
        }

        findOuts(cardsBySuits, duplicateCards, card1, card2);

        for (NamesOfCombinations combination: NamesOfCombinations.values()) {
            if (combination.test(cardsBySuits, duplicateCards)) {
                this.combination = combination.gerNumber();
                this.highestCard = combination.getHighCard();
                break;
            }
        }
    }

    public int getCombination() {
        return combination;
    }

    public int getHighestCard() {
        return highestCard;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOuts() {
        return outs;
    }
}
