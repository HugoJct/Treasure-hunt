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
         *
         */

        commandList.put("120",new CommandSendGameInfo(out));
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
                useMessage(this._msg);
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
            }
        }
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

            case "110":
                /**
                 * process to create a game
                 */
                createAGameProcess(g, p, brokenCommand);
                break;

            case "130":
                /**
                 * process to join a created game
                 */
                joinAGameProcess(g, p, brokenCommand);
                break;

            case "150":
                /**
                 * process to launch the game and check if everyone is ready
                 */
                startRequestProcess(g, p);
                break;

            case "152":
                /**
                 * check the response to resquest start (case 153)
                 */
                if(brokenCommand[2].equals("YES")) {
                    p.setReady(true);
                } else {
                    p.setReady(false);
                }
                p.setAnswered(true);
                break;

            case "200":
                /**
                 * process the movement of a player on game board
                 */
                playerMouvementProcess(g, p, brokenCommand);
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

    public void createAGameProcess(Game g, Player p, String[] brokenCommand) {
        /**
         * map creation (gamemod, map size, nbr of hole & treasures)
         * creation confirmation response with map id
         */
        int id = ServerMain.createGame(Integer.parseInt(brokenCommand[2]),Integer.parseInt(brokenCommand[4]),Integer.parseInt(brokenCommand[5]),Integer.parseInt(brokenCommand[7]),Integer.parseInt(brokenCommand[9]),p.getPlayerId());
        sendMessage("111 MAP CREATED " + id);
        String s = ""+id;
        String infos[] = {"130","JOIN",s,p.getName()};
        if(ServerMain.joinGame(infos)) {
            sendMessage("131 MAP "+p.getGameId()+" JOINED");
            broadcastInGame("The player "+p.getName()+" joined the game",p.getGameId());
        }
    } 



    public void joinAGameProcess(Game g, Player p, String[] brokenCommand) {
        /**
         * to join a game with the id
         * if the given id exist : 
         *  join the associate game
         *  send confirmation message to the player (131 command) with the game id
         *  broadcast to every players in game 140 command with the new player name
         * else we send to the sender that the specified game doesn't exist
         */
        if(ServerMain.joinGame(brokenCommand)) {                          
            sendMessage("131 MAP "+p.getGameId()+" JOINED");
            broadcastInGame("140 "+p.getName()+" JOINED",p.getGameId());                
        } else {
            sendMessage("No such game found");
        }
    }



    public boolean playerMouvementProcess(Game g, Player p, String[] brokenCommand) {
        /**
         * try to move the curent player
         * check if there is something on the new position
         * if yes :
         *  aboard the movement if it's a wall
         *  kill the player if it's a hole
         *  reward the player if it's a treasure and move it on the new coordonates
         * if not :
         *  move the player on the new coordonates
         * send the new game state to the client
         */
        int[] pos = p.getPos();
        if (p.getPlayerId() != g.getPlayerRound() && g.getGameMod() != 0) {
            sendMessage("902 NOT YOUR TURN");
            return false;
        } else {
            switch(brokenCommand[1]) {
                case "GOUP":
                    pos[0]--;
                    break;
                case "GODOWN":
                    pos[0]++;
                    break;
                case "GOLEFT":
                    pos[1]--;
                    break;
                case "GORIGHT":
                    pos[1]++;
                    break;
                default:
                    break;
            }
            String ret = p.setPos(g.getBoard(),pos);
            pos = p.getPos();
            if(ret.equals("ok")) {
    
                sendMessage("201 MOVE OK");
                broadcastInGame("510 "+p.getName()+" POS "+pos[1]+" "+pos[0],g.getGameId());
    
            } else if(ret.equals("Wall")) {
    
                sendMessage("202 MOVE BLOCKED");
                broadcastInGame("510 "+p.getName()+" POS "+pos[1]+" "+pos[0],g.getGameId());
    
            } else if(ret.equals("Treasure")) {
    
                Treasure tr = (Treasure) g.getBoard().getElementAt(pos[1],pos[0]);
    
                sendMessage("203 MOVE OK TRES "+tr.getTreasureValue());
                broadcastInGame("511 "+p.getName()+" POS "+pos[1]+" "+pos[0]+" TRES "+tr.getTreasureValue(),g.getGameId());     //missing treasure value
    
                tr.setTreasureValue(0);
                g.getBoard().setElementAt(null,pos[1],pos[0]);
                if (g.getBoard().getTreasureCount() == 0) {
                    broadcastInGame("530 " + g.leadingPlayer().getName() + " WINS", g.getGameId());
                }
    
            } else if(ret.equals("Hole")) {
                sendMessage("666 MOVE HOLE DEAD");
                broadcastInGame("520 "+p.getName()+" DIED",g.getGameId());
                p.setMoney(0);
                p.killPlayer();
                if (ServerMain.everyoneIsDead(g)) {
                    broadcastInGame("600 GAME OVER", g.getGameId());
                }
            }
        }
        return true;
    }


    public void startRequestProcess(Game g, Player p) {
        /**
         * when a player ask to launch the game : we check if this player is the owner and if yes 
         *  we broadcast to every players connected if they are ready to play
         *  if they are, we launch the game and broadcast the confirmation to everyone
         *  if they aren't, we aboard and broadcast it to everyone
         */
        if(g.getOwnerID() == p.getPlayerId()) {
            int[] broadcast = {152, p.getGameId()};
            for(Player p2 : g.getPlayers()) {
                p2.setAnswered(false);
            }
            ServerMain.broadcastPerGame(broadcast);

            Thread t = new Thread() {
                public void run() {
                    if(ServerMain.checkForLaunch(g.getGameId())) {
                        ServerMain.launchGame(g.getGameId());
                        broadcastInGame("153 GAME STARTED",g.getGameId());
                        for(Player p : g.getPlayers()) {
                            while(p.getPos()[0] == 0 & p.getPos()[1] == 0) {
                                System.out.print("");
                            }
                            broadcastInGame("510 "+p.getName()+" POS "+p.getPos()[1]+" "+p.getPos()[0],g.getGameId());
                        }
                    } else {
                        String playersNotReady = "";
                        for(Player pl : g.getPlayers()) {
                            if(!pl.getReady())
                                playersNotReady += pl.getName() + " ";
                        }
                        String[] playersNotReadyTab = breakCommand(playersNotReady);
                        broadcastInGame("154 START ABORTED "+playersNotReadyTab.length,g.getGameId());
                        for(int i=0;i<playersNotReadyTab.length;i++) {
                            String req = "154 MESS "+(i+1)+" PLAYER ";

                            for(int j=0; j<5;j++) {
                                req += playersNotReadyTab[i]+" ";
                                i++;
                                if(i == playersNotReadyTab.length)
                                    break;
                            }

                            broadcastInGame(req,g.getGameId());
                        }
                    }
                }
            };
            t.start();
        } else {
            sendMessage("You do not have permission to request start");
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
