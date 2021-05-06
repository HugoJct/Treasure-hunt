package client.control.shell;

import client.Player;
import client.connex.Communication;
import client.control.UI.application.Main;
import client.control.UI.application.Main.PrimeThread;
import javafx.application.Application;
import javafx.concurrent.Task;
import client.GameInfo;
import client.VueSwing;
import client.SceneSwing;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;

public class Console implements Runnable {

	private Communication _com;
	private String _message;
	private static int lastMoveRequested = 0;
	public static boolean startRequested = false;

	public Console(Communication com) {
		this._message = "";
		this._com = com;
		sendName();
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(Player.isConnected()) {	
			try {
				Thread.sleep(1);
				String input = sc.nextLine();
				useMessage(input);
			}
			catch(InterruptedException e) {

			}	
		}	
	}

	public void useMessage(String command) {
		String[] brokenCommand = breakCommand(command);
		if(startRequested) {
			switch(brokenCommand[0]) {
				case "y":
					_com.sendMessage("152 START YES");
					startRequested = false;
					


					
					
					javax.swing.SwingUtilities.invokeLater(
						      new Runnable() {
						        public void run() { 
						        	VueSwing v = new VueSwing();
						        }
						      }
					);
					
					
					break;
				case "n":
					_com.sendMessage("152 START NO");
					startRequested = false;
					break;
				default:
					System.out.println("Unrecognized answer");
					break;
			}
			return;
		}
		switch(brokenCommand[0]) {
			// Client -> Client
			case "UNKNOWN":
				System.out.println("Server doesn't recognised command");
				break;
			case "BROADCAST":
				broadcast(brokenCommand);
				break;
			case "GETLIST":
				listGames();
				break;
			case "CREATEGAME": 		//CREATEGAME <gamemode> <sizeX> <sizeY> <holeNumber> <treasureNumber>
				if(brokenCommand.length != 6) {
					System.out.println("Command syntax error");
				} else {
					createGame(Integer.parseInt(brokenCommand[1]),Integer.parseInt(brokenCommand[2]),Integer.parseInt(brokenCommand[3]),Integer.parseInt(brokenCommand[4]),Integer.parseInt(brokenCommand[5]));
				
				}
				break;
			case "JOIN":
				joinGame(Integer.parseInt(brokenCommand[1]));
				break;
			case "GETHOLES":
				getHoles();
				break;
			case "GETWALLS":
				getWalls();
				break;
			case "GETTREASURES":
				getTreasures();
				break;
			case "MOVE":
				if (GameInfo.getPlayable() == true && GameInfo.getLifeState() == false) {
					move(brokenCommand[1]);
				} else {
					System.out.println("You can't, you are dead...");
				}
				break;
			case "STOP":
				stopServer();
				break;
			case "REQUESTSTART":
				_com.sendMessage("150");
				break;
			case "PRINTTREASURES":
				System.out.println(Arrays.toString(GameInfo.getTreasuresPos()));
				break;
			case "PRINTWALLS":
				System.out.println(Arrays.toString(GameInfo.getWallsPos()));
				break;
			case "PRINTHOLES":
				System.out.println(Arrays.toString(GameInfo.getHolesPos()));
				break;
			case "PRINTBOARD":
				if(!GameInfo.isStarted()) {
		            System.out.println("La partie n'a pas encore commencé");
		            break;
		        }
				Player.printGameBoard();
				break;
			case "PRINTDIMS":
				System.out.println((GameInfo.getMap()[0]-2)+" "+(GameInfo.getMap()[1]-2));
				break;
			case "PRINTGAMES":
				System.out.println(Arrays.deepToString(GameInfo.getJoinableGames()));
				break;
			case "PRINTPLAYERS":
				System.out.println(Arrays.toString(GameInfo.getPlayersNames()));
				System.out.println(Arrays.toString(GameInfo.getPlayerPos()));
				break;
			default:
				System.out.println("Unknown command");
				break; 
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

	public static void setHoles(int h) {
		GameInfo.setHoles(h);	
	}

	public static void setTreasures(int t) {
		GameInfo.setTreasures(t);
	}

	public static void setWalls(int w) {
		GameInfo.setWalls(w);
	}

	public void sendName() {		//This method sends the player's name to the server when the connection occurs
		_com.sendMessage("100 HELLO PLAYER "+ Player.getName());
	}

	public void createGame(int gamemode, int sizeX, int sizeY, int holeNumber, int treasureNumber) {
		_com.sendMessage("110 CREATE "+gamemode+" SIZE "+sizeX+" "+sizeY+" HOLE "+holeNumber+" TRES "+treasureNumber);
	}

	public void listGames() {
		_com.sendMessage("120 GETLIST");
	}

	public void joinGame(int id) {
		_com.sendMessage("130 JOIN "+id+" "+_com.getPlayer().getName());
	}

	public void getHoles() {
		_com.sendMessage("400 GETHOLES");
	}

	public void getWalls() {
		_com.sendMessage("420 GETWALLS");
	}

	public void getTreasures() {
		_com.sendMessage("410 GETTREASURES");
	}

	public void broadcast(String[] s) {
		String s2 = "";
		for(int i=1;i<s.length;i++)
			s2 += " "+s[i];
		_com.sendMessage("BROADCAST"+s2);
	}

	public void move(String direction) {
		switch(direction) {
			case "UP":
				lastMoveRequested = 1;
				_com.sendMessage("200 GOUP");
				break;
			case "DOWN":
				lastMoveRequested = 2;
				_com.sendMessage("200 GODOWN");
				break;
			case "LEFT":
				lastMoveRequested = 4;
				_com.sendMessage("200 GOLEFT");
				break;
			case "RIGHT":
				lastMoveRequested = 3;
				_com.sendMessage("200 GORIGHT");
				break;
			default:
				System.out.println("No valid direction was recognized");
				break;
		}
	}

	public static int getLastMove() {
		return lastMoveRequested;
	}

	public void stopServer() {
		_com.sendMessage("0");
	}
}
