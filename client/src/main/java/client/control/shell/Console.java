package client.control.shell;

// import java Classes
import java.util.Scanner;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.awt.*;

// import our Classes
import client.Player;
import client.connex.Communication;
import client.GameInfo;
import client.commands.Command;
import client.commands.userIn.*;
import server.elements.*;


public class Console implements Runnable {

	private Communication _com;
	private String _message;
	private static int lastMoveRequested = 0;
	public static boolean startRequested = false;

	private HashMap<String,Command> commandList = new HashMap<String,Command>();

	public Console(Communication com) {
		this._message = "";
		this._com = com;
		sendName();

		commandList.put("CREATEGAME",new CommandCreateGame(_com.getOutput()));
		commandList.put("JOIN",new CommandJoinGame(_com.getOutput()));
		commandList.put("GETLIST",new CommandGetList(_com.getOutput()));
		commandList.put("MOVE",new CommandMove(_com.getOutput()));
		commandList.put("REQUESTSTART",new CommandRequestStart(_com.getOutput()));
		commandList.put("y",new CommandRequestStartSayYes(_com.getOutput()));
		commandList.put("n",new CommandRequestStartSayNo(_com.getOutput()));
		commandList.put("STOP",new CommandStopServer(_com.getOutput()));

	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(Player.isConnected()) {	
			try {
				Thread.sleep(1);
				String input = sc.nextLine();
				useMessageBetter(input);
				useMessage(input);
			}
			catch(InterruptedException e) {

			}	
		}	
	}
					
	private void useMessageBetter(String command) {
		String[] args = breakCommand(command);
		for(String s : commandList.keySet()) {
			if(s.equals(args[0])) {
				if((commandList.get(s) instanceof CommandRequestStartSayYes || commandList.get(s) instanceof CommandRequestStartSayNo) && !startRequested) {
					break;
				}
				commandList.get(s).execute(_com.getPlayer(),args);
				return;
			}
		}
        System.out.println("Unknown Command");
	}

	public void useMessage(String command) {
		String[] brokenCommand = breakCommand(command);
		switch(brokenCommand[0]) {

				/*
				 *	All the foollowing commands serve a debug purpose only
				 */

			/*case "GETHOLES":
				getHoles();
				break;
			case "GETWALLS":
				getWalls();
				break;
			case "GETTREASURES":
				getTreasures();
				break;
			case "PRINTTREASURES":
				for(Treasure t : GameInfo.getTreasures()) {
					System.out.println(t);
				}
				break;
			case "PRINTWALLS":
				for(Wall w : GameInfo.getWalls()) {
					System.out.println(w);
				}
				break;
			case "PRINTHOLES":
				for(Hole h : GameInfo.getHoles()) {
					System.out.println(h);
				}
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
				break;*/
			case "EXIT":
				System.exit(0);
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

	public void getHoles() {
		_com.sendMessage("400 GETHOLES");
	}

	public void getWalls() {
		_com.sendMessage("420 GETWALLS");
	}

	public void getTreasures() {
		_com.sendMessage("410 GETTREASURES");
	}

	public boolean getStartRequested() {
		return startRequested;
	}

	public static int getLastMove() {
		return lastMoveRequested;
	}

	public static void setLastMoveRequested(int i) {
		lastMoveRequested = i;
	}

	public static void setStartRequested(boolean b) {
		startRequested = b;
	}
}
