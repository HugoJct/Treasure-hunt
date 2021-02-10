package server;

import server.elements.Element;

public class Board {
    private Element[][] elements;

    //Constructor
    private final int sizeX;
    private final int sizeY;

    public Board(int x, int y) {
    	this.sizeX = x;
    	this.sizeY = y;
		elements = new Element[y][x];
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