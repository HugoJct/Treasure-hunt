package server;

import java.util.Vector;

public class Game implements Runnable{

	private Vector<Player> players = new Vector<>();

	private Board b;
	private boolean isRunning;
	private final String name;
	private static int id = 0;

	public Game(String name) {
		this.isRunning = true;
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
