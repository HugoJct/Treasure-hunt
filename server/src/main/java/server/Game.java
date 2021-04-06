package server;

import java.util.Vector;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import server.elements.*;

public class Game implements Runnable{

	private Vector<Player> players = new Vector<>();

	private Board b;
	private boolean isRunning;
	private String name;
	private int capacity;

	private static int id = 0;
	private int gameId;

	public Game() {
		this.isRunning = false;
		this.name = "default";
		this.capacity = 4;
		this.b = new Board();
		this.gameId = id;
		id++;
		try{
			FileReader reader = new FileReader("src/main/java/server/GameConfig.json");

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			JSONArray msg = (JSONArray) jsonObject.get("dimensions");
			Iterator<String> iterator = msg.iterator();

			int dimX = Integer.parseInt(((String) iterator.next()));
			int dimY = Integer.parseInt(((String) iterator.next()));

			b = new Board(dimX,dimY);

			this.capacity = ((Long) jsonObject.get("capacity")).intValue();
			this.name = (String) jsonObject.get("name");

			JSONArray board = (JSONArray) jsonObject.get("elements");
			for(int i = 0;i<dimY;i++) {
				JSONArray obj = (JSONArray) board.get(i);
				for(int j=0;j<dimX;j++) {
					switch(((String) obj.get(j)).charAt(0)) {
						case 'w':
							this.b.setElementAt(new Wall(),j+1,i+1);
							break;
						case 'h':
							this.b.setElementAt(new Hole(),j+1,i+1);
							break;
						case 't':
							String amount = "";
							for(int k=1;k<((String) obj.get(j)).length();k++)
								amount += ((String) obj.get(j)).charAt(k);
							this.b.setElementAt(new Treasure(Integer.parseInt(amount)),j+1,i+1);
							break;
						case ' ':
							break;
						default:
							System.out.println("Unknown symbol encoutered while parsing board configuration layout");
							break;
					}
				}
			}
			b.countElements();
			b.setBorder();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		System.out.println(this.b);
	}

	public Game(String name) {
		this.isRunning = false;
		this.name = name;
		this.capacity = 4;
		b = new Board();
		this.gameId = id;
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

	public Board getBoard(){
		return this.b;
	}

	public boolean isRunning(){
		return this.isRunning;
	}

	public int getCapacity(){
		return this.capacity;
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

	public String leadingPlayer(){
		if(players.size() > 0){
			Player leading = players.get(0);
			for(int i = 1 ; i<players.size() ; i++){
				if(players.get(i).getMoney() > leading.getMoney()){
					leading = players.get(i);
				}
			}
			return leading.getUserName();
		}
		return "No players in game";
	}


	public Vector<Player> getPlayers() {
		return players;
	}

	public String toString() {
		String s = this.gameId + " " + this.name;
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

	    for(int i =0;i<players.size();i++){
			players.get(i).setStartingPos(this.b); //Initializing a starting position for each player
	    }

	    while(ServerMain.isRunning()) {
			for(int i=0; i<players.size() ; i++){
			    if(!(players.get(i).isPlayerDead()) && players.get(i).isConnected){
					players.get(i).setPosFromInput(this.b, players.get(i).getPos()); 
				
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

	public int getGameId() {
		return this.gameId;
	}

}
