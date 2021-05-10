package client.commands.in;

import java.io.PrintWriter;

import client.Player;
import client.VueSwing;
import client.GameInfo;
import client.commands.Command;

import client.view.graphicDisplay.ControlDisplay;
import client.control.UIBis.DirectionalCrosses;

public class CommandGameStart extends Command {
	
	public CommandGameStart(PrintWriter out) {
		super("153",out);
	}

	public void execute(Player p, String[] args) {
		sendMessage("400 GETHOLES");
		sendMessage("410 GETTREASURES");
        sendMessage("420 GETWALLS");
        GameInfo.setStarted(true);
		new DirectionalCrosses(new ControlDisplay(p), this);
		javax.swing.SwingUtilities.invokeLater(
			      new Runnable() {
			        public void run() { 
			        	VueSwing v = new VueSwing();
			        }
			      }
		);
	}
}