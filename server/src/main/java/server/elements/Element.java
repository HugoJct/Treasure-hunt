package server.elements;

public abstract class Element {
	private char label;

	public Element(char c) {
		this.label = c;
	}

	public String toString() {
		return ""+label;
	}
}
