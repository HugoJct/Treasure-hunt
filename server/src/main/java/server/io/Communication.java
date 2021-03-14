package server.io;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.Player;
import server.ServerMain;

public class Communication implements Runnable {
    private String _msg;
    private Player _player;
    private String username;
    boolean isConnected;
    Socket s;
    BufferedReader in;
    PrintStream out;

    public Communication(Player p) {
        this._player = p;
        this.s = p.getSocket();
        this._msg = "";
        this.username = p.getName();
        
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintStream(s.getOutputStream());
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMessage(String message) {        //This method sends a message to the client handled by the instance of the class
        out.println(message);
        out.flush();
        return true;
    }

    public String getMessage() {
        return this._msg;
    }

    @Override
    public void run() {

        while(ServerMain.isRunning()) {    //As long as the remote socket is connected
            try {
                this._msg = in.readLine();    //read the input
               if(!ServerMain.isRunning() || this._msg == null) {       // if the socket is disconnected
                    System.out.println(this.username+" disconnected !");    //inform the user
                    break;                                              //break out of the loop
                }
                System.out.println(this.username+" wrote: "+ this._msg);   //print the message
                
            } catch(IOException e) {
                System.out.println(this.username + (": socket closed by the server."));
            }
        }
        this.isConnected = false;   //update status
    }

    public void stop() {
        try {
            this.sendMessage("Server Closed.");
            this.s.close();                                                                 //This line closes the socket s which unblocks the execution of run()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this._player.getName();
    }
}
