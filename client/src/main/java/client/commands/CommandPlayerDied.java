package client.commands;

import java.io.PrintWriter;

import client.Player;
import client.GameInfo;

public class CommandPlayerDied extends Command {
	
	public CommandPlayerDied(PrintWriter out) {
		super("520",out);
	}

	public void execute(Player p, String[] args) {
		GameInfo.removePlayer(args[1]);
		sendMessage("521 " + Player.getName() + " UPDATED");
	}
}