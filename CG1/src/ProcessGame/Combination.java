package ProcessGame;

import CardsBase.Card;
import CardsBase.PlayingCard;

import java.util.ArrayList;
import java.util.Comparator;

public class Combination {
    private int combination;
    private int highestCard;
    private Player player;

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

        ///////////////

        if (isFlushRoyal(cardsBySuits)) {
            return;
        } else if (isStraightFlush(cardsBySuits)) {
            return;
        } else if (isFourOfAKind(duplicateCards)) {
            return;
        } else if (isFullHouse(duplicateCards)) {
            return;
        } else if (isFlush(cardsBySuits)) {
            return;
        } else if (isStraight(duplicateCards)) {
            return;
        } else if (isThreeOfAKind(duplicateCards)) {
            return;
        } else if (isTwoPair(duplicateCards)) {
            return;
        } else if (isPair(duplicateCards)) {
            return;
        } else if (isHighCard(duplicateCards)) {
            return;
        } else {
            throw new RuntimeException("Ошибка в определении комбинации");
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

    private boolean isFlushRoyal(ArrayList<Integer>[] cardsBySuits) {
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
                combination = NamesOfCombinations.FLASH_ROYAL;
                highestCard = 13;
                return true;
            }
        }
        return false;
    }

    private boolean isStraightFlush(ArrayList<Integer>[] cardsBySuits) {
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
                combination = NamesOfCombinations.STRAIGHT_FLASH;
                highestCard = list.get(list.size() - 1);
                return true;
            }
        }
        return false;
    }

    private boolean isFourOfAKind(int[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 4) {
                combination = NamesOfCombinations.FOUR_OF_A_KIND;
                highestCard = i + 2;
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse(int[] cards) {
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
            combination = NamesOfCombinations.FULL_HOUSE;
            highestCard = maxDen;
            return true;
        }
        return false;
    }

    private boolean isFlush(ArrayList<Integer>[] cardsBySuits) {
        boolean flag = false;
        for (ArrayList<Integer> list: cardsBySuits) {
            if (list.size() >= 5) {
                flag = true;
                combination = NamesOfCombinations.FLUSH;
                highestCard = list.get(list.size() - 1);
            }
        }
        return flag;
    }

    private boolean isStraight(int[] cards) {
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] > 0) {
                count++;
            } else {
                count = 0;
            }
            if (count >= 5) {
                combination = NamesOfCombinations.STRAIGHT;
                highestCard = i + 2;
                flag = true;
            }
        }
        return flag;
    }

    private boolean isThreeOfAKind(int[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 3) {
                combination = NamesOfCombinations.THREE_OF_A_KIND;
                highestCard = i + 2;
                return true;
            }
        }
        return false;
    }

    private boolean isTwoPair(int[] cards) {
        int count = 0;
        int maxDen = 0;
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 2) {
                count++;
                maxDen = Math.max(maxDen, i + 2);
            }
        }
        if (count >= 2) {
            combination = NamesOfCombinations.TWO_PAIR;
            highestCard = maxDen;
            return true;
        }
        return false;
    }

    private boolean isPair(int[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 2) {
                combination = NamesOfCombinations.PAIR;
                highestCard = i + 2;
                return true;
            }
        }
        return false;
    }

    private boolean isHighCard(int[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] == 1) {
                combination = NamesOfCombinations.HIGH_CARD;
                highestCard = i + 2;
                return true;
            }
        }
        return false;
    }
}

class NamesOfCombinations {
    public static int FLASH_ROYAL = 10;
    public static int STRAIGHT_FLASH = 9;
    public static int FOUR_OF_A_KIND = 8;
    public static int FULL_HOUSE = 7;
    public static int FLUSH = 6;
    public static int STRAIGHT = 5;
    public static int THREE_OF_A_KIND = 4;
    public static int TWO_PAIR = 3;
    public static int PAIR = 2;
    public static int HIGH_CARD = 1;
}
