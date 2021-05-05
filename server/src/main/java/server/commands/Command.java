package server.commands;

import java.io.PrintWriter;

import server.playingProps.Game;
import server.playingProps.Player;

public abstract class Command {

	private String code;
	private PrintWriter out;

	public Command(String code, PrintWriter pw) {
		this.code = code;
		out = pw;
	}

	public String toString() {
		return this.code;
	}

	protected void sendMessage(String message) {
		out.println(message);
		out.flush();
	}

	public abstract void execute(Game g, Player p, String[] args);
}