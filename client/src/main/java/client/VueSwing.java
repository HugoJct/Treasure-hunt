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
	public static int ID = m.getID();
	public static String name = m.getName();


	public VueSwing() {
		ID += 1;
		this.setTitle("Chasse au Trésor - "+name+" ("+ID+")"); 
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
		int screenX = e.getXOnScreen();
	    int screenY = e.getYOnScreen();
		System.out.println("Mouse clicked on ("+screenX+","+screenY+")");
		
		int x = screenX/(30) - 2;
		int y = screenY/(28) - 4;
		System.out.println("Position in tab = ("+x+","+y+")");
		
		//Request to move with x and y attributes
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
	
	
	
	/*public static void main(String[] args) {
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
	}*/
	
	
	
}