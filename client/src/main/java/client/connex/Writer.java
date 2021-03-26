package client.connex;

import client.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Writer implements Runnable{
	PrintStream out;
	Socket soc;
	Scanner sc = new Scanner(System.in);
	
	public Writer(Socket soc) {
		this.soc = soc;
		try {
			out = new PrintStream(soc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sendName();
		String msg;
		while(!soc.isClosed()) {
			msg = sc.nextLine();
			out.println(msg);
			out.flush();
		}

	}

	public void sendMessage(String msg) {
		out.println(msg);
		out.flush();		
	}

	public void sendName() {		//This method sends the player's name to the server when the connection occurs
		out.println("100 HELLO PLAYER "+ Player.getName());
		out.flush();
	}

	public Socket getSocket() {
		return this.soc;
	}

}
