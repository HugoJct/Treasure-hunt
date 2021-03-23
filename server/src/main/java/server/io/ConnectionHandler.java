package server.io;

import server.ServerMain;
import server.Player;
import server.io.*;
import server.Console;

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
	Vector<Communication> coms = ServerMain.launchedCom;
	private int port;
	ServerSocket serverSoc;

	public ConnectionHandler(int port) {
		this.port = port;
	}

	public void run() {
		System.out.println("Config file loaded: "+ServerMain.getConfigFile());
		System.out.println("Server launched on port "+this.port);
		try {		//This whole code could be turned into a thread to make things more readable and spare space into the main. 
			serverSoc = new ServerSocket(this.port);	//opening the server
			Socket client;					
			while(ServerMain.isRunning()) {			//as long as the server is running
					client = serverSoc.accept();		//waiting for connection
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));


					String[] received = breakCommand(in.readLine());		//we receive an input from the client
					Player sc = null;
					Communication com = null;
					if(received[0].equals("100")) {							//if the received command is "100" (which means the client sends its name)
						sc = new Player(client,received[3]);		//build the client manager 
						com = new Communication(sc);
						Thread console = new Thread(new Console(com));
						console.start();
					}

					System.out.println(com.getName()+" is now connected");	//print in the server console
					//Thread t = new Thread(sc);	//build the thread with the client manager created above
					Thread c = new Thread(com);

					users.add(sc);		//add the client to the list
					coms.add(com);		//add the communication to the list

					com.sendMessage("Connected !");		//Notify the client that the connection succeeded 
					for(Player sc2 : users) {	//list update 
						if(!sc2.isConnected())	//if the client is disconnected
							users.remove(sc2);	//it is removed from the list
						/*else if(sc2.getName().equals("Hugo") && list.size() > 1) 	// This how to send a message
							sc2.sendMessage("message à Hugo");						// To a specific user
					*/}

					//t.start();	//thread start
					c.start();
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

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}
}