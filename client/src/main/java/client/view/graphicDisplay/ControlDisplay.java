package client.view.graphicDisplay;

import javax.swing.*;
import java.awt.*;

import client.control.UIBis.DirectionalCrosses;

public class ControlDisplay extends JFrame {
    private JFrame frame = new JFrame();
    private JPanel inputs = new JPanel();
    private DirectionalCrosses control;

    private JButton up = new JButton("UP");
    private JButton down = new JButton("DOWN");
    private JButton left = new JButton("LEFT");
    private JButton right = new JButton("RIGHT");
    private JButton emptyOne = new JButton();
    private JButton emptyTwo = new JButton();
    private JButton emptyThree = new JButton();
    private JButton emptyFor = new JButton();
    private JButton emptyFive = new JButton();

    public ControlDisplay() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(inputs);

        inputs.setLayout(new GridLayout(3, 3));

        inputs.add(emptyOne);
        inputs.add(up);
        inputs.add(emptyTwo);
        inputs.add(left);
        inputs.add(emptyThree);
        inputs.add(right);
        inputs.add(emptyFor);
        inputs.add(down);
        inputs.add(emptyFive);
        
        emptyOne.setVisible(false); 
        emptyTwo.setVisible(false); 
        emptyThree.setVisible(false); 
        emptyFor.setVisible(false); 
        emptyFive.setVisible(false); 

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