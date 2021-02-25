package client.control.UI;

import javax.naming.ldap.Control;

import client.view.gui.Menu;
import client.view.gui.JoinGame;
import client.view.gui.CreateGame;
import javax.swing.*;
import java.awt.*;

public class ControlUI {
    private Menu view;

    public ControlUI(Menu v) {
        this.view = v;

        this.view.getCreate().addActionListener((event) -> new CreateGame());       
        this.view.getJoin().addActionListener((event) -> new JoinGame());        
    }
}
