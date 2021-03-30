package client.connex;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import client.Player;

public class Communication implements Runnable{

    Socket s;
    BufferedReader in;
    PrintStream out;
    String serverMsg = "";

    public Communication(Player p) {
    	this.s = p.getSocket();
    	try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintStream(s.getOutputStream());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

	public void sendMessage(String message) {        //This method sends a message to the client handled by the instance of the class
        out.println(message);
        out.flush();
    }

    public String getServerMessage() {
    	return serverMsg;
    }

	@Override
    public void run() {
        try {
        	while(Player.isConnected()) {
            	serverMsg = in.readLine();
        		if(serverMsg == null) {       // if the socket is disconnected
                	System.out.println("Disconnected !");
                	Player.isConnected = false;
                	break;
            	}
            	System.out.println("Server wrote: "+ serverMsg);
        	}
        } catch(IOException e) {
            System.out.println("Socket closed by the server.");
        }
    }

}