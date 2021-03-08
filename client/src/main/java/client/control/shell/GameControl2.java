package Client.src.main.java.client.control.shell;
import java.util.Scanner;

import javax.swing.JFrame;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;  

public class GameControl2{
	JFrame f;
	
	public GameControl2() {
		f = new JFrame();
	}
    
	//Function which asks player a question and returns the answer
	public static String GetInput(String question) {
		System.out.println(question);
		Scanner input = new Scanner(System.in);
		String retour = input.nextLine();
		return retour;
	}
	
	//Function which asks player a direction to move and give the information to the server
	public static void moveTo(String direction) {
		
		//Reacts according to the given direction
		switch(direction) {
			case "UP":
				System.out.println("UP");
				break;
			case "DOWN":
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
	
	
	//Inner class used to control game thanks to keyboard
	public static class KeyboardControl extends JFrame implements KeyListener{
		private static final long serialVersionUID = 1L;
		
		KeyboardControl(){
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			//this.setSize(100, 100);
			this.setVisible(true);
			this.addKeyListener(this);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				System.out.println("UP direction activated");	
				moveTo("UP");
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				System.out.println("DOWN direction activated");
				moveTo("DOWN");
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				System.out.println("LEFT direction activated");
				moveTo("LEFT");
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println("RIGHT direction activated");
				moveTo("RIGHT");
				return;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}