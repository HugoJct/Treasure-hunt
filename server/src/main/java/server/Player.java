package server;

import server.elements.Wall;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    
    protected boolean setPos(Board b, int[] tab) {
    	if(b.getElementAt(tab[1], tab[0]) instanceof Wall) {
    		return false;
    	}
    	this.posX = tab[1];
    	this.posY = tab[0];
    	return true;
    }
    
    protected void killPlayer() {
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
