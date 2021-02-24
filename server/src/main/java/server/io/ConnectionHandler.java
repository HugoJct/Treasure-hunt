package server.io;

import server.ServerMain;
import server.Player;

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

		public ConnectionHandler(int port) {
			this.port = port;
		}

		public void run() {
			try {		//This whole code could be turned into a thread to make things more readable and spare space into the main. 
				ServerSocket serverSoc = new ServerSocket(this.port);	//opening the server
				Socket client;					
				while(true) {
						client = serverSoc.accept();		//waiting for connection
						BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
						Player player = new Player(client,in.readLine());
						System.out.println(player.getName()+" is now connected");	//inform the user
						Player sc = new Player(client,player.getName());		//build the client manager 
						Thread t = new Thread(sc);									//build the thread with the client manager created above
						users.add(sc);		//add the client to the list
						sc.sendMessage("Connected !");
						for(Player sc2 : users) {	//list update 
							if(!sc2.isConnected())	//if the client is disconnected
								users.remove(sc2);	//it is removed from the list
							/*else if(sc2.getName().equals("Hugo") && list.size() > 1) 	// This how to send a message
								sc2.sendMessage("message à Hugo");						// To a specific user
						*/}

						t.start();	//thread start
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}