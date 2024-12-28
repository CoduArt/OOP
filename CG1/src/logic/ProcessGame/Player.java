package logic.ProcessGame;

import logic.CardsBase.PlayingCard;
import logic.Trackers.ChipTracker;

import java.util.Random;

public class Player {
    private Random random = new Random();
    private int number;
    private PlayingCard[] handCards = new PlayingCard[2];
    private Combination combination;
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

    public void doAction(Stages stage) {
//        Actions.doCallOrCheck(this);

        int cost = 0;
        if (stage.stage == 0) {
            if (handCards[0].getSuit() == handCards[1].getSuit()) {
                cost += 14;
            }
            for (PlayingCard card: handCards) {
                if (card.getDenomination() == 1) {
                    cost += 12;
                } else {
                    cost += card.getDenomination();
                }
            }
            int action = cost + random.nextInt(50);
            if (action < 30) {
                Actions.doFold(this, stage);
            } else if (action > 65) {
                Actions.doRise(this, stage);
            } else {
                Actions.doCallOrCheck(this, stage);
            }
        } else {
            cost += combination.getCombination() * 20 + combination.getOuts() * 2;
            int action = cost + random.nextInt(60);
            if (stage.MaxBet != bet) {
                action -= 20;
            }
            if (action < 45) {
                Actions.doFold(this, stage);
            } else if (action > 110) {
                Actions.doRise(this, stage);
            } else {
                Actions.doCallOrCheck(this, stage);
            }
        }
    }

    public void newLayout(PlayingCard first, PlayingCard second) {
        if (!lose) {
            this.handCards[0] = first;
            this.handCards[1] = second;
            inGame = true;
            canDo = true;
        }
    }

    public Integer doBet(int Pot, Stages stage) {
        stage.pot += Pot - bet;
        bet += chipTracker.ChangeMoney(Pot - bet);
        if (chipTracker.getMoney() == 0) {
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

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
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
            bet = 0;
        } else {
            bet = 0;
            inGame = true;
            canDo = true;
        }
    }
}


