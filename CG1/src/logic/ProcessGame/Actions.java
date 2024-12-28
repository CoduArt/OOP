package logic.ProcessGame;

public class Actions {
    public static String doFold(Player player, Stages stage) {
        player.doFold();
        player.setLastAction("Fold");
//        Stages.nextPlayer();
        if (stage.isRemainedLastPlayer()) {
            return null;
        }
//        if (player.getNumber() == Stages.lastBetPlayer) {
//            Stages.nextLastPlayer();
//        }
        return "Fold";
    }

    public static String doCallOrCheck(Player player, Stages stage) {
        if (player.getBet() != stage.MaxBet) {
            player.doBet(stage.MaxBet, stage);
            player.setLastAction("Call");
//            Stages.nextPlayer();
            return "Call";
        } else {
            player.setLastAction("Check");
//            Stages.nextPlayer();
            return "Check";
        }
    }

    public static String doRise(Player player, Stages stage) {
        stage.lastBetPlayer = player.getNumber();
        stage.MaxBet = player.doBet((int) (stage.MaxBet*1.5), stage);
        for (Player player1: stage.players) {
            player1.setLastAction(null);
        }
        player.setLastAction("Rise");
//        Stages.nextPlayer();
        return "Rise";
    }
}
