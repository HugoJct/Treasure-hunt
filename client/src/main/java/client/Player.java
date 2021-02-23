package client;

import client.connex.Writer;
import client.connex.Reader;
import client.view.gui.Menu;

import java.net.Socket;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Thread;

public class Player {

    public static void main(String[] args) {
    	
		new Menu();
		
		/* try {
    		Socket s = new Socket("127.0.0.1",12345);
    		Thread read = new Thread(new Reader(s));
    		Thread write = new Thread(new Writer(s));

    		write.start();
    		read.start();
    	} catch(IOException e) {
    		e.printStackTrace();
    	} */
	}
}
