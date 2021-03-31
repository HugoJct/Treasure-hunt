public class GameInfo {
    private static int nbrOfHoles = 0;
    private static int nbrOfWalls = 0;
    private static int nbrOfTreasures = 0;
    private static int nbrOfPlayers = 0;

    private static int[] map = new map[2];  
    private static int[] mainPlayerPos = new mainPlayerPos[2];
    private static int[] holesPos;
    private static int[] wallsPos;
    private static int[] tresuresPos;   
    private static int[] playerPos;

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
    public static int[] getMap() {
        return map;
    }
    public static int[] getHolesPos() {
        if (holesPos != null) {
            return holesPos;
        }
    }
    public static int[] getWallsPos() {
        if (wallsPos != null) {
            return wallsPos;
        }
    }
    public static int[] getTreasuresPos() {
        if (tresuresPos != null) {
            return wallsPos;
        }
    }
    public static int[] getPlayerPos() {
        if (playerPos != null) {
            return playerPos;
        }
    }
    public static int[] getMainPlayerPos() {
        return mainPlayerPos;
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
    public static void setMap(int x, int y) {
        map[0] = x;
        map[1] = y;
    }
    public static void setHolesPos(int pos, int x, int y) {
        holesPos[pos] = x;
        holesPos[pos+1] = y;
    }
    public static void setWallsPos(int pos, int x, int y) {
        wallsPos[pos] = x;
        wallsPos[pos+1] = y;
    }
    public static void setTreasuresPos(int pos, int x, int y) {
        treasuresPos[pos] = x;
        treasuresPos[pos+1] = y;
    }
    public static void setPlayersPos(int pos, int x, int y) {
        holesPos[pos] = x;
        holesPos[pos+1] = y;
    }
    public static void initHolesPos() {
        holesPos = new holesPos[nbrOfHoles*2];
    }
    public static void initWallsPos() {
        wallsPos = new wallsPos[nbrOfWalls*2];
    }
    public static void initTreasuresPos() {
        treasuresPos = new treasuresPos[nbrOfTreasures*2];
    }
    public static void initPlayerPos() {
        playerPos = new playerPos[nbrOfPlayers*2];
    }
    public static void setMainPlayerPos(int x, int y) {
        mainPlayerPos[0] = x;
        mainPlayerPos[1] = y;
    }
}