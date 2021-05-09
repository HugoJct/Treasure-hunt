package client.commands;

import java.io.PrintWriter;

import client.Player;
import client.GameInfo;

public class CommandWalkedOnTreasure extends Command {
	
	public CommandWalkedOnTreasure(PrintWriter out) {
		super("203",out);
	}

	public void execute(Player p, String[] args) {
		GameInfo.addMoney(Integer.parseInt(args[4]));
		System.out.println("Treasure found: "+args[4]+"$, you now have "+GameInfo.getMoney()+"$");
	}
}