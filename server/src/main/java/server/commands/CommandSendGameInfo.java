package server.commands;

import java.io.PrintWriter;

import server.playingProps.Game;
import server.playingProps.Player;

import server.ServerMain;

public class CommandSendGameInfo extends Command {

	public CommandSendGameInfo(PrintWriter pw) {
		super("121",pw);
	}

	@Override
	public void execute(Game g, Player p, String[] args) {
		int nbrOfGames = ServerMain.getNumberOfGames();
        int tab[] = new int[nbrOfGames*5];

        sendMessage("121 NUMBER "+nbrOfGames);

        if (nbrOfGames != 0) {
            int k = 0;
            for (int i = 0 ; i < tab.length ; i+=5) {
                tab[0+i] = g.getGameMod();
                tab[1+i] = ServerMain.getGameX(k);
                tab[2+i] = ServerMain.getGameY(k);
                tab[3+i] = ServerMain.getNumberOfHoles(k);
                tab[4+i] = ServerMain.getNumberOfTreasures(k);
                k++;
            }

            int j = 0;
            for (int i = 0 ; i < tab.length ; i+=5) {
                sendMessage("121 MESS " + (j+1) + " ID " + j + " " + tab[0+i] + " " + tab[i+1] + " " + tab[2+i] + " " + tab[3+i] + " " + tab[4+i]);
                j++;
            }
        }
	}
}