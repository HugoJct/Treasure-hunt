package server.io;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import server.playingProps.Player;
import server.playingProps.Game;

import server.ServerMain;

import server.elements.Treasure;
import server.elements.Element;

import server.commands.*;

public class Communication implements Runnable {
    private String _msg;
    private Player _player;
    private String username;
    boolean isConnected;
    Socket s;
    BufferedReader in;
    PrintWriter out;

    private HashMap<String,Command> commandList = new HashMap<String,Command>();

    public Communication(Player p) {
        this._player = p;
        this.s = p.getSocket();
        this._msg = "";
        this.username = p.getName();
        
        /**
         * construction of the communication with the client
         * in for the buffer which read inputs from the client
         * out for the writer which will send requests to the client
         */

        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream());
        } catch( IOException e) {
            e.printStackTrace();
        }

        /*
         *  Filling the hashmap that will be used to recognize the commands
         */

        commandList.put("110",new CommandCreateGame(out));
        commandList.put("120",new CommandSendGameInfo(out));
        commandList.put("130",new CommandJoinGame(out));
        commandList.put("150",new CommandRequestStart(out));
        commandList.put("152",new CommandRequestStartResponse(out));
        commandList.put("200",new CommandPlayerMovement(out));
        commandList.put("400",new CommandSendHolesInfos(out));
        commandList.put("410",new CommandSendTreasuresInfos(out));
        commandList.put("420",new CommandSendWallsInfos(out));
    }

    @Override
    public void run() {

        /**
         * as long as the remote socket is connected :
         *  read the input
         *  print it
         *  print if user is disconected
         */

        while(ServerMain.isRunning()) {
            try {
                this._msg = in.readLine(); 
                if(!ServerMain.isRunning() || this._msg == null) {
                    System.out.println(this.username+" disconnected !");
                    break;
                }
                System.out.println(this.username+" wrote: "+ this._msg);
                //useMessage(this._msg);
                cleanUseMessage(_msg);
                
            } catch(IOException e) {
                System.out.println(this.username + (": socket closed by the server."));
                ServerMain.stop();
            }
        }
        this.isConnected = false;   //update status
    }

    private void cleanUseMessage(String command) {

        String[] brokenCommand = breakCommand(command);
        Game g = getPlayer().getGameConnectedTo();
        Player p = getPlayer();

        for(String s : commandList.keySet()) {
            if(s.equals(brokenCommand[0])) {
                commandList.get(s).execute(g,p,brokenCommand);
                return;
            }
        }
        System.out.println("Unrecognized command");
    }

    public void useMessage(String command) {

        /**
         * definition of important vars for commands treatment, as :
         * brokenCommand (tab of String with each command arguments)
         * g (a Game type with the current game)
         * p (a Player type with the current player) 
         */

        String[] brokenCommand = breakCommand(command);
        Game g = getPlayer().getGameConnectedTo();
        Player p = getPlayer();


        switch(brokenCommand[0]) {

            case "0":                                               //STOP
                ServerMain.stop();
                break;

            case "512":
                System.out.println(p.getName() + " : position updated CONFIRMATION");
                roundManager(g, p);
                break;

            case "501":
                /**
                 * WIP : TODO
                 */
                System.out.println("confirmation");
                break;
            case "521":
                System.out.println(p.getName() + " : player state updated CONFIRMATION");
                roundManager(g, p);
                break;
            default:
                sendMessage("999 COMMAND ERROR");
                break;
        }
    }

    public void roundManager(Game g, Player p) {
        Player[] playerList = ServerMain.getPlayersInGame(g.getGameId());
        int checkForRound = 0;
        if (g.getGameMod() != 0) {
            if (g.getConfirmations() == null) {
                g.setConfirmations(new boolean[playerList.length]);
            }
            if (g.getPlayerRound() == -1) {
                g.setPlayerRound(playerList[0].getPlayerId());
            }
            for (int i = 0 ; i < playerList.length ; i++) {
                if (g.getConfirmations()[i] == false) {
                    g.setConfirmations(i);
                    break;
                }
            }
            for (int i = 0 ; i<g.getConfirmations().length ; i++) {
                if (g.getConfirmations()[i] == false) {
                    break;
                } else {
                    checkForRound++;
                }
            }
            System.out.println(checkForRound);
            System.out.println(playerList.length);
            if (checkForRound == playerList.length) {
                Player playerToBroadcast = null;
                for (int i = 0 ; i<playerList.length ; i++) {
                    if (g.getPlayerRound() == playerList[i].getPlayerId()) {
                        for (int j = i ; 1 == 1; j++) {
                            if (j == playerList.length-1) {
                                j = -1;
                            }
                            if (playerList[j+1].isPlayerDead() == false) {
                                g.setPlayerRound(playerList[j+1].getPlayerId());
                                playerToBroadcast = playerList[j+1];
                                System.out.println("round : " + g.getPlayerRound());
                                break;
                            }
                            if (ServerMain.everyoneIsDead(g)) {
                                broadcastInGame("600 GAME OVER", g.getGameId());
                                break;
                            }
                        }
                        break;
                    }
                }
                broadcastInGame("500 " + playerToBroadcast.getName() + " TURN", g.getGameId());
                g.setConfirmations(new boolean[playerList.length]);
            }
        }
    }

    private String[] breakCommand(String command) {         //This method breaks the command which arguments are separated by spaces
        String delims = "[ ]+";     //This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
                                    //the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
        String[] args = command.split(delims);
        return args;
    }

    public void broadcastInGame(String message, int gameID) {       //send a message to all players in a specific game
        for(Communication c : ServerMain.launchedCom) {
            if(c.getPlayer().getGameId() == gameID) {
                c.sendMessage(message);
            }
        }
    }

    public boolean sendMessage(String message) {        //This method sends a message to the client handled by the instance of the class
        out.println(message);
        out.flush();
        if(out.checkError()) {
            return false;
        }
        return true;
    }
    


    public void stop() {
        try {
            this.sendMessage("Server Closed.");
            this.s.close();                                                                 //This line closes the socket s which unblocks the execution of run()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getMessage() {
        return this._msg;
    }
    public Player getPlayer() {
        return this._player;
    }
    public String getName() {
        return this._player.getName();
    }

}
