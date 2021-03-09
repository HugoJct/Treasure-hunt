package server;

public class Game implements Runnable{

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

	public int getID() {
		return this.id;
	}

	public String toString() {
		return this.id + ". " +this.name+" "+this.isRunning;
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
