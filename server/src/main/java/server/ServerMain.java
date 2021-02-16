package server;

import server.elements.Element;
import server.elements.Hole;
import server.elements.Treasure;
import server.elements.Wall;
import server.connex.ServConnex;
import server.Board;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class ServerMain {

	public static Vector<ServConnex> list = new Vector<>();
	static int i = 0;

	public static void main(String[] args) {
		Board b = new Board(15,15);
		//The following is the board featured on the screenshot from the project's video presentation
		
		//Walls
		b.setElementAt(new Wall(), 5, 1);
		b.setElementAt(new Wall(), 6, 3);
		b.setElementAt(new Wall(), 6, 4);
		b.setElementAt(new Wall(), 7, 5);
		
		b.setElementAt(new Wall(), 11, 6);
		b.setElementAt(new Wall(), 11, 7);
		b.setElementAt(new Wall(), 11, 8);
		b.setElementAt(new Wall(), 11, 9);
		b.setElementAt(new Wall(), 10, 9);
		
		b.setElementAt(new Wall(), 4, 10);
		b.setElementAt(new Wall(), 4, 11);
		b.setElementAt(new Wall(), 4, 12);
		b.setElementAt(new Wall(), 5, 12);
		b.setElementAt(new Wall(), 5, 13);

		//Treasures
		b.setElementAt(new Treasure(15), 8, 1);
		b.setElementAt(new Treasure(5), 4, 4);
		b.setElementAt(new Treasure(10), 13, 4);
		b.setElementAt(new Treasure(0), 9, 5);
		b.setElementAt(new Treasure(20), 2, 11);
		b.setElementAt(new Treasure(0), 8, 11);
		b.setElementAt(new Treasure(5), 5, 8);
		b.setElementAt(new Treasure(0), 12, 9);

		//Holes
		b.setElementAt(new Hole(), 2, 8);
		b.setElementAt(new Hole(), 5, 6);
		b.setElementAt(new Hole(), 8, 4);
		b.setElementAt(new Hole(), 7, 9);
		b.setElementAt(new Hole(), 9, 13);
		b.setElementAt(new Hole(), 14, 13);
		
		System.out.println(b);
		
		Player p1 = new Player("Hugo");
		System.out.println(p1);

		try {
			ServerSocket serverSoc = new ServerSocket(12345);
			Socket client;
			while(true) {
					client = serverSoc.accept();
					System.out.println("Client "+i+" is now connected");
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					PrintStream out = new PrintStream(client.getOutputStream());
					ServConnex sc = new ServConnex(client,"client " +i,in,out);	
					Thread t = new Thread(sc);
					list.add(sc);
					for(ServConnex sc2 : list) {
						if(!sc2.isConnected)
							list.remove(sc2);
					}

					t.start();
					i++;
			}
		} catch (IOException e) {
				e.printStackTrace();
			} 
	}

}
