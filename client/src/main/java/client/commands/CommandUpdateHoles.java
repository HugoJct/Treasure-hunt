package client.commands;

import java.io.PrintWriter;

import client.GameInfo;
import client.Player;

public class CommandUpdateHoles extends Command{

	public CommandUpdateHoles(PrintWriter out) {
		super("401",out);
	}

	public void execute(Player p, String[] args) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setHoles(Integer.parseInt(args[2]));
			GameInfo.initHolesPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getHoles() * 2);i++) {
				if(GameInfo.getHolesPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setHolesPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]));
				i++;
				fill += 2;
			}
		}
	}
}