package server.playingProps;

import server.elements.*;
import java.util.Random;

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

  public boolean placeElementRandomly(Element e){ //Places an element randomly on an empty case
    Random rand = new Random();
    int pos[]={-1,-1};
    do{
      pos[0] = rand.nextInt(this.getSizeY()-2);
      pos[1] = rand.nextInt(this.getSizeX()-2);
    }while(this.getElementAt(pos[1],pos[0])!=null);
    this.setElementAt(e,pos[1],pos[0]);
    return true;
  }

  public boolean fillElements(int hole, int tres){ // function that's used for the constructor we call upon for "110 CREATE", Only works with a board that has no holes or Treasures yet
    int nbrTres = 0;
    int nbrHole = 0;
    while(nbrTres != tres || nbrHole != hole){
      if(nbrTres < tres){
        placeElementRandomly(new Treasure(100));
        nbrTres++;
      }
      if(nbrHole < hole){
        placeElementRandomly(new Hole());
        nbrHole++;
      }
    }
    return true;
  }

  public boolean fillWalls(){ //Wall generation on an empty board 
    int[] posCenter = new int[2];
    posCenter[0] = (this.elements.length - 1)/2;
    posCenter[1] = (this.elements[posCenter[0]].length - 1)/2;
    this.fillWallsInter(posCenter,"nw");
    this.fillWallsInter(posCenter,"ne");
    this.fillWallsInter(posCenter,"sw");
    this.fillWallsInter(posCenter,"se");
    return true;
  }

  public boolean fillWallsInter(int posCenter[], String s){//Intermediary function for fillWalls NB:Works only on a board With minimum 8*8 size
    int x = posCenter[1];
    int y = posCenter[0];
    int it = 0;
    if(s.equals("nw")){
      x = x - 2;
      y = y - 2;
      while(this.withinBorders(x,y)){
        this.setElementAt(new Wall() ,x,y);
        this.setElementAt(new Wall() ,x+1,y);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x+1+i,y);
        }
        this.setElementAt(new Wall() ,x,y+1);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x,y+1+i);
        }
        it+=2;
        x = x - 2;
        y = y - 2;
      }
      return true;
    }
    if(s.equals("ne")){
      x = x + 2;
      y = y - 2;
      while(this.withinBorders(x,y)){
        this.setElementAt(new Wall() ,x,y);
        this.setElementAt(new Wall() ,x-1,y);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x-1-i,y);
        }
        this.setElementAt(new Wall() ,x,y+1);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x,y+1+i);
        }
        it+=2;
        x = x + 2;
        y = y - 2;
      }
      return true;
    }
    if(s.equals("sw")){
      x = x - 2;
      y = y + 2;
      while(this.withinBorders(x,y)){
        this.setElementAt(new Wall() ,x,y);
        this.setElementAt(new Wall() ,x+1,y);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x+1+i,y);
        }
        this.setElementAt(new Wall() ,x,y-1);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x,y-1-i);
        }
        it+=2;
        x = x - 2;
        y = y + 2;
      }
      return true;
    }
    if(s.equals("se")){
      x = x + 2;
      y = y + 2;
      while(this.withinBorders(x,y)){
        this.setElementAt(new Wall() ,x,y);
        this.setElementAt(new Wall() ,x-1,y);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x-1-i,y);
        }
        this.setElementAt(new Wall() ,x,y-1);
        for(int i=1;i<=it;i++){
          this.setElementAt(new Wall() ,x,y-1-i);
        }
        it+=2;
        x = x + 2;
        y = y + 2;
      }
      return true;
    }
    return false;
  }

  public boolean withinBorders(int pos[]){
    return (pos[0] < this.elements.length-1 && pos[0] > 0 && pos[1] < this.elements[0].length-1 && pos[1] > 0);
  }

  public boolean withinBorders(int x,int y){
    int pos[] = new int[2];
    pos[0] = y;
    pos[1] = x;
    return withinBorders(pos);
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
