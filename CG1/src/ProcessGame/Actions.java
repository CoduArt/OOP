package ProcessGame;

public class Actions {
    public static String doFold(Player player) {
        player.doFold();
        player.setLastAction("Fold");
//        Stages.nextPlayer();
        if (Stages.isRemainedLastPlayer()) {
            return null;
        }
//        if (player.getNumber() == Stages.lastBetPlayer) {
//            Stages.nextLastPlayer();
//        }
        return "Fold";
    }

    public static String doCallOrCheck(Player player) {
        if (player.getBet() != Stages.MaxBet) {
            player.doBet(Stages.MaxBet);
            player.setLastAction("Call");
//            Stages.nextPlayer();
            return "Call";
        } else {
            player.setLastAction("Check");
//            Stages.nextPlayer();
            return "Check";
        }
    }

    public static String doRise(Player player) {
        Stages.lastBetPlayer = player.getNumber();
        Stages.MaxBet = player.doBet((int) (Stages.MaxBet*1.5));
        for (Player player1: Stages.players) {
            player1.setLastAction(null);
        }
        player.setLastAction("Rise");
//        Stages.nextPlayer();
        return "Rise";
    }
}
