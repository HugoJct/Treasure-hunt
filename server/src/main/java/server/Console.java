package server;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String command = "";
		while(ServerMain.isRunning()) {

			command = sc.nextLine();
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
				default:
					System.out.println("No command was recognized. Please try again.");
			}
		}
	}

	private String[] breakCommand(String command) {
		int spaceCount = 0;
		for(int i=0;i<command.length();i++) {
			if(command.charAt(i) == ' ')
				spaceCount++;
		}

		String args[] = new String[spaceCount + 1];

		for(int i=0;i<args.length;i++)
			args[i] = "";

		int index = 0;
		for(int i=0;i<args.length;i++) {
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