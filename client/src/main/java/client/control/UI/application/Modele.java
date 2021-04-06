package Client.src.main.java.client.control.UI.application;

import java.util.Random;



public class Modele {
	int Lines;
	int Columns;
	int[] TreasurePos;
	int[] WallPos;
	int[] HolePos;
	int[] PlayerPos;
 	
	
	//Constructor
	public Modele(GameInfo g) {
		this.Lines = g.map[1];
		this.Columns = g.map[0];
		this.TreasurePos = g.getTreasuresPos();
		this.WallPos = g.getWallsPos();
		this.HolePos = g.getHolesPos();
		this.PlayerPos = g.getPlayersPos();
	}
	
	
	
	
	/*
	//Initializate a char tab (used for tests)
	public void init() {
		for(int i = 0; i<this.Lines; i++) {
			tab[0][i] = 'w';
			tab[i][0] = 'w';
		}
		tab[5][5] = 'w';
		tab[3][2] = 'w';
		tab[4][9] = 'w';
		tab[1][1] = 'h';
		tab[10][10] = 'h';
		tab[15][5] = 't';
		System.out.println(tab[10][11]+".");
	}
	
	
	//Display a tab of char (used for tests)
	public void displayTab() {
		for(int j = 0; j<Lines; j++) {
			for(int i = 0; i<Columns; i++) {
				System.out.print(tab[j][i]);
			}
			System.out.println("");
		}
	}
	
	//Generate a random char tab (used for tests)
	public void GenerateRandomBoard() { 
		int x, y;
    	//Place Holes:
    	for(int i = 0; i<7; i++) {
    		Random r = new Random();
    		do {
    		x = r.nextInt((this.Columns));
    		y = r.nextInt((this.Lines));
    		}while(this.tab[y][x]=='h' || this.tab[y][x]=='t' || this.tab[y][x]=='w');
    		tab[y][x] = 'h';
    	}
    	
    	//Place Treasures
    	for(int i = 0; i<7; i++) {
    		Random r = new Random();
    		do {
    		x = r.nextInt((this.Columns));
    		y = r.nextInt((this.Lines));
    		}while(this.tab[y][x]=='h' || this.tab[y][x]=='t' || this.tab[y][x]=='w');
    		this.tab[y][x] = 't';
    	}
    	
    	//Place walls
    	for(int i = 0; i<100; i++) { 
    		Random r = new Random();
    		do {
    			x = r.nextInt(this.Columns);
    			y = r.nextInt(this.Lines); 
    		}while(this.tab[y][x]=='h' || this.tab[y][x]=='t' || this.tab[y][x]=='w');
    		tab[y][x] = 'w';
    	}
    }
    */
}
