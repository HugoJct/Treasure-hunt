package server;

import server.elements.*;

public class Board {
  private Element[][] elements;

  //Constructor
  private final int sizeX;
  private final int sizeY;

  private int wallCount = 0;
  private int holeCount = 0;
  private int treasureCount = 0;

  public Board() {
    this.sizeX = 0;
    this.sizeY = 0;
  }

  public Board(int x, int y) {
    this.sizeX = x+2;
    this.sizeY = y+2;
    this.elements = new Element[this.sizeY][this.sizeX];
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

  public void setElementAt(Element elem, int x, int y) {
    elements[y][x] = elem;
    countElements();
  }

  public Element getElementAt(int x, int y) {
    return elements[y][x];
  }

  public void countElements() {
    int newWallCount = 0;
    int newHoleCount = 0;
    int newTreasureCount = 0;
    for(int i=1;i<elements.length-1;i++) {
      for(int j=1;j<elements[i].length-1;j++) {
        if(elements[i][j] instanceof Wall)
          newWallCount++;
        if(elements[i][j] instanceof Hole)
          newHoleCount++;
        if(elements[i][j] instanceof Treasure)
          newTreasureCount++;
      }
    }
    this.holeCount = newHoleCount;
    this.wallCount = newWallCount;
    this.treasureCount = newTreasureCount;
    System.out.println("walls: "+wallCount+" holes: "+holeCount+" tres: "+treasureCount);
  }

  public int[][] getWallPos() {
    countElements();
    int[][] wallPos = new int[wallCount][2];
    int x = 0;
    for(int i=1;i<elements.length-1;i++) {
      for(int j=1;j<elements[i].length-1;j++) {
        if(elements[i][j] instanceof Wall) {
          wallPos[x][0] = i;
          wallPos[x][1] = j;
          x++;
        }
      }
    }
    return wallPos;
  }

  public int[][] getHolePos() {
    countElements();
    int[][] holePos = new int[holeCount][2];
    int x = 0;
    for(int i=1;i<elements.length-1;i++) {
      for(int j=1;j<elements[i].length-1;j++) {
        if(elements[i][j] instanceof Hole) {
          holePos[x][0] = i;
          holePos[x][1] = j;
          x++;
        }
      }
    }
    return holePos;
  }

  public int[][] getTreasurePos() {
    countElements();
    int[][] trePos = new int[treasureCount][3];
    int x = 0;
    for(int i=1;i<elements.length-1;i++) {
      for(int j=1;j<elements[i].length-1;j++) {
        if(elements[i][j] instanceof Treasure) {
          trePos[x][0] = i;
          trePos[x][1] = j;
          trePos[x][2] = ((Treasure) elements[i][j]).getTreasureValue();
          x++;
        }
      }
    }
    return trePos;
  }

  public int sumAllTreasures(){
    int sum = 0;
    /*
    for(int i=0;i<elements.length;i++){
      for(int j=0;j<elements[i].length;j++){
        if(elements[i][j] instanceof Treasure){
        
        Treasure tmp = (Treasure) this.elements[i][j];
        sum += tmp.getTreasureValue();
        }
      }
    }*/

    int[][] tres = getTreasurePos();
    for(int i=0;i<tres.length;i++) {
        sum += tres[i][2];
    }

    return sum;
  }
  
  public String toString() {
    String retour = "";
      for(int i=0;i<elements.length;i++) {
        for(int j=0; j<elements[i].length;j++) {
          if(elements[i][j] != null) {
            retour += elements[i][j].toString()+" ";
          }
          else {
            retour += ". ";
          }
        }
        retour +="\n";
      }
    return retour+="\n";
  }

  public int getSizeX(){
	  return this.sizeX;
  }

  public int getSizeY(){
	  return this.sizeY;
  }

  public int getWallCount() {
    return wallCount;
  }

  public int getHoleCount() {
    return holeCount;
  }

  public int getTreasureCount() {
    return treasureCount;
  }
    
  //Getter of the two dimensional array
  public Element[][] getElement(){
    return this.elements;
  }
}
