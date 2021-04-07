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
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class ServerMain {

	public static Vector<Player> connectedUsers = new Vector<>();

	public static Vector<Game> createGames = new Vector<>();

	public static Vector<Communication> launchedCom = new Vector<>();

	public static Vector<Console> launchedCons = new Vector<>();

	private static boolean isRunning = true;
	private static ConnectionHandler ch;
	private static Console console;
	private static int port;
	private static File configFile;

	public static void main(String[] args) {

		try {
			if(args.length >= 1)
				configFile = new File(args[0]);
			else
				configFile = new File("src/main/java/server/config.json");

			FileReader reader = new FileReader(configFile);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            port = ((Long) jsonObject.get("port")).intValue();

        }  catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }

		ch = new ConnectionHandler(port);		//Launch the server	
		Thread waitForConnection = new Thread(ch);		//Create and launch the thread for the connection handler
		waitForConnection.start();

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
			wholeMessage += message[i]+" ";
		for(Communication c : launchedCom)
			c.sendMessage(wholeMessage);
	}

	public static void broadcastPerGame(int[] message) {
		if (message[0] == 152) {
			for (Communication c : launchedCom) {
				if (c.getPlayer().getGameId() == message[1]) {
					c.sendMessage("152");
				}
			}
		}
		if (message[0] == 153) {
			for (Communication c : launchedCom) {
				if (c.getPlayer().getGameId() == message[1]) {
					c.sendMessage("153");
				}
			}
		}
	}

	public static boolean checkForLaunch(int gameID) {
		for (Player p : connectedUsers) {
			if (p.getGameId() == gameID && p.getReady() == false) {
				//System.out.println("Everyone is not ready");
				return false;
			}
		}
		return true;
	}

	public static void printGame(int gameID) {
		for (Game g : createGames) {
			if (g.getGameId() == gameID) {
				System.out.println(g.getBoard().toString());
			}
		}
	}

	public static String printConnectedUsers() {					//method charged of executing the behaviour of the "listusers" command
		String list = "";
		if(connectedUsers.size() > 0) {
			for(Player p : connectedUsers) {
				list += (p + ", ");
			}
			return list;
		}
		else {
			return "There is currently no user connected.";
		}		
	}

	public static int createGame(String name) {		//this creates the game with the specified name 
		Game g = new Game(name);
		createGames.add(g);

		Thread game = new Thread(g);
		game.start();
		return g.getGameId();
	}

	public static void launchGame(int id) {
		for (Game g : createGames) {
			if (g.getGameId() == id) {
				g.start();
			}
		}
	}

	public static boolean joinGame(String[] info) {	// 130 JOIN gameId playerName 
		for(Game g : createGames) {	
			if(g.getGameId() == Integer.parseInt(info[2])) {
				for(Player p : connectedUsers) {
					if(p.getName().equals(info[3])) {
						g.addPlayer(p);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String stopGame(int id) {		//stops the specified game
		for(Game g : createGames) {
			if(g.getID() == id) {
				g.stop();
				createGames.remove(g);
				return "Game ID : " + g + " Stopped";
			}
		}
		return "Game ID not found";
	}

	public static String listGames() {		//lists the existing games
		String list = "121 ID ";
		if(createGames.size() > 0) {
			for(Game g : createGames) {
				list += (g + " ");
			}
		}	
		return list;
	}

	public static String listGamesWithDetails(){
		String games = "";
		for(Game g : createGames){
			games += "["+g+" ; "+g.getPlayers().size()+" Players Connected ; "+g.getBoard().getSizeX()+"x"+g.getBoard().getSizeY()+" Board ; Status: ";
			if(g.getPlayers().size() < g.getCapacity()){
				games +="Waiting for additional players (At least "+(g.getCapacity() - g.getPlayers().size())+" more)";
			}else if(g.getPlayers().size() >= g.getCapacity() && !(g.isRunning())){
				games += "About to start";
			}else if(g.isRunning()){
				games += "Running ; Leading player : "+g.leadingPlayer()+" ; Players left : "+g.getPlayers().size();
			}

			games += "]\n";
		}
		return games;
	}


	public static String listNbrOfGames() {
		int x = 0;
		if(createGames.size() > 0) {
			for (Game g : createGames) {
				x++;
			}
		}
		return "121 NUMBER " + x;
	}

	public static void stop() {		//this method sets the boolean to false to stop the execution of server relateds threads
		isRunning = false;
		for(Game g : createGames)
			g.stop();
		for(Communication c : launchedCom)
			c.stop();
		ch.stop();				//this line closes the ServerSocket of the ConnectionHandler class
	}

	public static boolean isRunning() {
		return isRunning;	
	}

	public static int getPort() {
		return port;
	}

	public static String getConfigFile() {
		return configFile.getPath();
	}
}
