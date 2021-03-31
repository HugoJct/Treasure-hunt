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
					if(_message == null)
						break;
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
			case "0":												//STOP
				ServerMain.stop();
				break;
			case "BROADCAST":
				ServerMain.broadcastMessage(brokenCommand[1]);
				break;
			case "LISTUSERS":
				_com.sendMessage(ServerMain.printConnectedUsers()); 
				break;
			case "110":												//CREATE GAME
				ServerMain.createGame(brokenCommand[1]);
				break;
			case "130":
				if(ServerMain.joinGame(brokenCommand)) { 				//JOINGAME 130 gameId playerID
					_com.sendMessage("131 MAP "+_com.getPlayer().getGameId()+" JOINED");
					broadcastInGame("The player "+_com.getPlayer().getName()+" joined the game",_com.getPlayer().getGameId());
				}
				else 
					_com.sendMessage("No such game found");
				break;
			case "156":
				_com.sendMessage(ServerMain.stopGame(Integer.parseInt(brokenCommand[1])));	//this stops the #args[1] game (doesn't work)
				break;
			case "120":												// GETLIST
				_com.sendMessage(ServerMain.listNbrOfGames());
				_com.sendMessage(ServerMain.listGames());
				break;
			case "150":												// BROADCAST inside game (for REQUEST START)
				int[] broadcast = {152, _com.getPlayer().getGameId()};
				ServerMain.broadcastPerGame(broadcast);
				break;
			case "152":												// REQUEST START RESPONSE
				while (ServerMain.checkForLaunch(_com.getPlayer().getGameId()) == false) {
					// Waiting for everyone
				}
				int[] broadcast2 = {153, _com.getPlayer().getGameId()};
				ServerMain.broadcastPerGame(broadcast2);
				ServerMain.launchGame(broadcast2[1]);	
				break;

			case "400":												//GETHOLES
				for(Game g : ServerMain.createGames) {
					if(g.getGameId() == _com.getPlayer().getGameId()) {
						sendHoleInfo(g);
						break;
					}
				}
				break;
			case "410":												//GETTREASURES
				for(Game g : ServerMain.createGames) {
					if(g.getGameId() == _com.getPlayer().getGameId()) {
						sendTreasureInfo(g);
						break;
					}
				}
				break;
			case "420":												//GETWALLS
				for(Game g : ServerMain.createGames) {
					if(g.getGameId() == _com.getPlayer().getGameId()) {
						sendWallInfo(g);
						break;
					}
				}
				break;
			default:
				_com.sendMessage("999 COMMAND ERROR");
				break;
		}
	}

	public void sendHoleInfo(Game g) {

		int k = g.getBoard().getHoleCount();
		int[][] b = g.getBoard().getHolePos();

		_com.sendMessage("421 NUMBER "+k);

		int index = 0;
		String toSend = "";

		int stop = k%5 == 0 ? (k/5) : (k/5) + 1;

		for(int i=1;i<=stop;i++) {
			toSend = "421 MESS "+i+" POS";
			int count = 0;
			for(int j=index;j<b.length;j++) {
				index = j;
				if(count == 5)
					break;
				for(int l=0;l<b[j].length;l++) {
					toSend += " "+b[j][l];		
				}
				count++;
			}
			_com.sendMessage(toSend);
		}
	}

	public void sendWallInfo(Game g) {

		int k = g.getBoard().getWallCount();
		int[][] b = g.getBoard().getWallPos();

		_com.sendMessage("401 NUMBER "+k);

		int index = 0;
		String toSend = "";
		int stop = k%5 == 0 ? (k/5) : (k/5) + 1;

		for(int i=1;i<=stop;i++) {
			toSend = "401 MESS "+i+" POS";
			int count = 0;
			for(int j=index;j<b.length;j++) {
				index = j;
				if(count == 5)
					break;
				for(int l=0;l<b[j].length;l++) {
					toSend += " "+b[j][l];		
				}
				count++;
			}
			_com.sendMessage(toSend);
		}
	}

	public void sendTreasureInfo(Game g) {

		int k = g.getBoard().getTreasureCount();
		int[][] b = g.getBoard().getTreasurePos();

		_com.sendMessage("411 NUMBER "+k);

		int index = 0;
		String toSend = "";

		int stop = k%5 == 0 ? (k/5) : (k/5) + 1;

		for(int i=1;i<=stop;i++) {
			toSend = "411 MESS "+i+" POS";
			int count = 0;
			for(int j=index;j<b.length;j++) {
				index = j;
				if(count == 5)
					break;
				for(int l=0;l<b[j].length;l++) {
					//System.out.println("i: "+i+" j: "+j);
					toSend += " "+b[j][l];		
				}
				count++;
			}
			_com.sendMessage(toSend);
		}
	}

	public void broadcastInGame(String message, int gameID) {
		for(Communication c : ServerMain.launchedCom) {
			if(c.getPlayer().getGameId() == gameID) {
				c.sendMessage(message);
			}
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}