package client.control.UIBis;

import javax.swing.*;
import java.awt.*;

import client.view.graphicDisplay.ControlDisplay;
import client.connex.Communication;

import client.commands.Command;

public class DirectionalCrosses {

    public DirectionalCrosses(ControlDisplay view, Command com) {
        view.getUp().addActionListener((event) -> com.sendMessage("200 GOUP"));
        view.getDown().addActionListener((event) -> com.sendMessage("200 GODOWN"));
        view.getLeft().addActionListener((event) -> com.sendMessage("200 GOLEFT"));
        view.getRight().addActionListener((event) -> com.sendMessage("200 GORIGHT"));

    }
}