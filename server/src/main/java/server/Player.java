package server;

import server.elements.Wall;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
