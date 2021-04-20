package server;

import java.util.Vector;
import java.util.ArrayList;
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

	private int ownerID = -1;

	private static int id = 0;
	private int gameId;
	public Game(String name, int ownerID) {

		this.isRunning = false;
		this.name = "default";
		this.capacity = 4;
		this.b = new Board();
		this.gameId = id;
		this.ownerID = ownerID;
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
			this.name = name;

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
							Treasure t = new Treasure(Integer.parseInt(amount));
							this.b.setElementAt(t,j+1,i+1);
							break;
						case ' ':
							break;
						default:
							System.out.println("Unknown symbol encountered while parsing board configuration layout");
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
	}/*

	public Game(String name) {
		this.isRunning = false;
		this.name = name;
		this.capacity = 4;
		b = new Board();
		this.gameId = id;
		this.id++;
	}*/

	public String getName(){
		return this.name;
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

	@Override
	public void run() {

		while(!isRunning) {
			try {
				Thread.sleep(1);
				if(!ServerMain.isRunning()) {
					System.out.println("Server stopped !");
					return;
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

	    for(int i =0;i<players.size();i++){
			players.get(i).setStartingPos(this.b); //Initializing a starting position for each player
	    }

		while(this.b.getTreasureCount() != 0 && !this.areAllPlayersDead()) {
			System.out.print("");
			if(!ServerMain.isRunning())
				break;
		}
		System.out.print("Game ended");
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

	public Player leadingPlayer(Vector<Player> p){ // returns the leading player in p
		if(p.size() > 0){
			Player leading = p.get(0);
			for(int i = 1 ; i<p.size() ; i++){
				if(p.get(i).getMoney() > leading.getMoney()){
					leading = p.get(i);
				}
			}
			return leading;
		}
		System.out.println("No players in game");
		return null;
	}

	public int leadingPlayerIndex(Vector<Player> p){  // returns the index of the leading player(that has the most money) in p
		int ret = -1;
		if(p.size() > 0){
			Player leading = p.get(0);
			ret = 0;
			for(int i = 1 ; i<p.size() ; i++){
				if(p.get(i).getMoney() > leading.getMoney()){
					leading = p.get(i);
					ret = i;
				}
			}
		}
		
		return ret;
	}


	public Player leadingPlayer(){ // returns the leading player in the players Vector attribute of the class
		return leadingPlayer(this.players);
	}

	public String gameRank(){ // prints the rank of each player along with the amount of money collected by each one. Takes only still connected players into account
		int rank = 0;
		Vector<Player> p = (Vector<Player>)this.players.clone();
		String ret = "----------Ranking----------\n";
		while(p.size()>0){ // while the temporary Vector p is not empty. we add each player with their appropriate ranking
			ret += "[ "+(++rank)+". "+this.leadingPlayer(p).getUserName()+"   Score: "+this.leadingPlayer(p).getMoney()+" ]\n";
			p.remove(this.leadingPlayerIndex(p));
		}
		return ret;
	}

	public void endGameRequest(){ 
		ArrayList<Player> playerList = new ArrayList<Player>(players); //shallow copy of players
		for(int i = 0 ; i<playerList.size();i++){
			boolean decision = playerList.get(i).endGameRequest();
			if(!decision){
				playerList.get(i).leaveGame();
				playerList.remove(i);
			}
		}
		Vector<Player> playersToRedirect = new Vector<Player>();
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i) != null){
				playersToRedirect.addElement(playerList.get(i));
			}
		}
		ServerMain.redirectPlayers(playersToRedirect);
	}

	public void stop() {
		this.isRunning = false;
	}

	public void start() {
		this.isRunning = true;
	}

	public boolean areAllPlayersDead() {
		boolean b = true;
		for(Player p : players) {
			if(!p.isPlayerDead()) {
				b = false;
				break;
			}
		}
		return b;
	}

	public int getID() {
		return this.id;
	}

	public Board getBoard(){
		return this.b;
	}

	public int getOwnerID() {
		return this.ownerID;
	}

	public boolean isRunning(){
		return this.isRunning;
	}

	public int getCapacity(){
		return this.capacity;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	public int getGameId() {
		return this.gameId;
	}

	public String toString() {
		String s = this.gameId + " " + this.name;
		for(Player p : players)
			s += players.toString();
		return s;
	}
}
