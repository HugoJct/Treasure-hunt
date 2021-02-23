package client.view.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame {
   private JPanel _MenuPanel;
   private JButton _create;
   private JButton _join;
   
    public Menu() {
        this.setTitle("Menu");
        this.setSize(800, 600);
    }
}