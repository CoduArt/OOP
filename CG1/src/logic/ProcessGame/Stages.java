package logic.ProcessGame;

import logic.CardsBase.Deck;
import logic.CardsBase.Location;
import logic.CardsBase.PlayingCard;
import logic.Trackers.Buttons;
import logic.Trackers.ChipTracker;

import java.util.ArrayList;
import java.util.Random;

public class Stages {
    public int hasAWinner;
    public int stage = 0;
    public int MaxBet = 0;
    public int pot = 0;
    public int dealer;
    public int currentPlayer;
    public int lastBetPlayer;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<PlayingCard> openCards = new ArrayList<>();
    public Buttons buttons = new Buttons(this);
    public Deck playingDeck = new Deck();
    public ArrayList<PlayingCard> cardList = new ArrayList<>();

    public void update() throws InterruptedException {
        if (hasAWinner != -1) {
            Thread.sleep(5000);
            players.get(hasAWinner).setWinner(false);
            startNewTurn();
        } else if (currentPlayer != 0) {
            buttons.setVisible(false);
            Player cur = players.get(currentPlayer);
            if (cur.isCanDo()) {
                Thread.sleep(1500);
                players.get(currentPlayer).doAction(this);
                Thread.sleep(1000);
            }
//                Stages.nextPlayer();
            playerPlus();
            isEndOfTurn();
        } else if (players.get(0).isCanDo()){
            buttons.setVisible(true);
        } else {
//            Stages.nextPlayer();
            playerPlus();
        }
    }


    public boolean nextStage() {
        for (Player player: players) {
            player.setLastAction(null);
        }
        switch (stage) {
            case 0 -> flop();
            case 1 -> turn();
            case 2 -> river();
            case 3 -> {openCards();
            return true;}
        }
        for (Player player: players) {
            player.setCombination(new Combination(player, openCards));
        }
        stage++;
        return false;
    }

    private void winProcess(Player player) {
        player.setLastAction(null);
        player.getChipTracker().setWinMoney(pot);
        player.setWinner(true);
        hasAWinner = player.getNumber();
    }

    private void openCards() {
        buttons.setVisible(false);
        ArrayList<Combination> winnerList = new ArrayList<>();
        for (Player player: players) {
            if (player.isInGame()) {
                player.getHandCards()[0].setOpened(1);
                player.getHandCards()[1].setOpened(1);
                winnerList.add(new Combination(player, openCards));
            }
        }
        winnerList.sort((x, y) -> {
            if (y.getCombination() - x.getCombination() == 0) {
                return y.getHighestCard() - x.getHighestCard();
            }
            return y.getCombination() - x.getCombination();
        });
        winProcess(winnerList.get(0).getPlayer());
    }

    private void river() {
        PlayingCard card = cardList.get(12);
        card.setOpened(1);
        openCards.add(card);
    }

    private void turn() {
        PlayingCard card = cardList.get(11);
        card.setOpened(1);
        openCards.add(card);
    }

    public void flop() {
        for (int i = 8; i < 11; i++) {
            PlayingCard card = cardList.get(i);
            card.setOpened(1);
            openCards.add(card);
        }

    }

    public void startNewGame() {
        dealsCards();
        players.clear();
        for (int i = 1; i < 8; i += 2) {
            players.add(new Player(null, null, new ChipTracker(cardList.get(i)), (i - 1) / 2));
        }
        dealer = (new Random()).nextInt(4);
        startNewTurn();
    }

    public void startNewTurn() {
        dealsCards();
        for (Player player: players) {
            player.startNewTurn();
        }
        pot = 0;
        stage = 0;
        openCards.clear();
        MaxBet = 0;
        hasAWinner = -1;
        int countCards = 1;
        int countPlayers = 0;
        while (countPlayers < players.size()) {
            players.get(countPlayers).newLayout(cardList.get(countCards - 1), cardList.get(countCards));
            countCards += 2;
            countPlayers++;
        }
        nextDealer();
        currentPlayer = dealer;
        nextPlayer();
        MaxBet = 50;
        for (int i = 2; i > 0; i--) {
            MaxBet = players.get(currentPlayer).doBet(MaxBet, this);
            MaxBet *= i;
            nextPlayer();
            lastBetPlayer = currentPlayer;
        }
    }

    private void dealsCards() {
        playingDeck.updateDeck();
        cardList.clear();
        for (Location location: Location.values()) {
            cardList.add(new PlayingCard(location, playingDeck.getCard()));
        }
    }

    public boolean isEndOfTurn() {
        if (currentPlayer == lastBetPlayer) {
            if (nextStage()) {
                for (Player player: players) {
                    player.setLastAction(null);
                }
            }
            return true;
        }
        return false;
    }

    public void nextPlayer() {
        playerPlus();
//        isEndOfTurn();
        while (!players.get(currentPlayer).isCanDo()) {
            playerPlus();
//            isEndOfTurn();
        }
    }

    public void playerPlus() {
        currentPlayer++;
        if (currentPlayer == 4) {
            currentPlayer = 0;
        }
    }

    public void nextDealer() {
        dealerPlus();
        while (players.get(currentPlayer).isLose()) {
            dealerPlus();
        }
    }

    private void dealerPlus() {
        dealer++;
        if (dealer == 4) {
            dealer = 0;
        }
    }

    public void nextLastPlayer() {
        lastPlayerPlus();
        while (!players.get(lastBetPlayer).isCanDo()) {
            lastPlayerPlus();
        }
    }

    private void lastPlayerPlus() {
        lastBetPlayer++;
        if (lastBetPlayer == 4) {
            lastBetPlayer = 0;
        }
    }

    public boolean isRemainedLastPlayer() {
        Player lastPlayer = null;
        for (Player player: players) {
            if (player.isInGame()) {
                if (lastPlayer == null) {
                    lastPlayer = player;
                } else {
                    return false;
                }
            }
        }
        if (lastPlayer == null) {
            throw new RuntimeException("Неверный просчёт проверки на последнего игрока");
        }
        winProcess(lastPlayer);
        return true;
    }
}
