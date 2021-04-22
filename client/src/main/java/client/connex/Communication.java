package client.connex;

import java.util.Scanner;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import client.Player;
import client.GameInfo;
import client.control.shell.Console;

import java.util.Arrays;

public class Communication implements Runnable{

    Socket s;
    BufferedReader in;
    PrintWriter out;
    Player p;
    String serverMsg = "";
    private static int lastMoveRequested = 0;

    public Communication(Player p) {
        this.p = p;
    	this.s = p.getSocket();
    	try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
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
			case "121":
				if(brokenCommand[1].equals("NUMBER")) {
					GameInfo.setGameNumber(Integer.parseInt(brokenCommand[2]));
				} else if(brokenCommand[1].equals("MESS") && brokenCommand[3].equals("ID")) {
					int[] tab = new int[6];
					for(int i=0;i<6;i++)
						tab[i] = Integer.parseInt(brokenCommand[4+i]);
					int games[][] = GameInfo.getJoinableGames();
					for(int i=0;i<games.length;i++) {
						boolean insert = true;
						for(int j=0;j<games[i].length;j++) {
							if(games[i][j] != 0) {
								insert = false;
								break;
							}
						}
						if(insert) {
							GameInfo.setGamePos(i,tab);
							break;
						}
					}
				}
				break;
			case "152":
				System.out.println("Ready to Play ? y/N : ");
				Console.startRequested = true;
				break;
			case "153":
                    sendMessage("400 GETHOLES");
                    sendMessage("410 GETTREASURES");
                    sendMessage("420 GETWALLS");
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
					int fill = 0;
					for(int i=0;i<(GameInfo.getHoles() * 2);i++) {
						if(GameInfo.getHolesPos()[i] == 0) {
							fill = (i == 0 ? 0 : i);
							break;
						}
					}
					for(int i=4;i<brokenCommand.length;i++) {
						GameInfo.setHolesPos(fill,Integer.parseInt(brokenCommand[i]),Integer.parseInt(brokenCommand[i+1]));
						i++;
						fill += 2;
					}
				}
				break;
			// set treasures data
			case "411":	
				if (brokenCommand[1].equals("NUMBER")) {
					GameInfo.setTreasures(Integer.parseInt(brokenCommand[2]));
					GameInfo.initTreasuresPos();
				} else if (brokenCommand[1].equals("MESS") && brokenCommand[3].equals("POS")) {
					int fill = 0;
					for(int i=0;i<(GameInfo.getTreasures() * 3);i++) {
						if(GameInfo.getTreasuresPos()[i] == 0) {
							fill = (i == 0 ? 0 : i);
							break;
						}
					}
					for(int i=4;i<brokenCommand.length;i++) {
						GameInfo.setTreasuresPos(fill,Integer.parseInt(brokenCommand[i]),Integer.parseInt(brokenCommand[i+1]),Integer.parseInt(brokenCommand[i+2]));
						i+=2;
						fill += 3;
					}
				}
				break;
			// set walls data
			case "421":	
				if (brokenCommand[1].equals("NUMBER")) {
					GameInfo.setWalls(Integer.parseInt(brokenCommand[2]));
					GameInfo.initWallsPos();
				} else if (brokenCommand[1].equals("MESS") && brokenCommand[3].equals("POS")) {
					int fill = 0;
					for(int i=0;i<(GameInfo.getWalls() * 3);i++) {
						if(GameInfo.getWallsPos()[i] == 0) {
							fill = (i == 0 ? 0 : i);
							break;
						}
					}
					for(int i=4;i<brokenCommand.length;i++) {
						GameInfo.setWallsPos(fill,Integer.parseInt(brokenCommand[i]),Integer.parseInt(brokenCommand[i+1]));
						i++;
						fill += 2;
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
				GameInfo.setLifeState(true);
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