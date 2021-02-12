package server.src.main.java.server;

import server.src.main.java.server.elements.Wall;

public class Player {
    private int posX;
    private int posY;
    private final String username;
    private int money;
    private boolean isDead;
    
    public Player(String name) {
    	this.username = name;
    	this.isDead = false;
    }
    
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
    
    public String toString() {
    	return this.username + " ["+this.posX+","+this.posY+"] "+money+"$ "+isDead;
    }
}
