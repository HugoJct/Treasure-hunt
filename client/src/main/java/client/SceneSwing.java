import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.control.UI.application.Modele;

@SuppressWarnings("serial")
public class SceneSwing extends JPanel{
	public static Modele m = new Modele();
	

	private Image Back;
	
	private Image Wall;
	private Image Treasure;
	private Image Hole;
	private Image Player;

	
	
	//Constructor
	public SceneSwing() {
		super();

		try {
			this.Back = (new ImageIcon("Images/Background3.jpg")).getImage();
			this.Wall = (new ImageIcon("Images/wall2.png")).getImage();
			this.Treasure  = (new ImageIcon("Images/treasure.png")).getImage();
			this.Hole = (new ImageIcon("Images/hole.png")).getImage();
			this.Player  = (new ImageIcon("Images/Player2.png")).getImage();
			
			/*this.Back = (new ImageIcon(getClass().getResource("/Images/Background3.jpg"))).getImage();
			this.Wall = (new ImageIcon(getClass().getResource("/Images/wall2.png"))).getImage();
			this.Treasure  = (new ImageIcon(getClass().getResource("/Images/treasure.png"))).getImage();
			this.Hole = (new ImageIcon(getClass().getResource("/Images/hole.png"))).getImage();
			this.Player  = (new ImageIcon(getClass().getResource("/Images/Player2.png"))).getImage();*/
			
		}catch(Exception e) {
			System.out.println("Error : Images not found");
			e.printStackTrace();
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
			g2.drawImage(this.Player, m.getPlayerPos()[i+1]*30, m.getPlayerPos()[i]*28, null);
			i++; // i used for x and i+1 for y.
		}
		
	}
}
