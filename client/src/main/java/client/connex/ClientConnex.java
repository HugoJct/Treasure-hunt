package client.connex;

import client.connex.Writer;
import client.connex.Reader;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientConnex {
    
	public ClientConnex() {

	}

    public void connex() {
		try {
			Socket socket = new Socket("127.0.0.1", 12345);
			
			Thread read = new Thread(new Reader(socket));
			Thread write = new Thread(new Writer(socket));
			
			write.start();
			read.start();

			write.join();
			read.join();

			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

    }
}
