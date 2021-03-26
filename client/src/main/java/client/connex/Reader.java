package client.connex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Reader implements Runnable {
	BufferedReader in;
	Socket soc;
	private String _msg;

	public Reader(Socket soc) {
		this.soc = soc;
		this._msg = "";
		try {
			in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {		
		while (!soc.isClosed()) {
			try {
				this._msg = in.readLine();
				System.out.println("server wrote : " + this._msg);
			} catch (IOException e) {
				System.out.println("socket closed by the server.");
			}
		} 
	}

	public String getMsg() {
		return this._msg;
	}

}
