package Server.Elements;

public class Treasure extends Element {

	private final int value;

	public Treasure(int value) {
		this.value = value;
	}

	public int getTreasureValue() {
		return this.value;
	}
 
	public String toString() {
		return "T";
	}

}
