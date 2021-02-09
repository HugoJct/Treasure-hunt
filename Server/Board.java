package Server;

import Server.Elements.Element;

public class Board {
    private Element[][] elements;
    private final int sizeX;
    private final int sizeY;

    public Board(int x, int y) {
    	this.sizeX = x;
    	this.sizeY = y;
		elements = new Element[y][x];
    }

    protected void setElementAt(Element elem, int x, int y) {
    	elements[x][y] = elem;
    }

    public Element getElementAt(int x, int y) {
    	return elements[x][y];
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