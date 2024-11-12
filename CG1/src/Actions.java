public class Actions {
    public static void doFold(Player player) {
        player.doFold();
    }

    public static void doCall(Player player, int maxBet) {
        player.grabMoney(maxBet);
    }

    public static void doRise(Player player, int maxBet) {

    }
}
