package client.view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreateGame extends JFrame {
    private JPanel _createPanel;

    public CreateGame() {
        this.setTitle("Créer une partie");
        this.setSize(800, 600);

        this._createPanel = new JPanel();
        this.setVisible(true); 
        this.setContentPane(this._createPanel);
    }
}
