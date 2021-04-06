package client;

public class GameInfo {
    private static int nbrOfHoles = 0;
    private static int nbrOfWalls = 0;
    private static int nbrOfTreasures = 0;
    private static int nbrOfPlayers = 0;

    private static int[] map = new int[2];  
    private static int[] mainPlayerPos = new int[2];
    private static int[] holesPos;
    private static int[] wallsPos;
    private static int[] treasuresPos;   
    private static int[] playerPos;


    // to get various elements
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

    // to get the map size
    public static int[] getMap() {
        return map;
    }
    public static int[] getHolesPos() {
        return holesPos;
    }
    public static int[] getWallsPos() {
        return wallsPos;
    }
    public static int[] getTreasuresPos() {
        return wallsPos;
    }
    public static int[] getPlayerPos() {
        if (playerPos != null) {
            return playerPos;
        }
        return null;
    }

    // to get the position of the client player
    public static int[] getMainPlayerPos() {
        return mainPlayerPos;
    }

    // to set each element number 
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

    // to define the map size
    public static void setMap(int x, int y) {
        map[0] = x;
        map[1] = y;
    }

    // to define each element position
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

    // to set player client position
    public static void up() {
        mainPlayerPos[0] = mainPlayerPos[0]-1;
    }
    public static void down() {
        mainPlayerPos[0] = mainPlayerPos[0]+1;
    }
    public static void right() {
        mainPlayerPos[1] = mainPlayerPos[1]+1;
    }
    public static void left() {
        mainPlayerPos[1] = mainPlayerPos[1]-1;
    }

    // to init various position
    public static void initHolesPos() {
        holesPos = new int[nbrOfHoles*2];
    }
    public static void initWallsPos() {
        wallsPos = new int[nbrOfWalls*2];
    }
    public static void initTreasuresPos() {
        treasuresPos = new int[nbrOfTreasures*2];
    }
    public static void initPlayerPos() {
        playerPos = new int[nbrOfPlayers*2];
    }
    
    // to set the client player position
    public static void setMainPlayerPos(int x, int y) {
        mainPlayerPos[0] = x;
        mainPlayerPos[1] = y;
    }
}