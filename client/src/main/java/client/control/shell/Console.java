package client.control.shell;

import client.connex.Reader;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {
	private Reader _com;
	private String _message;

	public Console(Reader com) {
		this._com = com;
		this._message = "";
	}

	@Override
	public void run() {
		
			try {
				Thread.sleep(1);
				if (_message != _com.getMsg()) {
					_message = _com.getMsg();
					useMessage(_message);
				}	
			}
			catch(InterruptedException e) {
				
			}		

	}

	public void useMessage(String command) {
		String[] brokenCommand = breakCommand(command);

		switch(brokenCommand[0]) {
			//case "0":
			default:
				_com.sendMessage("UNKNOW");
		}
	}

	private String[] breakCommand(String command) {			//This method breaks the command which arguments are separated by spaces
		String delims = "[ ]+";		//This line sets the delimiter between words. Here we use "space" as delimiter, brackets indicate 
									//the start and end of the group. "+" indicate that conscutive delimitor should be treated as a single one
		String[] args = command.split(delims);
		return args;
	}

}