package server;

import server.io.*;
import server.io.Communication;
import server.Game;
import server.Player;

import server.elements.Treasure;

import java.util.Scanner;
import java.util.Arrays;

public class Console implements Runnable {
	private Communication _com;
	private String _message;

	public Console(Communication com) {
		this._com = com;
		this._message = "";
	}

	@Override
	public void run() {
		_com.sendMessage("101 WELCOME "+_com.getPlayer().getName());
	}
}
