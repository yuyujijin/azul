import java.awt.Color;

public class Square{
	
	private Tile t;

	public Square(){
		t = null;
	}

	public void add(Tile t){
		this.t = t;
	}

	public Tile remove(){
		Tile s = t;
		t = null;
		return s;
	}

	public boolean isEmpty(){
		return(t==null);
	}
	
	public char getColor() {
		return t.getColor();
	}
	
	public Color getRGBColor() {
		return t.getRGBColor();
	}
	
}