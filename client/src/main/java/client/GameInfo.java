public class GameInfo {
    private int nbrOfHoles;
    private int nbrOfWalls;
    private int nbrOfTreasures;
    private int nbrOfPlayers;

    public GameInfo() {
        this.nbrOfHoles = 0;
        this.nbrOfWalls = 0;
        this.nbrOfTreasures = 0;
        this.nbrOfPlayers = 0;
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

    public void setHoles(int h) {
        this.nbrOfHoles = h;
    }
    public void setWalls(int w) {
        this.nbrOfWalls = w;
    }
    public void setTreasures(int t) {
        this.nbrOfTreasures = t;
    }
    public void setPlayers(int p) {
        this.nbrOfPlayers = p;
    }
}