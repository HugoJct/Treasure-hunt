package client;

import client.connex.Communication;
import client.control.shell.Console;
import client.view.gui.*;
import client.control.UI.ControlUI;

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

public class Player {

    private static String username;
    private static File configFile;
    private static String serverIP;
    private static int serverPort;
    private static Player p;
    private static FileReader reader;
    private static Socket s;

    public static boolean isConnected = true;

    public Player(String pathToConfigFile) {        
        try {

            configFile = new File(pathToConfigFile);
            if(configFile.exists()) {
                reader = new FileReader(configFile);
            } else {
                configFile = new File("src/main/java/client/config.json");
                reader = new FileReader(configFile);
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            this.username = (String) jsonObject.get("name");
            this.serverIP = (String) jsonObject.get("ip");
            this.serverPort = ((Long) jsonObject.get("port")).intValue();
        }  catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return s;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static String getName() {
        return username;
    }

    public static void main(String[] args) {

		//new ControlUI(new Menu());
        try {
            if(args.length > 0)
                p = new Player(args[0]);
            else 
                p = new Player("");

            s = new Socket(serverIP,serverPort);

            Communication com = new Communication(p);
            Console cons = new Console(com);

            Thread communication = new Thread(com);
            Thread console = new Thread(cons);

            communication.start();
            console.start();

        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
