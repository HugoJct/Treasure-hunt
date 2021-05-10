package client.view.graphicDisplay;

import javax.swing.*;
import java.awt.*;


public class GameSelectionDisplay extends JFrame {
    private JFrame frame = new JFrame();
    private JPanel createGame = new JPanel();
    private JPanel createGameInputs = new JPanel();
    private JPanel joinGame = new JPanel();
    private JPanel gamemod = new JPanel();

    private JCheckBox gamemodOne = new JCheckBox("Speeding contest");
    private JCheckBox gamemodTwo = new JCheckBox("Round by round");
    private JButton confirm = new JButton("Send");

    private JMenuBar head = new JMenuBar();

    private JTextField dimensionX = new JTextField("Length");
    private JTextField dimensionY = new JTextField("Width");
    private JTextField nbrOfHoles = new JTextField("Holes");
    private JTextField nbrOfTreasures = new JTextField("Treasures");

    public GameSelectionDisplay() {

        frame.setTitle("Game Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1600, 600);
        frame.setVisible(true);

        frame.setLayout(new GridLayout(1, 2));
        frame.add(createGame);
        frame.add(joinGame);

        frame.add(head);
        createGame.add(createGameInputs);

        head.add(new JMenuItem("Create Game"), BorderLayout.WEST);
        head.add(new JMenuItem("Join Game"), BorderLayout.EAST);
        frame.setJMenuBar(head);

        createGameInputs.setLayout(new GridLayout(6, 2));
        gamemod.setLayout(new GridLayout(1, 3));

        createGameInputs.add(gamemod);
        gamemod.add(new JLabel("Game mod : "));
        gamemod.add(gamemodOne);
        gamemod.add(gamemodTwo);
        createGameInputs.add(new JLabel(""));
        createGameInputs.add(new JLabel("Length : "));
        createGameInputs.add(dimensionX);
        createGameInputs.add(new JLabel("Width : "));
        createGameInputs.add(dimensionY);
        createGameInputs.add(new JLabel("Number of holes : "));
        createGameInputs.add(nbrOfHoles);
        createGameInputs.add(new JLabel("Number of treasures : "));
        createGameInputs.add(nbrOfTreasures);
        createGameInputs.add(confirm);
    }

    public JCheckBox getGamemodOne() {
        return this.gamemodOne;
    }
    public JCheckBox getGamemodTwo() {
        return this.gamemodTwo;
    }

    public JButton getConfirm() {
        return this.confirm;
    }

    public JTextField getCoX() {
        return this.dimensionX;
    }
    public JTextField getCoY() {
        return this.dimensionY;
    }
    public JTextField getHoles() {
        return this.nbrOfHoles;
    }
    public JTextField getTreasures() {
        return this.nbrOfTreasures;
    }

}
