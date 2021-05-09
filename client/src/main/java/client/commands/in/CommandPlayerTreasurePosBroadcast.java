package client.commands.in;

import java.io.PrintWriter;

import client.Player;
import client.GameInfo;
import client.commands.Command;

public class CommandPlayerTreasurePosBroadcast extends Command {
	
	public CommandPlayerTreasurePosBroadcast(PrintWriter out) {
		super("511",out);
	}

	public void execute(Player p, String[] args) {
		GameInfo.setTreasures(GameInfo.getTreasures()-1);
		GameInfo.removeTreasure(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
		GameInfo.setPlayers(args[1], Integer.parseInt(args[3]), Integer.parseInt(args[4]));
		sendMessage("512 " + args[1] + " UPDATED");
		Player.printGameBoard();
	}
}