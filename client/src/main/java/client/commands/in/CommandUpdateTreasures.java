package client.commands.in;

import java.io.PrintWriter;

import client.GameInfo;
import client.Player;
import client.commands.Command;

import server.elements.Treasure;

public class CommandUpdateTreasures extends Command{
	
	public CommandUpdateTreasures(PrintWriter out) {
		super("411",out);
	}

	public void execute(Player p, String[] args) {

		/*Old implementation
		if (args[1].equals("NUMBER")) {
			GameInfo.setTreasures(Integer.parseInt(args[2]));
			GameInfo.initTreasuresPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getTreasuresNumber() * 3);i++) {
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
		}*/

		//New implementation
		if (args[1].equals("MESS") && args[3].equals("POS")) {
			for(int i=4;i<args.length;i+=3) {
				GameInfo.addTreasure(new Treasure(Integer.parseInt(args[i]),Integer.parseInt(args[i+1]),Integer.parseInt(args[i+2])));
			}
		}
		
	}
}