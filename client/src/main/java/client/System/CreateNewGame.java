package client.System;

import client.view.gui.CreateGame;
import java.io.IOException;

public class CreateNewGame {
    
    public static void newGame() {
        CreateGame visual;
        try {
            visual = new CreateGame();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    } 
         
}
