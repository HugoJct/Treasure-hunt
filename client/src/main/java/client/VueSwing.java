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


@SuppressWarnings("serial")
public class VueSwing extends JFrame implements MouseInputListener{
	JPanel Background = new JPanel();
	static Modele m = new Modele();
	public static SceneSwing scene;


	public VueSwing() {
		this.setTitle("Chasse au Trésor"); 
		this.setSize(m.getColumns()*30+20, m.getLines()*28+50);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		/*File f = new File("Images/Background3.jpg");
		System.out.println(f.exists());
		ImageIcon BackImg = new ImageIcon("Images/Background3.jpg");
		JLabel BI = new JLabel(BackImg);*/

		scene = new SceneSwing();
		scene.setVisible(true);
		scene.addMouseListener(this);
		this.setContentPane(scene);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
	         }
	      }
	    );		
	}
}