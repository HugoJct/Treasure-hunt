package client.commands.userIn;

import java.io.PrintWriter;

import client.commands.Command;
import client.Player;
import client.control.shell.Console;
import client.GameInfo;

public class CommandMove extends Command {

	public CommandMove(PrintWriter out) {
		super("200",out);
	}

	public void execute(Player p, String[] args) {
		if (GameInfo.getLifeState() == false) {
					move(args[1]);
				} else {
					System.out.println("You can't, you are dead...");
				}
	}

	public void move(String direction) {
		switch(direction) {
			case "UP":
				Console.setLastMoveRequested(1);
				sendMessage("200 GOUP");
				break;
			case "DOWN":
				Console.setLastMoveRequested(2);
				sendMessage("200 GODOWN");
				break;
			case "LEFT":
				Console.setLastMoveRequested(4);
				sendMessage("200 GOLEFT");
				break;
			case "RIGHT":
				Console.setLastMoveRequested(3);
				sendMessage("200 GORIGHT");
				break;
			default:
				System.out.println("No valid direction was recognized");
				break;
		}
	}
} 