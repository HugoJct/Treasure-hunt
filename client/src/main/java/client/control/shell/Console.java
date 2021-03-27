package client.control.shell;

import client.Player;
import client.connex.Communication;

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
			case "GETLIST":
				listGames();
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
			case "152":
				_com.sendMessage("152 1");
				break;
			default:
				System.out.println("No command was recognized");;
				break; 
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

	public void sendName() {		//This method sends the player's name to the server when the connection occurs
		_com.sendMessage("100 HELLO PLAYER "+ Player.getName());
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

}