package client.System;

import client.view.gui.JoinGame;
import java.io.IOException;

public class JoinNewGame {
    
    public static void joinGame() {
        JoinGame visual;
        try {
            visual = new JoinGame();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    } 
         
}