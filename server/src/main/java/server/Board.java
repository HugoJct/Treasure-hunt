package server;

import server.elements.*;

public class Board {
    private Element[][] elements;

    //Constructor
    private final int sizeX;
    private final int sizeY;

    public Board() {        //default constructor generates the default board

        this.sizeX = 15+2;
        this.sizeY = 15+2;
        elements = new Element[this.sizeY][this.sizeX];

        //The following is the board featured on the screenshot from the project's video presentation
        
        //Walls
       this.setElementAt(new Wall(), 6, 2);
       this.setElementAt(new Wall(), 5, 4);
       this.setElementAt(new Wall(), 5, 5);
       this.setElementAt(new Wall(), 8, 6);
        
       this.setElementAt(new Wall(), 12, 7);
       this.setElementAt(new Wall(), 12, 8);
       this.setElementAt(new Wall(), 12, 9);
       this.setElementAt(new Wall(), 12, 10);
       this.setElementAt(new Wall(), 11, 10);
        
       this.setElementAt(new Wall(), 5, 11);
       this.setElementAt(new Wall(), 5, 12);
       this.setElementAt(new Wall(), 5, 13);
       this.setElementAt(new Wall(), 6, 13);
       this.setElementAt(new Wall(), 6, 14);

        //Treasures
       this.setElementAt(new Treasure(15), 9, 2);
       this.setElementAt(new Treasure(5), 5, 5);
       this.setElementAt(new Treasure(10), 14, 5);
       this.setElementAt(new Treasure(0), 10, 6);
       this.setElementAt(new Treasure(20), 3, 12);
       this.setElementAt(new Treasure(0), 9, 12);
       this.setElementAt(new Treasure(5), 6, 9);
       this.setElementAt(new Treasure(0), 13, 10);

        //Holes
       this.setElementAt(new Hole(), 3, 9);
       this.setElementAt(new Hole(), 6, 7);
       this.setElementAt(new Hole(), 9, 5);
       this.setElementAt(new Hole(), 8, 10);
       this.setElementAt(new Hole(), 10, 14);
       this.setElementAt(new Hole(), 15, 14);

       this.setBorder();
    }

    public Board(int x, int y) {
    	this.sizeX = x+2;
    	this.sizeY = y+2;
		this.elements = new Element[this.sizeY][this.sizeX];
		this.setBorder();
    }

	public void setBorder() {
		for (int i = 0 ; i < sizeY ; i++) {
			this.elements[i][0] = new Wall();
			this.elements[i][sizeX-1] = new Wall();
		}
		for (int i = 0 ; i < sizeX ; i++) {
			this.elements[sizeY-1][i] = new Wall();
			this.elements[0][i] = new Wall();
		}
	}

    protected void setElementAt(Element elem, int x, int y) {
    	elements[y][x] = elem;
    }

    public Element getElementAt(int x, int y) {
    	return elements[y][x];
    }
    
    //Getter of the two dimensional array
    public Element[][] getElement(){
    	return this.elements;
    }

    public String toString() {
    	String retour = "";
    	for(int i=0;i<elements.length;i++) {
    		for(int j=0; j<elements[i].length;j++) {
				if(elements[i][j] != null)
    				retour += elements[i][j].toString()+" ";
				else
					retour += ". ";
    		}
    		retour +="\n";
    	}
    	return retour+="\n";
    }
    

}
