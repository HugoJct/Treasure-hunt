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
				break;
			case "202":
				break;
			case "203":
				break;
			case "401":	// set holes data
				if (brokenCommand[1].equals("NUMBER")) {
					setHoles((int)brokenCommand[2]);
					GameInfo.initHolesPos();
				}
				else if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {
					int k = ((int)brokenCommand[2]);
					int nbrLastCoo = GameInfo.getHoles() - (k-1)*5;
					if (k/GameInfo.getHoles() == 1) {
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setHolesPos(i+k, ((int)brokenCommand[4+i]), ((int)brokenCommand[5+i]));
						}
					}
					GameInfo.setHolesPos(0+k, ((int)brokenCommand[4]), ((int)brokenCommand[5]));
					GameInfo.setHolesPos(1+k, ((int)brokenCommand[6]), ((int)brokenCommand[7]));
					GameInfo.setHolesPos(2+k, ((int)brokenCommand[8]), ((int)brokenCommand[9]));
					GameInfo.setHolesPos(3+k, ((int)brokenCommand[10]), ((int)brokenCommand[11]));
					GameInfo.setHolesPos(4+k, ((int)brokenCommand[12]), ((int)brokenCommand[13]));
				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			case "411":	// set treasures data
				if (brokenCommand[1].equals("NUMER")) {
					setTreasures((int)brokenCommand[2]);
					GameInfo.initTreasuresPos();
				}
				else if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {
					int k = ((int)brokenCommand[2]);
					int nbrLastCoo = GameInfo.getTreasures() - (k-1)*5;
					if (k/GameInfo.getTreasures() == 1) {
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setTreasuresPos(i+k, ((int)brokenCommand[4+i]), ((int)brokenCommand[5+i]));
						}
					}
					GameInfo.setTreasuresPos(0+k, ((int)brokenCommand[4]), ((int)brokenCommand[5]));
					GameInfo.setTreasuresPos(1+k, ((int)brokenCommand[6]), ((int)brokenCommand[7]));
					GameInfo.setTreasuresPos(2+k, ((int)brokenCommand[8]), ((int)brokenCommand[9]));
					GameInfo.setTreasuresPos(3+k, ((int)brokenCommand[10]), ((int)brokenCommand[11]));
					GameInfo.setTreasuresPos(4+k, ((int)brokenCommand[12]), ((int)brokenCommand[13]));
				}
				else {
					_com.sendMessage("UNKNOW");
				}
				break;
			case "421":	// set walls data
				if (brokenCommand[1].equals("NUMBER")) {
					setWalls((int)brokenCommand[2]);
					GameInfo.initWallsPos();
				}
				else if (brokenCommand[1].equals("MESS") && brokenCommand[3] == "POS") {

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
		_com.sendMessage("130 JOIN "+id);
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
				_com.sendMessage("200 GOUP");
				break;
			case "DOWN":
				_com.sendMessage("200 GODOWN");
				break;
			case "LEFT":
				_com.sendMessage("200 GOLEFT");
				break;
			case "RIGHT":
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