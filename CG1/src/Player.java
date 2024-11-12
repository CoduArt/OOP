import CardsBase.PlayingCard;
import Trackers.ChipTracker;

import java.util.ArrayList;

public class Player {
    private PlayingCard[] handCards = new PlayingCard[2];
    private ChipTracker chipTracker;
    private Integer bet = 0;
    private boolean lose = false;
    private boolean inGame = true;
    private boolean canDo = true;

    public Player(PlayingCard first, PlayingCard second, ChipTracker chipTracker) {
        this.handCards[0] = first;
        this.handCards[1] = second;
        this.chipTracker = chipTracker;
    }

    public void doAction(int maxBet, ArrayList<PlayingCard> visibleCenterCards) {
        Actions.doCall(this, maxBet);
    }

    public void newLayout(PlayingCard first, PlayingCard second) {
        if (!lose) {
            this.handCards[0] = first;
            this.handCards[1] = second;
            inGame = true;
            canDo = true;
        }
    }

    public Integer grabMoney(int bet) {
        this.bet = chipTracker.ChangeMoney(bet);
        if (this.bet < bet) {
            canDo = false;
        }
        return this.bet;
    }

    public boolean isCanDo() {
        return canDo;
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

    public boolean isInGame() {
        return inGame;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public void doFold() {
        inGame = false;
        canDo = false;
    }

    public void loseInGame() {
        lose = true;
        canDo = false;
        inGame = false;
    }
}


