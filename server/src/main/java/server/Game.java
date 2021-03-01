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
		while(ServerMain.isRunning()) {
			if(!this.isRunning)
				break;
			// Put game instructions here
		}
	}
}