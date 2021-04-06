package server;

import server.io.*;
import server.io.Communication;
import server.Game;

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
				ServerMain.broadcastMessage(brokenCommand);
				break;
			case "LISTUSERS":
				_com.sendMessage(ServerMain.printConnectedUsers()); 
				break;
			case "110":												//110 CREATEGAME "name"
				ServerMain.createGame(brokenCommand[2]);
				break;
			case "130":
				if(ServerMain.joinGame(brokenCommand)) { 				//JOINGAME 130 gameId playerID				
					_com.sendMessage("131 MAP "+_com.getPlayer().getGameId()+" JOINED");
					broadcastInGame("The player "+_com.getPlayer().getName()+" joined the game",_com.getPlayer().getGameId());				//shitty (use player and game functions)
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
				//ServerMain.printGame(_com.getPlayer().getGameId());
				break;
			case "150":												// BROADCAST inside game (for REQUEST START)
				int[] broadcast = {152, _com.getPlayer().getGameId()};
				ServerMain.broadcastPerGame(broadcast);
				break;
			case "152":												// REQUEST START RESPONSE
			//	if (ServerMain.checkForLaunch(_com.getPlayer().getGameId()) != false) {
					int[] broadcast2 = {153, _com.getPlayer().getGameId()};
					ServerMain.broadcastPerGame(broadcast2);
					ServerMain.launchGame(broadcast2[1]);	
			//	}
				break;
			case "200":
				Game g = _com.getPlayer().getGameConnectedTo();
				Player p = _com.getPlayer();
				int[] pos = p.getPos();
				switch(brokenCommand[1]) {
					case "GOUP":
						pos[0]--;
						ServerMain.printGame(_com.getPlayer().getGameId());
						break;
					case "GODOWN":
						pos[0]++;
						ServerMain.printGame(_com.getPlayer().getGameId());
						break;
					case "GOLEFT":
						pos[1]--;
						ServerMain.printGame(_com.getPlayer().getGameId());
						break;
					case "GORIGHT":
						pos[1]++;
						ServerMain.printGame(_com.getPlayer().getGameId());
						break;
					default:
						break;
				}
				String ret = p.setPos(g.getBoard(),pos);
				pos = p.getPos();
				if(ret.equals("ok")) {

					_com.sendMessage("201 MOVE OK");
					broadcastInGame("510 "+p.getName()+" POS "+pos[1]+" "+pos[0],g.getGameId());

				} else if(ret.equals("Wall")) {

					_com.sendMessage("202 MOVE BLOCKED");
					broadcastInGame("510 "+p.getName()+" POS "+pos[1]+" "+pos[0],g.getGameId());

				} else if(ret.equals("Treasure")) {

					_com.sendMessage("203 MOVE OK TRES "+g.getBoard().getElementAt(pos[0],pos[1]));
					broadcastInGame("510 "+p.getName()+" POS "+pos[1]+" "+pos[0]+" value",g.getGameId());		//missing treasure value

				} else if(ret.equals("Hole")) {

					_com.sendMessage("666 MOVE HOLE DEAD");

				}

				break;
			case "400":												//GETHOLES
				for(Game g2 : ServerMain.createGames) {
					if(g2.getGameId() == _com.getPlayer().getGameId()) {
						sendHoleInfo(g2);
						break;
					}
				}
				break;
			case "410":												//GETTREASURES
				for(Game g2 : ServerMain.createGames) {
					if(g2.getGameId() == _com.getPlayer().getGameId()) {
						sendTreasureInfo(g2);
						break;
					}
				}
				break;
			case "420":												//GETWALLS
				for(Game g2 : ServerMain.createGames) {
					if(g2.getGameId() == _com.getPlayer().getGameId()) {
						sendWallInfo(g2);
						break;
					}
				}
				break;
			default:
				_com.sendMessage("999 COMMAND ERROR");
				break;
		}
	}

	public void sendHoleInfo(Game g) {			//for comments see sendTreasureInfo

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

	public void sendWallInfo(Game g) {			//for comments see sendTreasureInfo

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

	public void sendTreasureInfo(Game g) {			//This function generates and sends as many lines as needed to provide informations about the treasures to the client 

		int k = g.getBoard().getTreasureCount();		//initialize number of treasures
		int[][] b = g.getBoard().getTreasurePos();		//get an 

		_com.sendMessage("411 NUMBER "+k);				//send number of treasures

		int index = 0;
		String toSend = "";	

		int stop = k%5 == 0 ? (k/5) : (k/5) + 1;		//initialize the number of lines needed 

		for(int i=1;i<=stop;i++) {						//this loop executes the code "stop" times
			toSend = "411 MESS "+i+" POS";				//prepare the line
			int count = 0;
			for(int j=index;j<b.length;j++) {			//browse the array of array
				index = j;
				if(count == 5)							//if 5 couples of coordinates have been sent
					break;
				for(int l=0;l<b[j].length;l++) {		//browse each tile of the array
					toSend += " "+b[j][l];				//add the coordinates to the line
				}
				count++;
			}
			_com.sendMessage(toSend);					// send each line
		}
	}

	public void broadcastInGame(String message, int gameID) {		//send a message to all players in a specific game
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