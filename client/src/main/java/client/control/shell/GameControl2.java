package Client.src.main.java.client.control.shell;
import java.util.Scanner;



public class GameControl2 {
    
	//Function which asks player a question and returns the answer
	public static String GetInput(String question) {
		System.out.println(question);
		Scanner input = new Scanner(System.in);
		String retour = input.nextLine();
		return retour;
	}
	
	//Function which asks player a direction to move and give the information to the server
	public static void moveTo() {
		//Asks direction
		String direction = GetInput("Choose a direction to move :  ");
		
		//Reacts according to the given direction
		switch(direction) {
			case "UP":
				System.out.println("UP");
				break;
			case "DOWN" :
				System.out.println("DOWN");
				break;
			case "LEFT":
				System.out.println("LEFT");
				break;
			case "RIGHT":
				System.out.println("RIGHT");
				break;
			default:
				System.out.println("Unknown direction.");
		}
	}
	
	
	
	public static void main(String[] args) {
		String test = GetInput("Quel est ton nom :");
		System.out.println("Bienvenue "+test);
		moveTo();
	}
	
	
}
