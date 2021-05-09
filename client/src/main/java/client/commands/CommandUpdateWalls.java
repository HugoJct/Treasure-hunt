package client.commands;

import java.io.PrintWriter;

import client.GameInfo;
import client.Player;

public class CommandUpdateWalls extends Command{

	public CommandUpdateWalls(PrintWriter out) {
		super("421",out);
	}

	public void execute(Player p, String[] args) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setWalls(Integer.parseInt(args[2]));
			GameInfo.initWallsPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getWalls() * 3);i++) {
				if(GameInfo.getWallsPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setWallsPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]));
				i++;
				fill += 2;
			}
		} 
	}
}