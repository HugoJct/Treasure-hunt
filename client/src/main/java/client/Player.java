package client;

// import java Classes
import java.net.Socket;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

// import our Classes
import client.connex.Communication;
import client.control.shell.Console;
import client.view.graphicDisplay.GameSelectionDisplay;
import client.control.UIBis.GameManager;
import server.elements.*;



public class Player {

    private static String username;
    private static File configFile;
    private static String serverIP;
    private static int serverPort;
    private static Player p;
    private static FileReader reader;
    private static Socket s;
    private static int gameID;

    public static boolean isConnected = true;
    
    public Player(String name) {
        try {
            configFile = new File("src/main/java/client/config.json");
            reader = new FileReader(configFile); 
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            this.username = name;
            this.serverIP = (String) jsonObject.get("ip");
            this.serverPort = ((Long) jsonObject.get("port")).intValue();    
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return s;
    }

    public int getGameId() {
        return gameID;
    }

    public void setGameId(int id) {
        gameID = id;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static String getName() {
        return username;
    }

    public static void printGameBoard() {
        char[][] board = new char[GameInfo.getMap()[0]][GameInfo.getMap()[1]];
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board[i].length;j++)
                board[i][j] = '.';

        for(Wall w : GameInfo.getWalls()) {
            int x = w.getX();
            int y = w.getY();
            board[x][y] = 'W';
        }
        for(Treasure t : GameInfo.getTreasures()) {
            int x = t.getX();
            int y = t.getY();
            board[x][y] = 'T';
        }
        for(Hole h : GameInfo.getHoles()) {
            int x = h.getX();
            int y = h.getY();
            board[x][y] = 'H';
        }

        for(int i=0;i<GameInfo.getPlayersNames().length;i++) {
            int x = GameInfo.getPlayerPos(GameInfo.getPlayersNames()[i])[1];
            int y = GameInfo.getPlayerPos(GameInfo.getPlayersNames()[i])[0];
            board[x][y] = GameInfo.getPlayersNames()[i].toLowerCase().charAt(0);
        }

        for(int i=0;i<board.length;i++){
            System.out.print("W ");
        }
        System.out.println();

        for(int i=1;i<board.length-1;i++) {
            System.out.print("W ");
            for(int j=1;j<board[i].length-1;j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.print("W");
            System.out.println();
        }

        for(int i=0;i<board.length;i++){
            System.out.print("W ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        try {
            if (args.length > 1) {
                p = new Player(args[1]);
            } else { 
                p = new Player("Unnamed_User");
            }
            s = new Socket(serverIP,serverPort);

            Communication com = new Communication(p);
            Console cons = new Console(com);

            Thread communication = new Thread(com);
            Thread console = new Thread(cons);

            communication.start();
            console.start();

            new GameManager(new GameSelectionDisplay(), com.getOutput(), p);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
