package Trackers;

import CardsBase.*;

public class ChipTracker {
    private final int CHIP_INDENT = 40;
    private int money = 500;
    private int x;
    private int y;
    private int chipWeight = 100;
    private int chipHeight = 100;

    public ChipTracker(PlayingCard card) {
        makeChipCoordinate(card);
    }
    private void makeChipCoordinate(PlayingCard card) {
        if (card.getTurn() == 5) {
            throw new RuntimeException("Error in Chip");
        }
        switch (card.getTurn()) {
            case GameParams.DOWN -> {
                x = card.getCoordX() + card.getWight() + CHIP_INDENT;
                y = card.getCoordY() + card.getHeight() / 4;
            }
            case GameParams.LEFT -> {
                x = card.getCoordX() + card.getWight() / 4;
                y = card.getCoordY() + card.getHeight() + CHIP_INDENT;
            }
            case GameParams.UP -> {
                x = card.getCoordX() - chipWeight - CHIP_INDENT;
                y = card.getCoordY() + card.getHeight() / 4;
            }
            case GameParams.RIGHT -> {
                x = card.getCoordX() + card.getHeight() / 4;
                y = card.getCoordY() - chipHeight - CHIP_INDENT;
            }
        }
    }

    public Integer getMoney() {
        return money;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getChipWeight() {
        return chipWeight;
    }

    public int getChipHeight() {
        return chipHeight;
    }

    public int ChangeMoney(int val) {
        if (money - val < 0) {
            int res = money;
            money = 0;
            return res;
        } else {
            money -= val;
            return val;
        }
    }
}
