package server;

import server.io.*;
import server.io.Communication;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {
	private Communication _com;
	private String _message;

	public Console(Communication com) {
		this._com = com;
		this._message = "";
	}

	@Override
	public void run() {
		
		while(ServerMain.isRunning()) {
			try {
				Thread.sleep(1);
				if (_message != _com.getMessage()) {
					_message = _com.getMessage();
					useMessage(_message);
				}	
			}
			catch(InterruptedException e) {
				
			}		
		}
	}

	public void useMessage(String command) {
		String[] brokenCommand = breakCommand(command);

		switch(brokenCommand[0]) {
			case "0":
				ServerMain.stop();
				break;
			case "broadcast":
				ServerMain.broadcastMessage(brokenCommand[1]);
				break;
			case "listusers":
				_com.sendMessage(ServerMain.printConnectedUsers()); 
				break;
			case "110":	// create a new game (args[1] as name)
				ServerMain.createGame(brokenCommand[1]);
				break;
			case "130":
				ServerMain.joinGame(brokenCommand); // 130 gameId playerID
			case "156":
				_com.sendMessage(ServerMain.stopGame(Integer.parseInt(brokenCommand[1])));	//this stops the #args[1] game (doesn't work)
				break;
			case "120":	// list of existing games
				_com.sendMessage(ServerMain.listNbrOfGames());
				_com.sendMessage(ServerMain.listGames());
				break;
			case "150":
				int[] broadcast = {152, _com.getPlayer().getGameId()};
				ServerMain.broadcastPerGame(broadcast);
				break;
			case "152":
				while (ServerMain.checkForLaunch(_com.getPlayer().getGameId()) == false) {
					// Waiting for everyone
				}
				int[] broadcast2 = {153, _com.getPlayer().getGameId()};
				ServerMain.broadcastPerGame(broadcast2);
				ServerMain.launchGame(broadcast2[1]);	
				break;
			default:
				_com.sendMessage("UNKNOW");
				break;
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}