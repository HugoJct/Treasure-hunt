package client.control.UI.application;
	
import client.GameInfo;
import client.control.UI.application.Modele;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainFXML.fxml"));
	Modele m = new Modele(); //Put a game object in argument
	
	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		/* ----------  Creation of window  ---------- */
		primaryStage.setTitle("Chasse au trésor"); //Title of window
		primaryStage.setWidth(31*m.getColumns()); //Width
		primaryStage.setHeight(31*m.getLines()); //Height
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
		
		
		//La fonction génère une erreur (cause pour le moment inconnue
		try {
			//Display Walls from Model informations
			for(int i = 0; i<m.getWallPos().length; i++) {
				File fw = new File("Images/wall2.png");
				String pathWall = fw.toURI().toURL().toString();
				Image WallImg = new Image(pathWall, false);
				ImageView Wall = new ImageView(WallImg);
				Wall.setLayoutX(m.getWallPos()[i]*30);
				Wall.setLayoutY(m.getWallPos()[i+1]*28);
				AllElements.getChildren().add(Wall);
				i++; // i used for x and i+1 for y.
			}
			
			//Display treasures
			for(int i = 0; i<m.getTreasurePos().length; i++) {
				File ft = new File("Images/treasure.png");
				String pathTreasure = ft.toURI().toURL().toString();
				Image TreasureImg = new Image(pathTreasure, false);
				ImageView Treasure = new ImageView(TreasureImg);
				Treasure.setLayoutX(m.getTreasurePos()[i]*30);
				Treasure.setLayoutY(m.getTreasurePos()[i+1]*28);
				AllElements.getChildren().add(Treasure);
				i++; // i used for x and i+1 for y.
			}
			
			//Display Holes
			for(int i = 0; i<m.getHolePos().length; i++) {
				File fh = new File("Images/hole.png");
				String pathHole = fh.toURI().toURL().toString();
				Image HoleImg = new Image(pathHole, false);
				ImageView Hole = new ImageView(HoleImg);
				Hole.setLayoutX(m.getHolePos()[i]*30);
				Hole.setLayoutY(m.getHolePos()[i+1]*28);
				AllElements.getChildren().add(Hole);
				i++; // i used for x and i+1 for y.
			}
			
			
			//Display players (Black square for tests)
			for(int i = 0; i<m.getPlayerPos().length; i++) {
				File fh = new File("Images/Player.png");
				String pathHole = fh.toURI().toURL().toString();
				Image HoleImg = new Image(pathHole, false);
				ImageView Hole = new ImageView(HoleImg);
				Hole.setLayoutX(m.getPlayerPos()[i]*30);
				Hole.setLayoutY(m.getPlayerPos()[i+1]*28);
				AllElements.getChildren().add(Hole);
				i++; // i used for x and i+1 for y.
			}
		}catch(Exception e){
			System.out.println("Erreur lors de l'affichage des éléments surement dû à un manque de données dans GameInfo");
			e.printStackTrace();
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
