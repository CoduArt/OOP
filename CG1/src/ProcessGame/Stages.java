package ProcessGame;

import CardsBase.Deck;
import CardsBase.Location;
import CardsBase.PlayingCard;
import Trackers.Buttons;
import Trackers.ChipTracker;

import java.util.ArrayList;
import java.util.Random;

public class Stages {
    public static int hasAWinner;
    public static int stage = 0;
    public static int MaxBet = 0;
    public static int pot = 0;
    public static int dealer;
    public static int currentPlayer;
    public static int lastBetPlayer;
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<PlayingCard> openCards = new ArrayList<>();
    public static Buttons buttons = new Buttons();
    public static Deck playingDeck = new Deck();
    public static ArrayList<PlayingCard> cardList = new ArrayList<>();


    public static boolean nextStage() {
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
        stage++;
        return false;
    }

    private static void winProcess(Player player) {
        player.getChipTracker().setWinMoney(pot);
        player.setWinner(true);
        hasAWinner = player.getNumber();
    }

    private static void openCards() {
        buttons.setVisible(false);
        ArrayList<Combination> winnerList = new ArrayList<>();
        for (Player player: players) {
            if (player.isInGame()) {
                player.getHandCards()[0].setOpened(1);
                player.getHandCards()[1].setOpened(1);
                winnerList.add(new Combination(player, openCards));
            }
        }
        winnerList.sort((x, y) -> y.getCombination() - x.getCombination());
        winProcess(winnerList.get(0).getPlayer());
    }

    private static void river() {
        PlayingCard card = cardList.get(12);
        card.setOpened(1);
        openCards.add(card);
    }

    private static void turn() {
        PlayingCard card = cardList.get(11);
        card.setOpened(1);
        openCards.add(card);
    }

    public static void flop() {
        for (int i = 8; i < 11; i++) {
            PlayingCard card = cardList.get(i);
            card.setOpened(1);
            openCards.add(card);
        }

    }

    public static void startNewGame() {
        dealsCards();
        players.clear();
        for (int i = 1; i < 8; i += 2) {
            players.add(new Player(null, null, new ChipTracker(cardList.get(i)), (i - 1) / 2));
        }
        dealer = (new Random()).nextInt(4);
        startNewTurn();
    }

    public static void startNewTurn() {
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
            MaxBet = players.get(currentPlayer).doBet(MaxBet);
            MaxBet *= i;
            nextPlayer();
            lastBetPlayer = currentPlayer;
        }
    }

    private static void dealsCards() {
        playingDeck.updateDeck();
        cardList.clear();
        for (Location location: Location.values()) {
            cardList.add(new PlayingCard(location, playingDeck.getCard()));
        }
    }

    public static boolean isEndOfTurn() {
        if (currentPlayer == lastBetPlayer) {
            if (Stages.nextStage()) {
                for (Player player: players) {
                    player.setLastAction(null);
                }
            }
            return true;
        }
        return false;
    }

    public static void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == 4) {
            currentPlayer = 0;
        }
        while (players.get(currentPlayer).isLose()) {
            nextPlayer();
        }
    }

    public static void nextDealer() {
        dealer++;
        if (dealer == 4) {
            dealer = 0;
        }
        while (players.get(currentPlayer).isLose()) {
            nextDealer();
        }
    }
}
