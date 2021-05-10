package client.commands.in;

import java.io.PrintWriter;

import client.Player;
import client.VueSwing;
import client.GameInfo;
import client.commands.Command;

import client.view.graphicDisplay.ControlDisplay;
import client.control.UIBis.DirectionalCrosses;

public class CommandGameStart extends Command {
	private PrintWriter out;

	public CommandGameStart(PrintWriter out) {
		super("153",out);
		this.out = out;
	}

	public void execute(Player p, String[] args) {
		sendMessage("400 GETHOLES");
		sendMessage("410 GETTREASURES");
        sendMessage("420 GETWALLS");
        GameInfo.setStarted(true);
<<<<<<< client/src/main/java/client/commands/in/CommandGameStart.java
		new DirectionalCrosses(new ControlDisplay(p), this);
		javax.swing.SwingUtilities.invokeLater(
			      new Runnable() {
			        public void run() { 
			        	VueSwing v = new VueSwing();
			        }
			      }
		);
=======
		//new DirectionalCrosses(new ControlDisplay(p), this.out);
>>>>>>> client/src/main/java/client/commands/in/CommandGameStart.java
	}
}