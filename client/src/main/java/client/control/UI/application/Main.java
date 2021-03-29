package Client.src.main.java.client.control.UI.application;
	
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	Modele m = new Modele(null); //Put a game object in argument
	
	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		/* ----------  Creation of window  ---------- */
		primaryStage.setTitle("Chasse au trésor"); //Title of window
		primaryStage.setWidth(31*m.Columns); //Width
		primaryStage.setHeight(31*m.Lines); //Height
		primaryStage.setResizable(false); //Not resizable
		primaryStage.centerOnScreen(); //Window is centered on screen
		
		
		Group AllElements = new Group(); //Regroups all Elements of the screen(Background image, wall, hole, treasures and players)
		
		/* ----------  Adding elements of the game  ---------- */
		//Background
		try {
			File f = new File("Images/Background3.jpg"); 
			String path = f.toURI().toURL().toString();
			Image BackImg = new Image(path, false); //Creation of Image from a file
			ImageView Background = new ImageView(BackImg); //Conversion to an ImageView
			AllElements.getChildren().add(Background); //Adds this ImageView to the group of egraphic elements
			
		}catch(Exception e) {
			System.out.println("Erreur lors de la recherche d'une image dans le fichier Images");
			e.printStackTrace();
		}
		
		
		//All elements of board :
		for(int y = 0; y<m.Lines; y++) {
			for(int x = 0; x<m.Columns; x++) {
				//Walls
				if(m.tab[y][x].toString() == "w") {
					File fw = new File("Images/wall2.png");
					String pathWall = fw.toURI().toURL().toString();
					Image WallImg = new Image(pathWall, false);
					ImageView Wall = new ImageView(WallImg);
					Wall.setLayoutX(x*30);
					Wall.setLayoutY(y*28);
					AllElements.getChildren().add(Wall);
				}
				
				//treasures
				if(m.tab[y][x].toString() == "t") {
					File ft = new File("Images/treasure.png");
					String pathTreasure = ft.toURI().toURL().toString();
					Image TreasureImg = new Image(pathTreasure, false);
					ImageView Treasure = new ImageView(TreasureImg);
					Treasure.setLayoutX(x*30);
					Treasure.setLayoutY(y*28);
					AllElements.getChildren().add(Treasure);
				}
				
				//holes
				if(m.tab[y][x].toString() == "h") {
					File fh = new File("Images/hole.png");
					String pathHole = fh.toURI().toURL().toString();
					Image HoleImg = new Image(pathHole, false);
					ImageView Hole = new ImageView(HoleImg);
					Hole.setLayoutX(x*30);
					Hole.setLayoutY(y*28);
					AllElements.getChildren().add(Hole);
				}
			}
		}
		
		
		
		/* ----------  Push all graphic elements in the window  --------- */
		Scene SceneAllElements = new Scene(AllElements);
		primaryStage.setScene(SceneAllElements);
		
		
		/* ----------  Display  ---------- */
		primaryStage.show(); //Display of window
		
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
