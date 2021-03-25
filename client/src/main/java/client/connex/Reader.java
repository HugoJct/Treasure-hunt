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
		try {
			in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String tmp = "";
		try {
			while (true) {
				_msg = in.readLine();
				if (tmp != _msg) {
					System.out.println("Server wrote: " + _msg);			
				}
				tmp = _msg;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMsg() {
		return this._msg;
	}

}
