package client;

import java.util.Arrays;

public class GameInfo {

    private static int[][] availableGameInfos = null;
    private static boolean isStarted = false;

    public static boolean isStarted() {
        return isStarted;
    }

    public static void setStarted(boolean b) {
        isStarted = b;
    }

    private static int nbrOfHoles = 0;
    private static int nbrOfWalls = 0;
    private static int nbrOfTreasures = 0;
    private static int nbrOfPlayers = 0;

    private static int[] map = new int[2];  
    private static int[] holesPos;
    private static int[] wallsPos;
    private static int[] treasuresPos;   
    private static int[] playerPos = new int[2];
    private static String[] playerName = {""};

    private static boolean isDead = false;

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
    public static int getGameNumber() {
        if(availableGameInfos == null)
            return 0;
        else
            return availableGameInfos.length;
    }
    
    public static boolean getLifeState() {
        return isDead;
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
        return treasuresPos;
    }
    public static int[] getPlayerPos() {
        if (playerPos != null) {
            return playerPos;
        }
        return null;
    }

    //To set the list of joinable games
    public static int[][] getJoinableGames() {
        if(availableGameInfos == null)
            return new int[0][0];
        else
            return availableGameInfos;
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

    public static void setGameNumber(int i) {
        availableGameInfos = new int[i][6];
    }
    public static void setLifeState(boolean b) {
        isDead = b;
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
    public static void removeTreasure(int x, int y) {
        int treasuresPosBis[] = new int[treasuresPos.length-3];
        int j = 0;
        for (int i = 0 ; i<treasuresPos.length ; i+=3) {
            if (treasuresPos[i] != y && treasuresPos[i+1] != x) {
                treasuresPosBis[j] = treasuresPos[i];
                treasuresPosBis[j+1] = treasuresPos[i+1];
                treasuresPosBis[j+2] = treasuresPos[i+2];
                j+=3;
            }
        }
        treasuresPos = treasuresPosBis;
    }
    public static void setTreasuresPos(int pos, int x, int y, int v) {
        treasuresPos[pos] = x;
        treasuresPos[pos+1] = y;
        treasuresPos[pos+2] = v;        
    }

    public static void removePlayer(String name) {    
        String[] playerNameBis = new String[playerName.length-1];
        for (int i = 0 ; i<playerName.length ; i++) {
            if (playerName[i].contentEquals(name)) {
                removePlayerPos(i*2);
            } else {
                playerNameBis[i] = playerName[i];
            }
        }
        playerName = playerNameBis;
    }
    public static void removePlayerPos(int pos) {
        int[] playerPosBis = new int[playerPos.length-2];
        for (int i = 0 ; i<playerPos.length ; i++) {
            if (i == pos) {
                i++;
            } else {
                playerPosBis[i] = playerPos[i];
            }
        }
        playerPos = playerPosBis;
    }


    public static void setGamePos(int pos, int[] tab) {
        availableGameInfos[pos] = tab;
    }

    public static void setPlayers(String name, int x, int y) {
        int pos = 0;
        if (!checkPlayerName(name)) {
            addPlayerName(name);
            addPlayersPos(x, y);
        } else {
            pos = findPos(name)*2;
            playerPos[pos] = x;
            playerPos[pos+1] = y;
        }
    }
    public static int findPos(String name) {
        int i = 0;
        while (!playerName[i].equals(name)) {
            i++;
        } return i;
    }
    public static boolean checkPlayerName(String name) {
        for (int i = 0 ; i<playerName.length ; i++) {
            if (playerName[i].equals(name)) {
                return true;
            }
        } return false;
    }
    public static void addPlayersPos(int x, int y) {
        if (playerPos.length != 2) {
            int[] tBis = new int[playerPos.length + 2];
            tBis[playerPos.length + 1] = x;
            tBis[playerPos.length + 2] = y;
            playerPos = tBis;
        } else {
            playerPos[0] = x;
            playerPos[1] = y;
        }
        nbrOfPlayers++;

    }
    public static void addPlayerName(String name) {
        
        if (playerName.length != 1) {
            String[] tBis = new String[playerName.length + 1];
            tBis[playerName.length + 1] = name;
            playerName = tBis;
        } else {
            playerName[0] = name;
        }

    }

    // to init various position
    public static void initHolesPos() {
        holesPos = new int[nbrOfHoles*2];
    }
    public static void initWallsPos() {
        wallsPos = new int[nbrOfWalls*2];
    }
    public static void initTreasuresPos() {
        treasuresPos = new int[nbrOfTreasures*3];
    }
    
}