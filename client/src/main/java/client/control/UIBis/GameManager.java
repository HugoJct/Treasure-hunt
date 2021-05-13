package client.control.UIBis;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

import client.view.graphicDisplay.GameSelectionDisplay;
import client.connex.Communication;
import client.Player;
import client.commands.userIn.CommandCreateGame;
import client.commands.userIn.CommandGetList;
import client.GameInfo;
import client.commands.userIn.CommandJoinGame;
import client.commands.userIn.CommandRequestStart;

public class GameManager {
    
    public GameManager(GameSelectionDisplay view, PrintWriter out, Player p) {
        CommandCreateGame create = new CommandCreateGame(out);
        CommandGetList getList = new CommandGetList(out);
        CommandJoinGame joinGame = new CommandJoinGame(out);
        CommandRequestStart requestStart = new CommandRequestStart(out);
        view.getConfirm().addActionListener((event) -> build(create, view, p));
        view.getRefresh().addActionListener((event) -> refresh(getList, p, view));
        view.joinGame().addActionListener((event) -> join(view, joinGame, p));
        view.getRequestStart().addActionListener((event) -> startGame(view, requestStart, p));

    }

    public void build(CommandCreateGame create, GameSelectionDisplay view, Player p) {
        String[] command = {"110", evalGameMod(view), view.getCoX().getText(), view.getCoY().getText(), view.getHoles().getText(), view.getTreasures().getText()};
        create.execute(p, command);
        view.listGames();
    }

    public void startGame(GameSelectionDisplay view, CommandRequestStart requestStart, Player p) {
        requestStart.execute(p, null);
    }

    public void join(GameSelectionDisplay view, CommandJoinGame joinGame, Player p) {
        for (int i = 0 ; i<GameInfo.getNumberOfGames() ; i++) {
            if (view.getGames()[i].isSelected()) {
                String args[] = {"130", String.valueOf(i)};
                joinGame.execute(p, args);
                break;
            }
        }
    }

    public String evalGameMod(GameSelectionDisplay view) {
        if (view.getGamemodOne().isSelected()) {
            return "0";
        } else {
            return "1";
        }    
    }

    public void refresh(CommandGetList getList, Player p, GameSelectionDisplay view) {
        getList.execute(p, null) ; view.setNbrOfGames(GameInfo.getNumberOfGames());
        view.listGames();
    }

}
