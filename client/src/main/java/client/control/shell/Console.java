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

		switch(brokenCommand[0]) {
			// Client -> Client
			case "UNKNOW":
				System.out.println("Server doesn't recognised command");
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
			// Client -> Server
			case "152":
				_com.sendMessage("152 1");
				break;
			case "153":
				_com.sendMessage("400 GETHOLES");
				_com.sendMessage("410 GETTREASURES");
				_com.sendMessage("420 GETWALLS");
				break;
			case "201":
				if (lastMoveRequested == 1) {
					GameInfo.up();
				}
				else if (lastMoveRequested == 2) {
					GameInfo.down();
				}
				else if (lastMoveRequested == 3) {
					GameInfo.right();
				}
				else if (lastMoveRequested == 4) {
					GameInfo.left();
				}
				else {
					System.out.println("Error : no move engaged");
				}
				break;
			case "202":
				break;
			case "203":
				break;
			// set holes data
			case "401":	
				if (brokenCommand[1].equals("NUMBER")) {
					setHoles(Integer.parseInt(brokenCommand[2]));
					GameInfo.initHolesPos();
				}
				if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {
					int k = Integer.parseInt(brokenCommand[2]);
					int nbrLastCoo = GameInfo.getHoles() - (k-1)*5;
					if (k/GameInfo.getHoles() == 1) {
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setHolesPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					GameInfo.setHolesPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
					GameInfo.setHolesPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
					GameInfo.setHolesPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
					GameInfo.setHolesPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
					GameInfo.setHolesPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			// set treasures data
			case "411":	
				if (brokenCommand[1].equals("NUMER")) {
					setTreasures(Integer.parseInt(brokenCommand[2]));
					GameInfo.initTreasuresPos();
				}
				if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {
					int k = (Integer.parseInt(brokenCommand[2]));
					int nbrLastCoo = GameInfo.getTreasures() - (k-1)*5;
					if (k/GameInfo.getTreasures() == 1) {
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setTreasuresPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					GameInfo.setTreasuresPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
					GameInfo.setTreasuresPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
					GameInfo.setTreasuresPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
					GameInfo.setTreasuresPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
					GameInfo.setTreasuresPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			// set walls data
			case "421":	
				if (brokenCommand[1].equals("NUMBER")) {
					setWalls(Integer.parseInt(brokenCommand[2]));
					GameInfo.initWallsPos();
				}
				if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {
					int k = (Integer.parseInt(brokenCommand[2]));
					int nbrLastCoo = GameInfo.getWalls() - (k-1)*5;
					if (k/GameInfo.getWalls() == 1) {
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setWallsPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					GameInfo.setWallsPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
					GameInfo.setWallsPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
					GameInfo.setWallsPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
					GameInfo.setWallsPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
					GameInfo.setWallsPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			case "501":
				break;
			case "511":
				if (brokenCommand[2].equals("UPDATED")) {

				}
				else if (brokenCommand[2].equals("POS") && brokenCommand[5].equals("TRES")) {

				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			case "666":
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
		_com.sendMessage("110 CREATE "+name);
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

	public void help() {

	}
}