package client.connex;

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
			out.println("Hugo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String msg;
		while(!soc.isClosed()) {
			msg = sc.nextLine();
			out.println(msg);
			out.flush();
		}
	}

}
