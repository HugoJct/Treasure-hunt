package Server;

import Server.Elements.Element;

public class Board {
    private Element[][] elements;

    //Constructor
    public Board() {
    	fillEmpty();
    }

    private void fillEmpty() {
    	for(int i=0;i<elements.length;i++)
    		for(int j=0; j<elements[i].length;j++)
    			elements[i][j] = null; //new Element();
    }

    protected void setElementAt(Element elem, int x, int y) {
    	elements[x][y] = elem;
    }

    public Element getElementAt(int x, int y) {
    	return elements[x][y];
    }
    
    //Getter of the two dimensional array
    public Element[][] getElement(){
    	return this.elements;
    }

    public String toString() {
    	for(int i=0;i<elements.length;i++)
    		for(int j=0; j<elements[i].length;j++)
    			return elements[i][j].toString();
		return null;
    }

}