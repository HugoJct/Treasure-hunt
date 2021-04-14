package client.connex;

import java.util.Scanner;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import client.Player;
import client.GameInfo;
import client.control.shell.Console;

public class Communication implements Runnable{

    Socket s;
    BufferedReader in;
    PrintStream out;
    Player p;
    String serverMsg = "";
    private static int lastMoveRequested = 0;

    public Communication(Player p) {
        this.p = p;
    	this.s = p.getSocket();
    	try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintStream(s.getOutputStream());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

	public void sendMessage(String message) {        //This method sends a message to the client handled by the instance of the class
        out.println(message);
        out.flush();
    }

    public String getServerMessage() {
    	return serverMsg;
    }

    public Player getPlayer() {
        return this.p;
    }

	@Override
    public void run() {
        try {
        	while(Player.isConnected()) {
            	serverMsg = in.readLine();
        		if(serverMsg == null) {       // if the socket is disconnected
                	System.out.println("Disconnected !");
                	Player.isConnected = false;
                	break;
            	}
            	System.out.println("Server wrote: "+ serverMsg);
                useMessage(serverMsg);
        	}
        } catch(IOException e) {
            System.out.println("Socket closed by the server.");
        }
    }

	public void useMessage(String command) {
        String[] brokenCommand = breakCommand(command);
		switch(brokenCommand[0]) {
			// Server -> Client
			case "152":
				System.out.println("Ready to Play ? y/N : ");
				Console.startRequested = true;
				break;
			case "153":
                try {
                    sendMessage("400 GETHOLES");
                    Thread.sleep(5);
                    sendMessage("410 GETTREASURES");
					Thread.sleep(5);
                    sendMessage("420 GETWALLS");
                }
                catch(InterruptedException e) {
                    
                }
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
				}/*
				else {
					System.out.println("Error : no move engaged");
				}*/
				break;
			case "202":
				System.out.println("Impossible movement, wall met");
				break;
			case "203":
				System.out.println("Treasure found: "+brokenCommand[4]+"$");
				break;
			// set holes data
			case "401":
				if (brokenCommand[1].equals("NUMBER")) {
					GameInfo.setHoles(Integer.parseInt(brokenCommand[2]));
					GameInfo.initHolesPos();
				} else if (brokenCommand[1].equals("MESS") && brokenCommand[3].equals("POS")) {
					int k = Integer.parseInt(brokenCommand[2]);
					if (k*5 > GameInfo.getHoles()) {
						int nbrLastCoo = GameInfo.getHoles() - (k-1)*5;
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setHolesPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					else {
						GameInfo.setHolesPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
						GameInfo.setHolesPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
						GameInfo.setHolesPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
						GameInfo.setHolesPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
						GameInfo.setHolesPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
					}
				}
				break;
			// set treasures data
			case "411":	
				if (brokenCommand[1].equals("NUMBER")) {
					GameInfo.setTreasures(Integer.parseInt(brokenCommand[2]));
					GameInfo.initTreasuresPos();
				} else if (brokenCommand[1].equals("MESS") && brokenCommand[3].equals("POS")) {
					int k = (Integer.parseInt(brokenCommand[2]));
					if (k*5 > GameInfo.getTreasures()) {
						int nbrLastCoo = GameInfo.getTreasures() - (k-1)*5;
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setTreasuresPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					else {
						GameInfo.setTreasuresPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
						GameInfo.setTreasuresPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
						GameInfo.setTreasuresPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
						GameInfo.setTreasuresPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
						GameInfo.setTreasuresPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
					}
				}
				break;
			// set walls data
			case "421":	
				if (brokenCommand[1].equals("NUMBER")) {
					GameInfo.setWalls(Integer.parseInt(brokenCommand[2]));
					GameInfo.initWallsPos();
				} else if (brokenCommand[1].equals("MESS") && brokenCommand[3].equals("POS")) {
					int k = (Integer.parseInt(brokenCommand[2]));
					if (k*5 > GameInfo.getWalls()) {
						int nbrLastCoo = GameInfo.getWalls() - (k-1)*5;
						for (int i = 0 ; i < nbrLastCoo ; i++) {
							GameInfo.setWallsPos(i+k, Integer.parseInt(brokenCommand[4+i]), Integer.parseInt(brokenCommand[5+i]));
						}
					}
					else {
						GameInfo.setWallsPos(0+k, Integer.parseInt(brokenCommand[4]), Integer.parseInt(brokenCommand[5]));
						GameInfo.setWallsPos(1+k, Integer.parseInt(brokenCommand[6]), Integer.parseInt(brokenCommand[7]));
						GameInfo.setWallsPos(2+k, Integer.parseInt(brokenCommand[8]), Integer.parseInt(brokenCommand[9]));
						GameInfo.setWallsPos(3+k, Integer.parseInt(brokenCommand[10]), Integer.parseInt(brokenCommand[11]));
						GameInfo.setWallsPos(4+k, Integer.parseInt(brokenCommand[12]), Integer.parseInt(brokenCommand[13]));
					}
				} 
				break;
			case "501":
				break;
			case "510":
				GameInfo.setPlayers(brokenCommand[1], Integer.parseInt(brokenCommand[3]), Integer.parseInt(brokenCommand[4]));
				break;
			case "511":
				if (brokenCommand[2].equals("UPDATED")) {

				}
				else if (brokenCommand[2].equals("POS") && brokenCommand[5].equals("TRES")) {

				}
				else {
					sendMessage("UNKNOWN");
				}
				break;
			case "666":
				System.out.println("U'R DEAD");
				break;
			default:
				break; 
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}