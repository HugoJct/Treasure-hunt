package client.control.UI.application;
	
import client.GameInfo;
import client.control.UI.application.Modele;

import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Main extends Application {
	FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainFXML.fxml"));
	Modele m = new Modele(); //Put a game object in argument
	
	
	public void Refresh(Stage primaryStage) {
		/* ----------  Creation of window  ---------- */
		primaryStage.setTitle("Chasse au trésor"); //Title of window
		primaryStage.setResizable(false); //Not resizable
		primaryStage.centerOnScreen(); //Window is centered on screen
		primaryStage.setWidth(31*m.getColumns()); //Width
		primaryStage.setHeight(31*m.getLines()); //Height
		
		Group AllElements = new Group(); //Regroups all Elements of the screen(Background image, wall, hole, treasures and players)
		/* ----------  Adding elements of the game  ---------- */
		//Background
		try {
			File f = new File("./src/main/java/client/control/UI/Images/Background3.jpg"); 
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
			for(int i = 0; i<m.getWallPos().length-1; i++) {
				File fw = new File("./src/main/java/client/control/UI/Images/wall2.png");
				String pathWall = fw.toURI().toURL().toString();
				Image WallImg = new Image(pathWall, false);
				ImageView Wall = new ImageView(WallImg);
				Wall.setLayoutX(m.getWallPos()[i+1]*30);
				Wall.setLayoutY(m.getWallPos()[i]*28);
				AllElements.getChildren().add(Wall);
				i++; // i used for x and i+1 for y.
			}
			
			//Display treasures
			for(int i = 0; i<m.getTreasurePos().length-1; i++) {
				File ft = new File("./src/main/java/client/control/UI/Images/treasure.png");
				String pathTreasure = ft.toURI().toURL().toString();
				Image TreasureImg = new Image(pathTreasure, false);
				ImageView Treasure = new ImageView(TreasureImg);
				Treasure.setLayoutX(m.getTreasurePos()[i+1]*30);
				Treasure.setLayoutY(m.getTreasurePos()[i]*28);
				AllElements.getChildren().add(Treasure);
				i+=2; // i used for x and i+1 for y.
			}
			
			//Display Holes
			for(int i = 0; i<m.getHolePos().length-1; i++) {
				File fh = new File("./src/main/java/client/control/UI/Images/hole.png");
				String pathHole = fh.toURI().toURL().toString();
				Image HoleImg = new Image(pathHole, false);
				ImageView Hole = new ImageView(HoleImg);
				Hole.setLayoutX(m.getHolePos()[i+1]*30);
				Hole.setLayoutY(m.getHolePos()[i]*28);
				AllElements.getChildren().add(Hole);
				i++; // i used for x and i+1 for y.
			}
			
			
			//Display players (Black square for tests)
			for(int i = 0; i<m.getPlayerPos().length-1; i++) {
				File fh = new File("./src/main/java/client/control/UI/Images/Player2.png");
				String pathPlayer = fh.toURI().toURL().toString();
				Image PlayerImg = new Image(pathPlayer, false);
				ImageView Player = new ImageView(PlayerImg);
				Player.setLayoutX(m.getPlayerPos()[i+1]*30);
				Player.setLayoutY(m.getPlayerPos()[i]*28);
				AllElements.getChildren().add(Player);
				i++; // i used for x and i+1 for y.
			}
			
		}catch(Exception e){
			System.out.println("Error, some GameInfo attributes are null");
			e.printStackTrace();
		}
			
			
			
		/* ----------  Push all graphic elements in the window  --------- */
		Scene SceneAllElements = new Scene(AllElements);
		primaryStage.setScene(SceneAllElements);
		primaryStage.show();
	}
	

	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		// ----------  Creation of window  ---------- 
		primaryStage.setTitle("Chasse au trésor"); //Title of window
		primaryStage.setResizable(false); //Not resizable
		primaryStage.centerOnScreen(); //Window is centered on screen
		
		primaryStage.setWidth(31*m.getColumns()); //Width
		primaryStage.setHeight(31*m.getLines()); //Height
		
		
		Group AllElements = new Group(); //Regroups all Elements of the screen(Background image, wall, hole, treasures and players)
		
		// ----------  Adding elements of the game  ---------- 
		//Background
		try {
			File f = new File("./src/main/java/client/control/UI/Images/Background3.jpg"); 
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
			for(int i = 0; i<m.getWallPos().length-1; i++) {
				File fw = new File("./src/main/java/client/control/UI/Images/wall2.png");
				String pathWall = fw.toURI().toURL().toString();
				Image WallImg = new Image(pathWall, false);
				ImageView Wall = new ImageView(WallImg);
				Wall.setLayoutX(m.getWallPos()[i+1]*30);
				Wall.setLayoutY(m.getWallPos()[i]*28);
				AllElements.getChildren().add(Wall);
				i++; // i used for x and i+1 for y.
			}
			
			//Display treasures
			for(int i = 0; i<m.getTreasurePos().length-1; i++) {
				File ft = new File("./src/main/java/client/control/UI/Images/treasure.png");
				String pathTreasure = ft.toURI().toURL().toString();
				Image TreasureImg = new Image(pathTreasure, false);
				ImageView Treasure = new ImageView(TreasureImg);
				Treasure.setLayoutX(m.getTreasurePos()[i+1]*30);
				Treasure.setLayoutY(m.getTreasurePos()[i]*28);
				AllElements.getChildren().add(Treasure);
				i+=2; // i used for x and i+1 for y.
			}
			
			//Display Holes
			for(int i = 0; i<m.getHolePos().length-1; i++) {
				File fh = new File("./src/main/java/client/control/UI/Images/hole.png");
				String pathHole = fh.toURI().toURL().toString();
				Image HoleImg = new Image(pathHole, false);
				ImageView Hole = new ImageView(HoleImg);
				Hole.setLayoutX(m.getHolePos()[i+1]*30);
				Hole.setLayoutY(m.getHolePos()[i]*28);
				AllElements.getChildren().add(Hole);
				i++; // i used for x and i+1 for y.
			}
			
			
			//Display players (Black square for tests)
			for(int i = 0; i<m.getPlayerPos().length-1; i++) {
				File fh = new File("./src/main/java/client/control/UI/Images/Player2.png");
				String pathPlayer = fh.toURI().toURL().toString();
				Image PlayerImg = new Image(pathPlayer, false);
				ImageView Player = new ImageView(PlayerImg);
				Player.setLayoutX(m.getPlayerPos()[i+1]*30);
				Player.setLayoutY(m.getPlayerPos()[i]*28);
				AllElements.getChildren().add(Player);
				i++; // i used for x and i+1 for y.
			}
			
		}catch(Exception e){
			System.out.println("Error, some GameInfo attributes are null");
			e.printStackTrace();
		}
			
			
			
		// ----------  Push all graphic elements in the window  --------- 
		Scene SceneAllElements = new Scene(AllElements);
		//primaryStage.setScene(SceneAllElements);
		//primaryStage.show();
		
		
		primaryStage.setWidth(500); //Width
		primaryStage.setHeight(500); //Height
		// ************ Menu scene ************
		Group MenuElements = new Group();
		
		//Creation of button and adaptation of their style (color, position, action ...)
		Label l1 = new Label("Menu");
		l1.setStyle("-fx-text-fill : #FFFFFF; -fx-font-size : 40; ");
		l1.setLayoutX(primaryStage.getWidth()/2 - 60);
		
		
		Button b1 = new Button("Play");
		b1.setStyle("-fx-background-color: #B9AA6A; -fx-font-size : 20; ");
		b1.setLayoutX(primaryStage.getWidth()/2 - 52);
		b1.setLayoutY(80);
		
		
		Button b2 = new Button("Quit");
		b2.setStyle("-fx-background-color: #B9AA6A; -fx-font-size : 20;");
		b2.setLayoutX(primaryStage.getWidth()/2 - 43);
		b2.setLayoutY(150);
		
		/*Timeline loop = new Timeline(new  KeyFrame(Duration.millis(30), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				m = new Modele();
				primaryStage.setWidth(31*m.getColumns()); //Width
				primaryStage.setHeight(31*m.getLines()); //Height
				Refresh(primaryStage);
			}
		}));*/
		
		b1.setOnAction(e -> {//Refresh(primaryStage);
							/*loop.setCycleCount(Timeline.INDEFINITE);
							loop.play();*/
							this.m = new Modele();
							 primaryStage.centerOnScreen(); //Window is centered on screen
							 primaryStage.setWidth(31*m.getColumns()); //Width
							 primaryStage.setHeight(31*m.getLines()); //Height
							 primaryStage.setScene(SceneAllElements);});
		
		
		b2.setOnAction((ActionEvent event) -> {
		    Platform.exit();
		});
		
		//File for background image
		File f = new File("./src/main/java/client/control/UI/Images/Background3.jpg"); //initial : client/src/main/java/client/control/UI/Images/Background3.jpg
		String path = f.toURI().toURL().toString();
		Image BackImg = new Image(path, false); //Creation of Image from a file
		ImageView Background = new ImageView(BackImg); //Conversion to an ImageView
		
		//Add elements to create Menu's scene
		MenuElements.getChildren().addAll(Background, l1, b1, b2);
		Scene Menu = new Scene(MenuElements);
		
		//Displays Menu scene
		primaryStage.setScene(Menu);
		primaryStage.show();
		
	}
	
	
	
	
	
	
	
	
	
	public static class PrimeThread extends Thread {
        public void run() {
        	Application.launch(Main.class);
        }
    }
	
	/*public static void main(String[] args) throws Exception {
		System.out.println("test");
		launch(args);
	}*/
}
