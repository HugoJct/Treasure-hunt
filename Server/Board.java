package Server;

import Server.Elements.Element;

public class Board {
    private Element[][] elements;
    private final int sizeX;
    private final int sizeY;

    public Board(int x, int y) {
    	this.sizeX = x;
    	this.sizeY = y;
    }

    protected void setElementAt(Element elem, int x, int y) {
    	elements[x][y] = elem;
    }

    public Element getElementAt(int x, int y) {
    	return elements[x][y];
    }

    public String toString() {
    	for(int i=0;i<elements.length;i++)
    		for(int j=0; j<elements[i].length;j++)
    			return elementss[i][j].toString();
    }

}