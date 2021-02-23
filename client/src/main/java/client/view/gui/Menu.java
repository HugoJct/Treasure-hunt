package client.view.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Menu extends JFrame {
   private JPanel _menuPanel;
   private JButton _create;
   private JButton _join;
   private JLabel _info;
   
    public Menu() {
        this.setTitle("Menu");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this._menuPanel = new JPanel();
        this.setVisible(true); 
        this.setContentPane(this._menuPanel);

        this._create = new JButton("Creer une partie");
        this._join = new JButton("Rejoindre une partie");

        this._menuPanel.add(this._create);
        this._menuPanel.add(this._join);
        this._menuPanel.add(this._info);
        
    
    }
}