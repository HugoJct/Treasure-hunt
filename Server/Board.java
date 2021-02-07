package Server;

import Server.Elements.Element;

public class Board {
    private Element[][] elements;

    public Board() {
    	fillEmpty();
    }

    public void fillEmpty() {
    	for(int i=0;i<elements.length;i++)
    		for(int j=0; j<elements[i].length;j++)
    			elements[i][j] = new Element();
    }

    public String toString() {
    	for(int i=0;i<elements.length;i++)
    		for(int j=0; j<elements[i].length;j++)
    			return elementss[i][j].toString();
    }

}