package server.src.main.java.server.connex;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServConnex {
    
    public void connex() {
        ServerSocket sSocket;
		int port = 12345;
		try {
			sSocket = new ServerSocket(port);
			System.out.println("Server lauched on port "+port+" !!");
			Socket cSocket = sSocket.accept();
			
			Thread write = new Thread(new Writer(cSocket));
			Thread read = new Thread(new Reader(cSocket));
			
			write.start();
			read.start();

			write.join();
			read.join();

			sSocket.close();
			cSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
    }
}
