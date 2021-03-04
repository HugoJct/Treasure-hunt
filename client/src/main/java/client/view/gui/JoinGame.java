package client.view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JoinGame extends JFrame {
    private JPanel _joinPanel;  
    
    public JoinGame() {
        this.setTitle("Rejoindre une partie");
        this.setSize(800, 600);

        this._joinPanel = new JPanel();
        this.setVisible(true); 
        this.setContentPane(this._joinPanel);
    }
}
