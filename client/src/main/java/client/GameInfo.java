public class GameInfo {
    private static int nbrOfHoles = 0;
    private static int nbrOfWalls = 0;
    private static int nbrOfTreasures = 0;
    private static int nbrOfPlayers = 0;

    public static int getHoles() {
        return nbrOfHoles;
    }
    public static int getWalls() {
        return nbrOfWalls;
    }
    public static int getTreasures() {
        return nbrOfTreasures;
    }
    public static int getPlayers() {
        return nbrOfPlayers;
    }

    public static void setHoles(int h) {
        nbrOfHoles = h;
    }
    public static void setWalls(int w) {
        nbrOfWalls = w;
    }
    public static void setTreasures(int t) {
        nbrOfTreasures = t;
    }
    public static void setPlayers(int p) {
        nbrOfPlayers = p;
    }
}