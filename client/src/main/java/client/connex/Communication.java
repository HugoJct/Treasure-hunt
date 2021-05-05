package client.connex;

// import java Classes
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// import our Classes
import client.Player;
import client.GameInfo;
import client.control.shell.Console;


public class Communication implements Runnable{

    Socket s;
    BufferedReader in;
    PrintWriter out;
    Player p;
    String serverMsg = "";

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
				updateGameList(brokenCommand);

				int mapID = p.getGameId();
				int[][] games = GameInfo.getJoinableGames();
				for(int i=0;i<games.length;i++) {
					if(games[i][0] == mapID) {
						GameInfo.setMap(games[i][2],games[i][3]);
						break;
					}
				}
				break;
			case "131":
				sendMessage("120 GETLIST");
				break;
			case "152":
				System.out.println("Ready to Play ? y/N : ");
				Console.startRequested = true;
				break;
			case "153":
                sendMessage("400 GETHOLES");
                sendMessage("410 GETTREASURES");
                sendMessage("420 GETWALLS");
                GameInfo.setStarted(true);
				break;
			case "201":
				updatePlayerPos(brokenCommand);
				break;
			case "202":
				System.out.println("Impossible movement, wall met");
				break;
			case "203":
				GameInfo.addMoney(Integer.parseInt(brokenCommand[4]));
				System.out.println("Treasure found: "+brokenCommand[4]+"$, you now have "+GameInfo.getMoney()+"$");
				break;
			// set holes data
			case "401":
				setHolesData(brokenCommand);
				break;
			// set treasures data
			case "411":	
				setTreasureData(brokenCommand);
				break;
			// set walls data
			case "421":	
				setWallsData(brokenCommand); 
				break;
			case "500":
				if (brokenCommand[1].equals(Player.getName())) {
					GameInfo.setPlayable(true);
					System.out.println("Your turn");
					sendMessage("501 TURN UPDATED");
				} else {
					System.out.println(brokenCommand[1] + " turn");
				}
				break;
			case "510":
				GameInfo.setPlayers(brokenCommand[1], Integer.parseInt(brokenCommand[3]), Integer.parseInt(brokenCommand[4]));
				GameInfo.printGameBoard();
				break;
			case "511":
				if (brokenCommand[2].equals("UPDATED")) {

				}
				else if (brokenCommand[2].equals("POS") && brokenCommand[5].equals("TRES")) {
					GameInfo.setTreasures(GameInfo.getTreasures()-1);
					GameInfo.removeTreasure(Integer.parseInt(brokenCommand[3]), Integer.parseInt(brokenCommand[4]));
					GameInfo.setPlayers(brokenCommand[1], Integer.parseInt(brokenCommand[3]), Integer.parseInt(brokenCommand[4]));
					sendMessage("512 " + Player.getName() + " UPDATED");
				}
				else {
					sendMessage("UNKNOWN");
				}
				GameInfo.printGameBoard();
				break;
			case "520":
				GameInfo.removePlayer(brokenCommand[1]);
				sendMessage("521 " + Player.getName() + " UPDATED");
				break;
			case "600":
				System.out.println("Everyone is dead : GAME OVER");
				System.exit(0);
			case "666":
				GameInfo.setLifeState(true);
				GameInfo.resetMoney();
				System.out.println("U'R DEAD");
				break;
			case "902":
				System.out.println("Not your round");
			default:
				break; 
		}
	}

	public void setHolesData(String args[]) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setHoles(Integer.parseInt(args[2]));
			GameInfo.initHolesPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getHoles() * 2);i++) {
				if(GameInfo.getHolesPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setHolesPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]));
				i++;
				fill += 2;
			}
		}
	}

	public void setTreasureData(String args[]) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setTreasures(Integer.parseInt(args[2]));
			GameInfo.initTreasuresPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getTreasures() * 3);i++) {
				if(GameInfo.getTreasuresPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setTreasuresPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]),Integer.parseInt(args[i+2]));
				i+=2;
				fill += 3;
			}
		}
	}

	public void setWallsData(String args[]) {
		if (args[1].equals("NUMBER")) {
			GameInfo.setWalls(Integer.parseInt(args[2]));
			GameInfo.initWallsPos();
		} else if (args[1].equals("MESS") && args[3].equals("POS")) {
			int fill = 0;
			for(int i=0;i<(GameInfo.getWalls() * 3);i++) {
				if(GameInfo.getWallsPos()[i] == 0) {
					fill = (i == 0 ? 0 : i);
					break;
				}
			}
			for(int i=4;i<args.length;i++) {
				GameInfo.setWallsPos(fill,Integer.parseInt(args[i]),Integer.parseInt(args[i+1]));
				i++;
				fill += 2;
			}
		}
	}

	public void updateGameList(String args[]) {
		if(args[1].equals("NUMBER")) {
			GameInfo.setGameNumber(Integer.parseInt(args[2]));
		} else if(args[1].equals("MESS") && args[3].equals("ID")) {
			int[] tab = new int[6];
			for(int i=0;i<6;i++)
				tab[i] = Integer.parseInt(args[4+i]);
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
	}

	public void updatePlayerPos(String args[]) {
		int last = Console.getLastMove();
		int[] pos = GameInfo.getPlayerPos(p.getName());
		int j=0;
		if (last == 1) {
			System.out.println("MOVE OK : UP");
			pos[1]--;
		}
		else if (last == 2) {
			System.out.println("MOVE OK : DOWN");
			pos[1]++;
		}
		else if (last == 3) {
			System.out.println("MOVE OK : RIGHT");
			pos[0]++;
		}
		else if (last == 4) {
			System.out.println("MOVE OK : LEFT");
			pos[0]--;
		}
		else {
			System.out.println("Error : no move engaged");
		}
		GameInfo.setPlayerPos(pos[2],pos[0],pos[1]);
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}
