package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

import client.control.UI.application.Modele;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import client.SceneSwing;


@SuppressWarnings("serial")
public class VueSwing extends JFrame implements MouseInputListener{
	JPanel Background = new JPanel();
	static Modele m = new Modele();
	public static SceneSwing scene;
	public static int ID = 0;


	public VueSwing() {
		ID += 1;
		this.setTitle("Chasse au Trésor "+ID); 
		this.setSize(m.getColumns()*30+20, m.getLines()*28+50);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		scene = new SceneSwing();
		scene.addMouseListener(this);
		this.setContentPane(scene);
		this.setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	
	


	@Override
	public void mouseClicked(MouseEvent e) {
		//this.GridGeneration();
		System.out.println("Mouse clicked");
	}



	@Override
	public void mousePressed(MouseEvent e) {
	}



	@Override
	public void mouseReleased(MouseEvent e) {
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
	
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
	      new Runnable() {
	        public void run() { 
	          VueSwing v = new VueSwing();
	          v.setVisible(true);
	          VueSwing v2 = new VueSwing();
	          v2.setVisible(true);
	         }
	      }
	    );		
	}
	
	
	
}