package application;

import java.util.Random;



public class Modele {
	int Lines;
	int Columns;
	char tab[][];
	
	
	
	public Modele(int Lines, int Columns) {
		this.Lines = Lines;
		this.Columns = Columns;
		this.tab = new char[this.Columns][this.Lines];
		//this.init();
		this.GenerateRandomBoard();
	}
	
	
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
	
	
	public void displayTab() {
		for(int j = 0; j<Lines; j++) {
			for(int i = 0; i<Columns; i++) {
				System.out.print(tab[j][i]);
			}
			System.out.println("");
		}
	}
	
	
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
}
