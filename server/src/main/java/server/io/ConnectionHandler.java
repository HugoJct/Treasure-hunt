package server.io;

import server.ServerMain;
import server.Player;
import server.io.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class ConnectionHandler implements Runnable{

	Vector<Player> users = ServerMain.connectedUsers;
	private int port;
	ServerSocket serverSoc;

	public ConnectionHandler(int port) {
		this.port = port;
	}

	public void run() {
		try {		//This whole code could be turned into a thread to make things more readable and spare space into the main. 
			serverSoc = new ServerSocket(this.port);	//opening the server
			Socket client;					
			while(ServerMain.isRunning()) {			//as long as the server is running
					client = serverSoc.accept();		//waiting for connection
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					Player sc = new Player(client,"TestUser");		//build the client manager 
					//Communication com = new Communication(sc);
					System.out.println(sc.getName()+" is now connected");	//print in the server console
					Thread t = new Thread(sc);	//build the thread with the client manager created above
					users.add(sc);		//add the client to the list
					sc.sendMessage("Connected !");		//Notify the client that the connection succeeded 
					for(Player sc2 : users) {	//list update 
						if(!sc2.isConnected())	//if the client is disconnected
							users.remove(sc2);	//it is removed from the list
						/*else if(sc2.getName().equals("Hugo") && list.size() > 1) 	// This how to send a message
							sc2.sendMessage("message à Hugo");						// To a specific user
					*/}

					t.start();	//thread start
			}
		} catch (IOException e) {
			System.out.println("ServerSocket closed by server");
		}
	}

	public void stop() {
		try {
			serverSoc.close();		//this line closes the ServerSocket cutting the execution of the accept() method
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}