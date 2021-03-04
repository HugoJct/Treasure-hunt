package server;

import server.io.*;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {
	private Communication _com;

	public Console(Communication com) {
		this._com = com;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String command = "";
		while(ServerMain.isRunning()) {

			command = _com.getMessage();
			System.out.println(_com.getMessage());
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				
			}
			String[] brokenCommand = breakCommand(command);

			switch(brokenCommand[0]) {
				case "stop":
					ServerMain.stop();
					break;
				case "broadcast":
					//System.out.println("broadcast triggered");
					ServerMain.broadcastMessage(brokenCommand);
					break;
				case "listusers":
					//System.out.println("listusers triggered");
					ServerMain.printConnectedUsers();
					break;
				case "creategame":
					ServerMain.createGame(brokenCommand[1]);	//this creates a game with the args[1] as name
					break;
				case "stopgame":
					ServerMain.stopGame(Integer.parseInt(brokenCommand[1]));	//this stops the #args[1] game 
					break;
				case "listgames":		//this lists the existing games
					ServerMain.listGames();
					break;
				/* default:
					System.out.println("No command was recognized. Please try again."); */
			}
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		int spaceCount = 0;	
		for(int i=0;i<command.length();i++) {				//Count space number to determine 
			if(command.charAt(i) == ' ')
				spaceCount++;
		}

		String args[] = new String[spaceCount + 1];			//Create the array to return

		for(int i=0;i<args.length;i++)						//fill the array with voiod strings
			args[i] = "";

		int index = 0;
		for(int i=0;i<args.length;i++) {						//fill each slot of the array with a whole word
			for(int j=index;j<command.length();j++) {
				if(command.charAt(j) == ' ') {
					index++;
					break;
				}
				args[i] += command.charAt(j);
				index++;
			}
		}
		return args;
	}

}