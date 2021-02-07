package Server.Elements;

public class Treasure extends Element {

	private final int value;

	public Treasure(int value) {
		super();
		this.value = value;
	}

	public int getTreasureValue() {
		return this.value;
	}
 
	public String toString() {
		return "Treasure";
	}

}