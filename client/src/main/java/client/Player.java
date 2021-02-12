package client;

import client.connex.ClientConnex;


public class Player {
    public static void main(String[] args) {
        ClientConnex connection = new ClientConnex();
        connection.connex();
	}
}
