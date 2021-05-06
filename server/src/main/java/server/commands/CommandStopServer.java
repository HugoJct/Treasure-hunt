package server.commands;

import java.io.PrintWriter;

import server.playingProps.Game;
import server.playingProps.Player;

import server.ServerMain;

public class CommandStopServer extends Command {

	public CommandStopServer(PrintWriter pw) {
		super("0",pw);
	}

	@Override
	public void execute(Game g, Player p, String[] args) {
		ServerMain.stop();
	}
}