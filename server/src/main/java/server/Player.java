package server;

import server.elements.Wall;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

public class Player implements Runnable{
    //player attributes
    private int posX;
    private int posY;
    private final String username;
    private int money;
    private boolean isDead;

    //Network attributes
    boolean isConnected;
    Socket s;
    BufferedReader in;
    PrintStream out;
    
    public Player(Socket s, String name) {
    	this.username = name;
    	this.isDead = false;
        this.isConnected = true;
        this.s = s;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintStream(s.getOutputStream());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }
    
    //Network methods
    public String getName() {       //returns the name of the class instance
        return username;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean sendMessage(String message) {        //This method sends a message to the client handled by the instance of the class
        out.println(message);
        out.flush();
        return true;
    }

    @Override
    public void run() {

        String msg = "";        // This loop handles the printing of the incoming messages
        while(ServerMain.isRunning() && msg != null) {    //As long as the remote socket is connected
            try {
                msg = in.readLine();    //read the input

               if(!ServerMain.isRunning() || msg == null) {       // if the socket is disconnected
                    System.out.println(this.username+" disconnected !");    //inform the user
                    break;                                              //break out of the loop
                }
                System.out.println(this.username+" wrote: "+msg);   //print the message
            } catch(IOException e) {
                System.out.println(this.username + (": socket closed by the server."));
            }
        }
        this.isConnected = false;   //update status
    }

    public void stop() {
        try {
            this.sendMessage("Server Closed.");
            this.s.close();                                                                 //This line closes the socket s which unblocks the execution of run()
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	}while(!b.getElementAt(pos[1],pos[0]) instanceof (? extends Element));//keep going until the starting location isn't a special item(!!!not sure that '? extends Element' can be used here as expected to point a treasure or hole or wall. Need a confirmation!!!) 
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
	if(b.getElementAt(tab[1], tab[0]) instanceof Treasure){//The player steps on a treasure, the content is added to his money and the treasure is emptied
	    this.addMoney(b.getElementAt(tab[1], tab[0]).getTreasureValue());
	    b.getElementAt(tab[1], tab[0]).setTreasureValue(0);
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
	    this.setPos(b, currentpos);
	}
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
