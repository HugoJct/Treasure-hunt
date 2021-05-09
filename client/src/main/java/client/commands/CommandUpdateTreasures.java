package client.commands;

import java.io.PrintWriter;

import client.GameInfo;
import client.Player;

public class CommandUpdateTreasures extends Command{
	
	public CommandUpdateTreasures(PrintWriter out) {
		super("411",out);
	}

	public void execute(Player p, String[] args) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setTreasures(Integer.parseInt(args[2]));
			GameInfo.initTreasuresPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getTreasures() * 3);i++) {
				if(GameInfo.getTreasuresPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setTreasuresPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]),Integer.parseInt(args[i+2]));
				i+=2;
				fill += 3;
			}
		}
	}
}