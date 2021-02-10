package Server;

import Server.Elements.Element;
import Server.Elements.Wall;

public class Board {
    private Element[][] elements;

    //Constructor
    private final int sizeX;
    private final int sizeY;

    public Board(int x, int y) {
    	this.sizeX = x+2;
    	this.sizeY = y+2;
		elements = new Element[this.sizeY][this.sizeX];
		setBorder();
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