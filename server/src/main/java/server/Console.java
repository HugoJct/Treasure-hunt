package server;

import server.io.*;
import server.io.Communication;
import server.Game;
import server.Player;

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
		
		_com.sendMessage("101 WELCOME "+_com.getPlayer().getName());
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

		Game g = _com.getPlayer().getGameConnectedTo();
		Player p = _com.getPlayer();

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
				int id = ServerMain.createGame(brokenCommand[2]);
				_com.sendMessage("111 MAP CREATED " + id);
				break;
			case "130":
				if(ServerMain.joinGame(brokenCommand)) { 				//JOINGAME 130 gameId playerID				
					_com.sendMessage("131 MAP "+p.getGameId()+" JOINED");
					broadcastInGame("The player "+p.getName()+" joined the game",p.getGameId());				//shitty (use player and game functions)
				}
				else 
					_com.sendMessage("No such game found");
				break;
			case "156":
				_com.sendMessage(ServerMain.stopGame(Integer.parseInt(brokenCommand[1])));	//this stops the #args[1] game (doesn't work)
				break;
			case "120":												// GETLIST
				_com.sendMessage(ServerMain.listNbrOfGames());
				sendGameInfo();
				break;
			case "150":												// BROADCAST inside game (for REQUEST START)
				int[] broadcast = {152, p.getGameId()};
				for(Player p2 : g.getPlayers()) {
					p2.setAnswered(false);
				}
				ServerMain.broadcastPerGame(broadcast);

				Thread t = new Thread() {
			      	public void run() {
			        	if(ServerMain.checkForLaunch(g.getGameId())) {
				        	ServerMain.launchGame(g.getGameId());
				        	broadcastInGame("153 GAME STARTED",g.getGameId());
							for (Player pl : ServerMain.getConnectedUsers()) {
								if (pl.getGameId() == g.getGameId()) {
									broadcastInGame("510 "+pl.getName()+" POS "+pl.getPos()[1]+" "+pl.getPos()[0],g.getGameId());
								}
							}
				        } else {
				        	String playersNotReady = "";
				        	for(Player pl : g.getPlayers()) {
				        		if(!pl.getReady())
				        			playersNotReady += pl.getName() + " ";
				        	}
				        	broadcastInGame("154 START ABORTED "+playersNotReady,g.getGameId());
				        }
				      }
			    };

			    t.start();
				break;
			case "152":												// REQUEST START RESPONSE
			//	if (ServerMain.checkForLaunch(_com.getPlayer().getGameId()) != false) {
				if(brokenCommand[2].equals("YES")) {
					p.setReady(true);
				} else {
					p.setReady(false);
				}
				p.setAnswered(true);
			//	}
				break;
			case "200":
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
					broadcastInGame("520 "+p.getName()+" DIED",g.getGameId());

				}

				break;
			case "400":												//GETHOLES
				sendHoleInfo(g);
				break;
			case "410":												//GETTREASURES
				sendTreasureInfo(g);
				break;
			case "420":												//GETWALLS
				sendWallInfo(g);
				break;
			default:
				_com.sendMessage("999 COMMAND ERROR");
				break;
		}
	}

	public void sendGameInfo() {

		int nbrOfGames = ServerMain.getNumberOfGames();
		int tab[] = new int[nbrOfGames*5];

		if (nbrOfGames != 0) {
			for (int i = 0 ; i < nbrOfGames ; i++) {
				tab[0+i] = 0;	// No game mod for now
				tab[1+i] = ServerMain.getGameX(i);
				tab[2+i] = ServerMain.getGameY(i);
				tab[3+i] = ServerMain.getNumberOfHoles(i);
				tab[4+i] = ServerMain.getNumberOfTreasures(i);
			}

			for (int i = 0 ; i < nbrOfGames ; i++) {
				_com.sendMessage("121 MESS " + (i+1) + " ID " + i + " " + tab[0+i] + " " + tab[1+i] + " " + tab[2+i] + " " + tab[3+i] + " " + tab[4+i]);
			}
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