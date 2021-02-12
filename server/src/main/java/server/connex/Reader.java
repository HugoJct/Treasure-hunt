package server.connex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Reader implements Runnable {
	BufferedReader in;
	Socket soc;

	public Reader(Socket soc) {
		this.soc = soc;
		try {
			in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String msg;
		try {
			msg = in.readLine();
			while (msg != null) {
				System.out.println(msg);
				msg = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
