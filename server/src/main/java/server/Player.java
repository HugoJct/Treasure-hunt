package server;

import server.elements.*;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

public class Player {
    //player attributes
    private int posX;
    private int posY;
    private static int globalId = 0;
    private String username;
    private final int playerID;
    private int money;
    private boolean isDead;
    private int gameID = -1;
    private boolean ready;

    private String _msg;

    //Network attributes
    boolean isConnected;
    Socket s;
    
    public Player(Socket s, String name) {
    	this.username = name;
    	this.isDead = false;
        this.isConnected = true;
        this.s = s;
        this._msg = "";
        this.ready = false;
        this.playerID = globalId;
        globalId++;
    }
    
    //Network methods
    public String getName() {       //returns the name of the class instance
        return username;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setGame(Game g) {
        if(this.gameID == -1)
            this.gameID = g.getID();
        else if(this.gameID == g.getID())
            System.out.println("The player is already connected to this game");
        else
            System.out.println("The player is already connected to another game");
    }

    public void leaveGame() {
        if(this.gameID != -1)
            this.gameID = -1;
        else
            System.out.println("This player isn't currently in any game");
    }


    //Player managament methods
    protected void addMoney(int amount) {
    	this.money += amount;
    }

    protected void setStartingPos(Board b){ //initiate beginning positions randomly for players
    	Random rx = new Random();
    	Random ry = new Random();
    	int[] pos = {-1,-1};
    	do{
    	    pos[0] = ry.nextInt(b.getSizeY()-1)+ 1; 
    	    pos[1] = rx.nextInt(b.getSizeX()-1)+ 1;
         } while(b.getElementAt(pos[1],pos[0]) != null);/*keep going until the starting location isn't a special item */
    	this.setPos(b, pos);
    }

    protected boolean setPos(Board b, int[] tab) {
    	if(b.getElementAt(tab[1], tab[0]) instanceof Wall) {
    		return false;
    	}
    	this.posX = tab[1];
    	this.posY = tab[0];
    	if(b.getElementAt(tab[1], tab[0]) instanceof Hole){ //Added this directly in this function to avoid overencumber the run method in 'Game'
    	    this.killPlayer();
    	}
    	if((b.getElementAt(tab[1], tab[0]) instanceof Treasure)){//The player steps on a treasure, the content is added to his money and the treasure is emptied
    	    Treasure tmp = (Treasure)b.getElementAt(tab[1], tab[0]);
    	    this.addMoney(tmp.getTreasureValue());
    	    tmp.setTreasureValue(0);
    	}
    	return true;
    }

    protected void setPosFromInput(Board b, int[] currentPos){
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Move: Up(u),Right(r),Left(l),Down(d)");
    	if(sc.nextLine().equals("u")){
    	    currentPos[0] -= 1;
    	    this.setPos(b, currentPos);
    	}
    	if(sc.nextLine().equals("r")){
    	    currentPos[1] += 1;
    	    this.setPos(b, currentPos);
    	}
    	if(sc.nextLine().equals("l")){
    	    currentPos[1] -= 1;
    	    this.setPos(b, currentPos);
    	}
    	if(sc.nextLine().equals("d")){
    	    currentPos[0] += 1;
    	    this.setPos(b, currentPos);
    	}
    }
    
    public void killPlayer() {
    	isDead = true;
    }
    
    public boolean isPlayerDead() {
    	return isDead;
    }
    
    public int[] getPos() {
    	int[] tab = {posX,posY};
    	return tab;
    }

    public Socket getSocket() {
        return this.s;
    }

    public String getMsg() {
        return this._msg;
    }

    public int getGameId() {
        return this.gameID;
    }
    
    public int getPlayerId() {
        return this.playerID;
    }

    public void setGameId(int id) {
        this.gameID = id;
    }

    public String getUserName() {
        return this.username;
    }

    public boolean getReady() {
        return this.ready;
    }
    
    public int getMoney(){
        return this.money;
    }


    public void setReady(boolean b) {
        this.ready = b;
    }

    public void setUserName(String s) {
        this.username = s;
    }

    public String toString() {
    	return this.username + " ["+this.posX+","+this.posY+"] "+money+"$ "+isDead;
    }

}
