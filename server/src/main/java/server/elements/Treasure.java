package server.elements;

public class Treasure extends Element {

	private final int value;

	public Treasure(int value) {
		this.value = value;
	}
        public void setTreasureValue(int v){
	    this.value = v; //used to set a treasure value to zero once it's taken by a player
        }


        public int getTreasureValue() {
		return this.value;
	}
    
	public String toString() {
		return "T";
	}

}
