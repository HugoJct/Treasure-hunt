package client.view.graphicDisplay;

import javax.swing.*;
import java.awt.*;

import client.control.UIBis.DirectionalCrosses;

public class ControlDisplay extends JFrame {
    private JFrame frame = new JFrame();
    private JPanel inputs = new JPanel();
    private DirectionalCrosses control;

    private JButton up = new JButton("GO UP");
    private JButton down = new JButton("GO DOWN");
    private JButton left = new JButton("GO LEFT");
    private JButton right = new JButton("GO RIGHT");

    public ControlDisplay() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(inputs);

        inputs.setLayout(new GridLayout(2, 2));

        inputs.add(up);
        inputs.add(down);
        inputs.add(left);
        inputs.add(right);

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public JButton getUp() {
        return up;
    }
    public JButton getDown() {
        return down;
    }
    public JButton getLeft() {
        return left;
    }
    public JButton getRight() {
        return right;
    }
}