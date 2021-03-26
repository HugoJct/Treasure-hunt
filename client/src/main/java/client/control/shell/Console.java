package client.control.shell;

import client.connex.Reader;
import client.connex.Writer;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {
	private Reader _read;
	private Writer _write;

	private String _message;

	public Console(Reader r, Writer w) {
		this._read = r;
		this._write = w;
		this._message = "D";
	}

	@Override
	public void run() {
		while(!(_write.getSocket().isClosed())) {	
			try {
				Thread.sleep(1);
				if (_message != _read.getMsg()) {
					_message = _read.getMsg();
					//useMessage(_message);
				}	
			}
			catch(InterruptedException e) {

			}	
		}	

	}

	public void useMessage(String command) {
		String[] brokenCommand = breakCommand(command);

		switch(brokenCommand[0]) {
			case "0":
				_write.sendMessage("TEST");
			default:
				_write.sendMessage("UNKNOW");
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}