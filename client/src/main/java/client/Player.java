package client;

import client.connex.Writer;
import client.connex.Reader;
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

    public Player(String pathToConfigFile) {        
        try {
            configFile = new File(pathToConfigFile);
            FileReader reader = new FileReader(configFile);

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

    public Player() {               //If the user did not specify any configuration file, we load the default one
        try {
            configFile = new File("src/main/java/client/config.json");
            FileReader reader = new FileReader(configFile);

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

    public static String getName() {
        return username;
    }

    public static void main(String[] args) {

		new ControlUI(new Menu());
        try {

            if(args.length >= 1)
                p = new Player(args[0]);
            else
                p = new Player();

            Socket s = new Socket(serverIP,serverPort);
            Thread read = new Thread(new Reader(s));
            Thread write = new Thread(new Writer(s));

            write.start();
            read.start();

        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
