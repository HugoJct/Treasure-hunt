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
    private final String username;
    private int money;
    private boolean isDead;

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
    }
    
    //Network methods
    public String getName() {       //returns the name of the class instance
        return username;
    }

    public boolean isConnected() {
        return isConnected;
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
     	}while(b.getElementAt(pos[1],pos[0]) != null);/*keep going until the starting location isn't a special item */
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
    
    public String toString() {
    	return this.username + " ["+this.posX+","+this.posY+"] "+money+"$ "+isDead;
    }

}
