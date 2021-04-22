package client.control.shell;

import client.Player;
import client.connex.Communication;
import client.GameInfo;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Scanner;

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
			case "CREATEGAME":
				createGame(brokenCommand[1]);
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
				move(brokenCommand[1]);
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
				Player.printGameBoard();
				break;
			case "PRINTDIMS":
				System.out.println((GameInfo.getMap()[0]-2)+" "+(GameInfo.getMap()[1]-2));
			case "PRINTGAMES":
				System.out.println(Arrays.deepToString(GameInfo.getJoinableGames()));
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

	public void createGame(String name) {
		_com.sendMessage("110 CREATE "+name+" "+_com.getPlayer().getName());
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

	public void stopServer() {
		_com.sendMessage("0");
	}
}
