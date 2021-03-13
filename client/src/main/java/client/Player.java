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
import java.io.IOException;
import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Player {

    private static String username;
    private static FileReader reader;
    private static String serverIP;
    private static int serverPort;
    private static Player p;

    public Player(String pathToConfigFile) {
        try {
            reader = new FileReader(pathToConfigFile);

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

    public Player() {
        try {
            reader = new FileReader("src/main/java/client/config.json");

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
