package client.control.UI;


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.LinkedList;
import java.util.HashMap;

import client.control.UI.VueSwing;
import client.GameInfo;

import server.elements.*;

@SuppressWarnings("serial")
public class SceneSwing extends JPanel{

	private Image Back;
	
	private Image Wall;
	private Image Treasure;
	private Image Hole;
	private Image Player;

	private LinkedList<Wall> walls;
	private LinkedList<Hole> holes;
	private LinkedList<Treasure> treasures;
	private HashMap<String,Integer[]> players;
	
	
	//Constructor
	public SceneSwing() {
		super();


		try {
			
			
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

		walls = GameInfo.getWalls();
		holes = GameInfo.getHoles();
		treasures = GameInfo.getTreasures();
		players = GameInfo.getPlayerCoordinates();

		//Display walls with new GameInfo structure
		for(Wall w : walls) {
			g2.drawImage(this.Wall,w.getY()*30,w.getX()*28,null);
		}
		
		//Display holes with new GameInfo structure
		for(Hole h : holes) {
			g2.drawImage(this.Hole,h.getY()*30,h.getX()*28,null);
		}

		//Display Treasures with new GameInfo structure
		for(Treasure t : treasures) {
			g2.drawImage(this.Treasure,t.getY()*30,t.getX()*28,null);
		}
 
		for(String s : players.keySet()) {
			int[] co = GameInfo.getPlayerCoordinates(s);
			g2.drawImage(this.Player,co[0]*30,co[1]*28,null);
		}

		/*		Previous components painting using old GameInfo Structure
		//Display players (Black square for tests)
		for(int i = 0; i<GameInfo.getPlayerPos().length-1; i++) {
			g2.drawImage(this.Player, GameInfo.getPlayerPos()[i]*30, GameInfo.getPlayerPos()[i+1]*28, null);
			i++; // i used for x and i+1 for y.
		}

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
		*/
	}
}
