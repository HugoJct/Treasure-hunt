package server.connex;

import server.ServerMain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ServConnex implements Runnable{

	private final String name;
	private final BufferedReader in;
	private final PrintStream out;	
	public boolean isConnected;
	Socket s;

	public ServConnex(Socket s, String name, BufferedReader in, PrintStream out) {
		this.s = s;
		this.in = in;
		this.out = out;
		this.name = name;
		this.isConnected = true;
		sendMessage("Connected !");
	}

	public String getName() {		//returns the name of the class instance
		return name;
	}

	public boolean sendMessage(String message) {		//This method sends a message to the client handled by the instance of the class
		out.println(message);
		out.flush();
		return true;
	}

	@Override
	public void run() {

		String msg = "";		// This loop handles the printing of the incoming messages
		while(msg != null) {	//As long as the remote socket is connected
			try {
				msg = in.readLine();	//read the input
				if(msg == null) {		// if the socket is disconnected
					System.out.println(this.name+" disconnected !");	//inform the user
					break;												//break out of the loop
				}
				System.out.println(this.name+" wrote: "+msg);	//print the message
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		this.isConnected = false;	//update status
	}
}
