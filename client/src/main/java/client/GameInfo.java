public class GameInfo {
    private final int nbrOfHoles;
    private final int nbrOfWalls;
    private int nbrOfTreasures;
    private int nbrOfPlayers;

    public GameInfo(int h, int w, int t, int p) {
        this.nbrOfHoles = h;
        this.nbrOfWalls = w;
        this.nbrOfTreasures = t;
        this.nbrOfPlayers = p;
    }

    public int getHoles() {
        return this.nbrOfHoles;
    }
    public int getWalls() {
        return this.nbrOfWalls;
    }
    public int getTreasures() {
        return this.nbrOfTreasures;
    }
    public int getPlayers() {
        return this.nbrOfPlayers;
    }
}