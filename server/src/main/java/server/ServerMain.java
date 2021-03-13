package server;

import server.elements.Element;
import server.elements.Hole;
import server.elements.Treasure;
import server.elements.Wall;

import server.Board;
import server.Player;
import server.Game;
import server.io.Communication;

import server.io.ConnectionHandler;

import java.util.Vector;

public class ServerMain {

	public static Vector<Player> connectedUsers = new Vector<>();

	public static Vector<Game> launchedGames = new Vector<>();

	public static Vector<Communication> launchedCom = new Vector<>();

	public static Vector<Console> launchedCons = new Vector<>();

	private static boolean isRunning = true;
	private static ConnectionHandler ch;
	private static Console console;

	public ServerMain() {}

	public static void main(String[] args) {

		ch = new ConnectionHandler(12345);		//Launch the server	
		Thread waitForConnection = new Thread(ch);		//Create and launch the thread for the connection handler
		waitForConnection.start();
		while (true) {
			if (ch.getCom() != null) {
				console = new Console(ch.getCom());
				break;
			}
		}		

		Thread checkInput = new Thread(console);		//Create and launch the thread for the connection handler
		checkInput.start();
	}

	public static void broadcastMessage(String message) {		//method charged of executing the behaviour of the "broadcast" command
		for(Communication c : launchedCom)
			c.sendMessage(message);
	}

	public static void broadcastMessage(String[] message) {		//method charged of executing the behaviour of the "broadcast" command but from a string array (making it easier to use with the command breaker)
		String wholeMessage = "";
		for(int i=1;i<message.length;i++)
			wholeMessage += message[i] + " ";
		for(Communication c : launchedCom)
			c.sendMessage(wholeMessage);
	}

	public static void printConnectedUsers() {					//method charged of executing the behaviour of the "listusers" command
		if(connectedUsers.size() > 0)
			for(Player p : connectedUsers)
				System.out.println(p);
		else
			System.out.println("There is currently no user connected.");
	}

	public static void createGame(String name) {		//this creates the game with the specified name 
		Game g = new Game(name);
		launchedGames.add(g);

		Thread game = new Thread(g);
		game.start();
	}

	public static void stopGame(int id) {		//stops the specified game
		for(Game g : launchedGames) {
			if(g.getID() == id) {
				g.stop();
				launchedGames.remove(g);
				break;
			}
		}
	}

	public static String listGames() {		//lists the existing games
		String list = "";
		if(launchedGames.size() > 0) {
			for(Game g : launchedGames) {
				list += g;
			}
			return list;
		}	
		else {
			return "There is currently no game in progress.";
		}
	}

	public static void stop() {		//this method sets the boolean to false to stop the execution of server relateds threads
		isRunning = false;
		for(Game g : launchedGames)
			g.stop();
		for(Communication c : launchedCom)
			c.stop();
		ch.stop();				//this line closes the ServerSocket of the ConnectionHandler class
	}

	public static boolean isRunning() {
		return isRunning;	
	}
}
