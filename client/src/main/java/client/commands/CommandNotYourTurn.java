package client.commands;

import java.io.PrintWriter;

import client.Player;
import client.GameInfo;

public class CommandNotYourTurn extends Command {
	
	public CommandNotYourTurn(PrintWriter out) {
		super("152",out);
	}

	public void execute(Player p, String[] args) {
		System.out.println("Not your round");
	}
}