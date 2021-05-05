package server.commands;

import java.io.PrintWriter;

import server.playingProps.Game;
import server.playingProps.Player;

import server.ServerMain;
import server.io.Communication;

public abstract class Command {

	private String code;
	private PrintWriter out;

	public Command(String code, PrintWriter pw) {
		this.code = code;
		out = pw;
	}

	public String toString() {
		return this.code;
	}

	protected void sendMessage(String message) {
		out.println(message);
		out.flush();
	}

	protected void broadcastInGame(String message, int gameID) {       //send a message to all players in a specific game
        for(Communication c : ServerMain.launchedCom) {
            if(c.getPlayer().getGameId() == gameID) {
                sendMessage(message);
            }
        }
    }

   	protected String[] breakCommand(String command) {         //This method breaks the command which arguments are separated by spaces
        String delims = "[ ]+";     //This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
                                    //the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
        String[] args = command.split(delims);
        return args;
    }

	public abstract void execute(Game g, Player p, String[] args);
}