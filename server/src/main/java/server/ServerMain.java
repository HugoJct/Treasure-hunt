package server;

import server.elements.Element;
import server.elements.Hole;
import server.elements.Treasure;
import server.elements.Wall;

import server.Board;
import server.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class ServerMain {

	public static Vector<Player> list = new Vector<>();
	static int i = 0;

	public static void main(String[] args) {
		Board b = new Board(15,15);
		//The following is the board featured on the screenshot from the project's video presentation
		
		//Walls
		b.setElementAt(new Wall(), 6, 2);
		b.setElementAt(new Wall(), 5, 4);
		b.setElementAt(new Wall(), 5, 5);
		b.setElementAt(new Wall(), 8, 6);
		
		b.setElementAt(new Wall(), 12, 7);
		b.setElementAt(new Wall(), 12, 8);
		b.setElementAt(new Wall(), 12, 9);
		b.setElementAt(new Wall(), 12, 10);
		b.setElementAt(new Wall(), 11, 10);
		
		b.setElementAt(new Wall(), 5, 11);
		b.setElementAt(new Wall(), 5, 12);
		b.setElementAt(new Wall(), 5, 13);
		b.setElementAt(new Wall(), 6, 13);
		b.setElementAt(new Wall(), 6, 14);

		//Treasures
		b.setElementAt(new Treasure(15), 9, 2);
		b.setElementAt(new Treasure(5), 5, 5);
		b.setElementAt(new Treasure(10), 14, 5);
		b.setElementAt(new Treasure(0), 10, 6);
		b.setElementAt(new Treasure(20), 3, 12);
		b.setElementAt(new Treasure(0), 9, 12);
		b.setElementAt(new Treasure(5), 6, 9);
		b.setElementAt(new Treasure(0), 13, 10);

		//Holes
		b.setElementAt(new Hole(), 3, 9);
		b.setElementAt(new Hole(), 6, 7);
		b.setElementAt(new Hole(), 9, 5);
		b.setElementAt(new Hole(), 8, 10);
		b.setElementAt(new Hole(), 10, 14);
		b.setElementAt(new Hole(), 15, 14);
		
		System.out.println(b);
		/*
		Player p1 = new Player("Hugo");
		System.out.println(p1);*/

		try {		//This whole code could be turned into a thread to make things more readable and spare space into the main. 
			ServerSocket serverSoc = new ServerSocket(12345);	//opening the server
			Socket client;					
			while(true) {
					client = serverSoc.accept();		//waiting for connection
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					Player player = new Player(client,in.readLine());
					System.out.println(player.getName()+" is now connected");	//inform the user
					Player sc = new Player(client,player.getName());		//build the client manager 
					Thread t = new Thread(sc);									//build the thread with the client manager created above
					list.add(sc);		//add the client to the list
					for(Player sc2 : list) {	//list update 
						if(!sc2.isConnected)	//if the client is disconnected
							list.remove(sc2);	//it is removed from the list
						/*else if(sc2.getName().equals("Hugo") && list.size() > 1) 	// This how to send a message
							sc2.sendMessage("message à Hugo");						// To a specific user
					*/}

					t.start();	//thread start
					i++;	//name variable incrementation
			}
		} catch (IOException e) {
				e.printStackTrace();
			} 
	}

}
