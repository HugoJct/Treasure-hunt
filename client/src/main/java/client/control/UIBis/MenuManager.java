package client.control.UIBis;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

import client.view.graphicDisplay.Menu;
import client.ServerInfo;

public class MenuManager {
    
    public MenuManager(Menu view) {
        view.getConnect().addActionListener((event) -> writeDatas(view));
    }

    private void writeDatas(Menu view) {
        ServerInfo.setIp(view.getIp().getText());
        ServerInfo.setPort(view.getPort().getText());
    }
}
