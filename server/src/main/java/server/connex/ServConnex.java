package server.connex;

import server.ServerMain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ServConnex implements Runnable{

	private final String name;
	private final BufferedReader in;
	private final PrintStream out;	
	public boolean isConnected;
	Socket s;

	public ServConnex(Socket s, String name, BufferedReader in, PrintStream out) {
		this.s = s;
		this.in = in;
		this.out = out;
		this.name = name;
		this.isConnected = true;
		sendMessage("Connected !");
	}

	public String getName() {
		return name;
	}

	public boolean sendMessage(String message) {
		out.println(message);
		out.flush();
		return true;
	}

	@Override
	public void run() {
		String msg = "";
		while(msg != null) {
			try {
				msg = in.readLine();
				if(msg == null) {
					System.out.println(this.name+" disconnected !");
					break;
				}
				System.out.println(this.name+" wrote: "+msg);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		this.isConnected = false;
	}
}
