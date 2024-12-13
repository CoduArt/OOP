package ProcessGame;

import CardsBase.PlayingCard;
import Trackers.ChipTracker;

import java.util.ArrayList;

public class Player {
    private int number;
    private PlayingCard[] handCards = new PlayingCard[2];
    private ChipTracker chipTracker;
    private Integer bet = 0;
    private String lastAction;
    private boolean winner = false;
    private boolean lose = false;
    private boolean inGame = true;
    private boolean canDo = true;

    public Player(PlayingCard first, PlayingCard second, ChipTracker chipTracker, int number) {
        this.handCards[0] = first;
        this.handCards[1] = second;
        this.chipTracker = chipTracker;
        this.number = number;
    }

    public void doAction(int maxBet, ArrayList<PlayingCard> visibleCenterCards) {
        Actions.doCall(this);
    }

    public void newLayout(PlayingCard first, PlayingCard second) {
        if (!lose) {
            this.handCards[0] = first;
            this.handCards[1] = second;
            inGame = true;
            canDo = true;
        }
    }

    public Integer doBet(int Pot) {
        Stages.pot += Pot - bet;
        bet += chipTracker.ChangeMoney(Pot - bet);
        if (bet < Stages.MaxBet) {
            canDo = false;
        }
        return bet;
    }

    public boolean isCanDo() {
        return canDo;
    }

    public String getLastAction() {
        return lastAction;
    }

    public PlayingCard[] getHandCards() {
        return handCards;
    }

    public ChipTracker getChipTracker() {
        return chipTracker;
    }

    public Integer getBet() {
        return bet;
    }

    public int getNumber() {
        return number;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isInGame() {
        return inGame;
    }

    public boolean isLose() {
        return lose;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void doFold() {
        inGame = false;
        canDo = false;
        for (PlayingCard card: handCards) {
            card.setOpened(0);
        }
    }

    public void loseInGame() {
        lose = true;
        canDo = false;
        inGame = false;
        for (PlayingCard card: handCards) {
            card.setOpened(0);
        }
    }

    public void startNewTurn() {
        if (chipTracker.getMoney() == 0) {
            loseInGame();
        } else {
            bet = 0;
            inGame = true;
            canDo = true;
        }
    }
}


