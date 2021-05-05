package server;

import server.elements.Element;
import server.elements.Hole;
import server.elements.Treasure;
import server.elements.Wall;

import server.playingProps.Board;
import server.playingProps.Player;
import server.playingProps.Game;

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

	//public static Vector<Console> launchedCons = new Vector<>();

	private static boolean isRunning = true;
	//private static Console console;

	private static ConnectionHandler ch;

	public static void main(String[] args) {
		File configFile = null;
		String configFilePath = "";
		int port = -1;

		try {
			if(args.length >= 1) {
				configFile = new File(configFilePath);
				configFilePath = configFile.getPath();
			} else {
				configFilePath = "src/main/java/server/config.json";
				configFile = new File(configFilePath);
			}

			FileReader reader = new FileReader(configFile);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            
			port = ((Long) jsonObject.get("port")).intValue();

        }  catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }

		ch = new ConnectionHandler(port,configFilePath);		//Launch the server	

		Thread waitForConnection = new Thread(ch);		//Create and launch the thread for the connection handler
		waitForConnection.start();
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

	public static int createGame(int gamemode, int sizeX, int sizeY, int holeNumber, int treasureNumber, int gameOwnerID) {		//this creates the game with the specified name 
		int ownerID = -1;
		for(Player p : connectedUsers)
			if(p.getPlayerId() == gameOwnerID)
				ownerID = p.getPlayerId();

		Game g = new Game(gamemode,sizeX,sizeY,holeNumber,treasureNumber,gameOwnerID);
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

	public static boolean checkForLaunch(int id) {
		boolean ok = true;
		for(Game g : createGames) {
			if(g.getGameId() == id) {
				for(Player p : g.getPlayers()) {
					while(!p.getAnswered()) {
						if(!isRunning)
							return false;
						System.out.print("");
					}
					if(!p.getReady()) {
						ok = false;
					}
				}
			}
		}
		return ok;
	}

	public static boolean joinGame(String[] args) {	// 130 JOIN gameId playerName 
		for(Game g : createGames) {	
			if(g.getGameId() == Integer.parseInt(args[2])) {
				for(Player p : connectedUsers) {
					if(p.getName().equals(args[3])) {
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

	public static int listLength(int id) {
		int i = 0;
		for (Player p : connectedUsers) {
			if (p.getGameId() == id) {
				i++;
			}
		}		
		return i;
	}


	public static boolean everyoneIsDead(Game g) {
		for (Player p : connectedUsers) {
			if (p.getGameId() == g.getGameId()) {
				if (p.isPlayerDead() == false) {
					return false;
				}
			}
		}
		return true;
	}

	public static Game getGameFromCreateGames(int id){
		for(Game g : createGames){
			if(g.getGameId() == id){
				return g;
			}
		}
		return null;
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

	public static int getNumberOfGames() {
		int x = 0;
		for (Game g : createGames) {
			x++;
		}
		return x;
	}

	public static int getGameX(int id) {
		for (Game g : createGames) {
			if (g.getGameId() == id) {
				return g.getBoard().getSizeX();
			}
		}
		return -1;
	}

	public static int getGameY(int id) {
		for (Game g : createGames) {
			if (g.getGameId() == id) {
				return g.getBoard().getSizeY();
			}
		}
		return -1;
	}

	public static int getNumberOfHoles(int id) {
		for (Game g : createGames) {
			if (g.getGameId() == id) {
				return g.getBoard().getHoleCount();
			}
		}
		return -1;
	}

	public static int getNumberOfTreasures(int id) {
		for (Game g : createGames) {
			if (g.getGameId() == id) {
				return g.getBoard().getTreasureCount();
			}
		}
		return -1;
	}

	public static Vector<Player> getConnectedUsers() {
		return connectedUsers;
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
}
