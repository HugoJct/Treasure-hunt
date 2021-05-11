package client;


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.control.UI.application.Modele;
import client.VueSwing;

@SuppressWarnings("serial")
public class SceneSwing extends JPanel{
	public Modele m = new Modele();
	

	private Image Back;
	
	private Image Wall;
	private Image Treasure;
	private Image Hole;
	private Image Player;

	
	
	//Constructor
	public SceneSwing() {
		super();


		try {
			if(this.m == null) {
				System.out.println("modele null");
			}
			
			
			// ------- Fonctionnel avec un lancement windows ------- 
			/*this.Back = (new ImageIcon(getClass().getResource("/Images/Background3.jpg"))).getImage();
			this.Wall = (new ImageIcon(getClass().getResource("/Images/wall2.png"))).getImage();
			this.Treasure  = (new ImageIcon(getClass().getResource("/Images/treasure.png"))).getImage();
			this.Hole = (new ImageIcon(getClass().getResource("/Images/hole.png"))).getImage();
			this.Player  = (new ImageIcon(getClass().getResource("/Images/Player2.png"))).getImage();
			*/
			
			
			
			
			// -------- Fonctionnel avec lancement linux ------- 
			this.Back = (new ImageIcon("./src/main/java/client/control/UI/Images/Background3.jpg")).getImage();
			this.Wall = (new ImageIcon("./src/main/java/client/control/UI/Images/wall2.png")).getImage();
			this.Treasure  = (new ImageIcon("./src/main/java/client/control/UI/Images/treasure.png")).getImage();
			this.Hole = (new ImageIcon("./src/main/java/client/control/UI/Images/hole.png")).getImage();
			this.Player  = (new ImageIcon("./src/main/java/client/control/UI/Images/Player2.png")).getImage();
			
			
		}catch(Exception e) {
			System.out.println("Error : Images not found");
			//e.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics2D)g;
		
		
		g2.drawImage(this.Back, 0, 0, null);
		
		
		//Display Walls
		for(int i = 0; i<m.getWallPos().length-1; i++) {
			g2.drawImage(this.Wall, m.getWallPos()[i+1]*30, m.getWallPos()[i]*28, null);
			i++; // i used for x and i+1 for y.
		}
		
			
		//Display treasures
		for(int i = 0; i<m.getTreasurePos().length-1; i++) {
			g2.drawImage(this.Treasure, m.getTreasurePos()[i+1]*30, m.getTreasurePos()[i]*28, null);
			i+=2; // i used for x and i+1 for y.
		}
		
		//Display Holes
		for(int i = 0; i<m.getHolePos().length-1; i++) {
			g2.drawImage(this.Hole, m.getHolePos()[i+1]*30, m.getHolePos()[i]*28, null);
			i++; // i used for x and i+1 for y.
		}
		
		
		//Display players (Black square for tests)
		for(int i = 0; i<m.getPlayerPos().length-1; i++) {
			g2.drawImage(this.Player, m.getPlayerPos()[i]*30, m.getPlayerPos()[i+1]*28, null);
			i++; // i used for x and i+1 for y.
		}
		
	}
	
	
	/*public static void main(String[] args) {
		JFrame fen = new JFrame();
		SceneSwing scene = new SceneSwing();
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setSize(scene.m.getColumns()*30, scene.m.getLines()*28);
		fen.setLocationRelativeTo(null);
		fen.setResizable(false);
		fen.setAlwaysOnTop(true);
		
		
		fen.setContentPane(scene);
		fen.setVisible(true);
	}*/
}