package client.commands;

import java.io.PrintWriter;

import client.Player;
import client.GameInfo;

public class CommandMoveHoleDead extends Command {
	
	public CommandMoveHoleDead(PrintWriter out) {
		super("152",out);
	}

	public void execute(Player p, String[] args) {
		GameInfo.setLifeState(true);
		GameInfo.resetMoney();
		System.out.println("U'R DEAD");
	}
}