package ProcessGame;

public class Actions {
    public static String doFold(Player player) {
        player.doFold();
        player.setLastAction("Fold");
        Stages.nextPlayer();
        return "Fold";
    }

    public static String doCall(Player player) {
        player.doBet(Stages.MaxBet);
        player.setLastAction("Call");
        Stages.nextPlayer();
        return "Call";
    }

    public static String doRise(Player player) {
        Stages.lastBetPlayer = player.getNumber();
        Stages.MaxBet = player.doBet((int) (Stages.MaxBet*1.5));
        player.setLastAction("Rise");
        Stages.nextPlayer();
        return "Rise";
    }
}
