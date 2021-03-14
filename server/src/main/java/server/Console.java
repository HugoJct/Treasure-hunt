package server;

import server.io.*;

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
			case "0":
				ServerMain.stop();
				break;
			case "broadcast":
				ServerMain.broadcastMessage(brokenCommand);
				break;
			case "listusers":
				_com.sendMessage(ServerMain.printConnectedUsers()); 
				break;
			case "110":	// create a new game (args[1] as name)
				ServerMain.createGame(brokenCommand[1]);
				_com.sendMessage(ServerMain.listGames());
				break;
			case "stopgame":
				ServerMain.stopGame(Integer.parseInt(brokenCommand[1]));	//this stops the #args[1] game (doesn't work)
				break;
			case "120":	// list of existing games
				_com.sendMessage(ServerMain.listGames());
				break;
			default:
				System.out.println("No command was recognized. Please try again.");
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