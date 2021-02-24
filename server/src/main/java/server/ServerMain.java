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
	private boolean isRunning = true;

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

		Thread waitForConnection = new Thread(new ConnectionHandler(12345));
		waitForConnection.start();
		 
	}
}
