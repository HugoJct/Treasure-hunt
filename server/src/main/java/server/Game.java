package server;

import java.util.Vector;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

public class Game implements Runnable{

	private Vector<Player> players = new Vector<>();

	private Board b;
	private boolean isRunning;
	private final String name;
	private final int capacity;

	private static int id = 0;

	public Game() {
		this.isRunning = false;
		this.name = "default";
		try{
			FileReader reader = new FileReader("src/main/java/server/GameConfig.json");

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			JSONArray msg = (JSONArray) jsonObject.get("dimensions");
			Iterator<Integer> iterator = msg.iterator();

			int dimX = ((Integer) iterator.next()).intValue();
			int dimY = ((Integer) iterator.next()).intValue();

			b = new Board(dimX,dimY);

		} catch(IOException e) {
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		}

		b = new Board();
	}

	public Game(String name) {
		this.isRunning = false;
		this.name = name;
		b = new Board();
		this.id++;
	}

	public void stop() {
		this.isRunning = false;
	}

	public void start() {
		this.isRunning = true;
	}

	public int getID() {
		return this.id;
	}

	public boolean addPlayer(Player p) {		//this method tests if a player doesn't have the same name as another one in the game
		for(Player p2 : players) {
			if(p.getName() == p2.getName()) {
				System.out.println("Two players can't have the same name");
				return false;
			}
		}	
		players.add(p);
		p.setGame(this);
		return true;
	}

	public boolean removePlayer(Player p) {
		for(Player p2 : players) {
			if(p == p2) {
				players.remove(p);
				return true;
			}
		}
		return false;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	public String toString() {
		String s = this.name;
		for(Player p : players)
			s += players.toString() + "\n";
		return s;
	}

	@Override
	public void run() {

		while(!isRunning) {
			try {
				Thread.sleep(1);
				if(!ServerMain.isRunning()) {
					System.out.println("Server stopped ! ");
					return;
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	    //By taking the assumption that they will only be 3 players in the game(will change later so that it adapts to more players)
	    Player players[] = new Player[3];

	    for(int i =0;i<players.length;i++){
			players[i].setStartingPos(this.b); //Initializing a starting position for each player
	    }

	    while(ServerMain.isRunning()) {
			for(int i=0; i<players.length ; i++){
			    if(!players[i].isPlayerDead() && players[i].isConnected){
					players[i].setPosFromInput(this.b, players[i].getPos()); 
				
			    }
			    if(this.b.sumAllTreasures() == 0){
					this.stop();
			    }
			    	if(!this.isRunning){
					break;
			    }
			    // Should add a printing of all the ranks for each player along with the money collected by each one
			}
			if(!this.isRunning){
		    	break;
			}
	    }
	}
}
