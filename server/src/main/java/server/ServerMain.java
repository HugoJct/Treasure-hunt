package server;

import server.elements.Element;
import server.elements.Hole;
import server.elements.Treasure;
import server.elements.Wall;

import server.Board;
import server.Player;

import server.io.ConnectionHandler;

import java.util.Vector;

public class ServerMain {

	public static Vector<Player> connectedUsers = new Vector<>();
	private static boolean isRunning = true;
	private static ConnectionHandler ch;
	private static Console console;

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

		ch = new ConnectionHandler(12345);		//Launch the server
		console = new Console();				

		Thread waitForConnection = new Thread(ch);		//Create and launch the thread for the connection handler
		waitForConnection.start();

		Thread checkInput = new Thread(console);		//Create and launch the thread for the connection handler
		checkInput.start();
	}

	public static void broadcastMessage(String message) {		//method charged of executing the behaviour of the "broadcast" command
		for(Player p : connectedUsers)
			p.sendMessage(message);
	}

	public static void broadcastMessage(String[] message) {		//method charged of executing the behaviour of the "broadcast" command but from a string array (making it easier to use with the command breaker)
		String wholeMessage = "";
		for(int i=1;i<message.length;i++)
			wholeMessage += message[i] + " ";
		for(Player p : connectedUsers)
			p.sendMessage(wholeMessage);
	}

	public static void printConnectedUsers() {					//method charged of executing the behaviour of the "listusers" command
		for(Player p : connectedUsers)
			System.out.println(p);
	}

	public static boolean isRunning() {
		return isRunning;	
	}

	public static void stop() {		//this method sets the boolean to false to stop the execution of server relateds threads
		isRunning = false;
		for(Player p : connectedUsers)
			p.stop();
		ch.stop();				//this line closes the ServerSocket of the ConnectionHandler class
	}
}
