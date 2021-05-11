package client.control.UIBis;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

import client.view.graphicDisplay.GameSelectionDisplay;
import client.connex.Communication;
import client.Player;
import client.commands.userIn.CommandCreateGame;
import client.commands.userIn.CommandGetList;

public class GameManager {
    
    public GameManager(GameSelectionDisplay view, PrintWriter out, Player p) {
        CommandCreateGame create = new CommandCreateGame(out);
        CommandGetList getList = new CommandGetList(out);
        view.getConfirm().addActionListener((event) -> build(create, view, p));
        view.getRefresh().addActionListener((event) -> getList.execute(p, null));
    }

    public void build(CommandCreateGame create, GameSelectionDisplay view, Player p) {
        String[] command = {"110", evalGameMod(view), view.getCoX().getText(), view.getCoY().getText(), view.getHoles().getText(), view.getTreasures().getText()};
        create.execute(p, command);
    }

    public String evalGameMod(GameSelectionDisplay view) {
        if (view.getGamemodOne().isSelected()) {
            return "0";
        } else {
            return "1";
        }    
    }
}
