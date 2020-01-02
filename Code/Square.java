import java.awt.Color;

public class Square{
	
	
	private Tile t;

	// constructor for the square
	public Square(){
		t = null;
	}

	// adding a tile to a square
	public void add(Tile t){
		this.t = t;
	}

	// removes a tile 
	public Tile remove(){
		Tile s = t;
		t = null;
		return s;
	}

	// checks is a square is empty
	public boolean isEmpty(){
		return(t==null);
	}
	
	// gets the color of a square
	public char getColor() {
		return t.getColor();
	}
	
	// get the rgb color for the square
	public Color getRGBColor() {
		return t.getRGBColor();
	}
	
}