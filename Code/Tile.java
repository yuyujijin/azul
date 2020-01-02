import java.awt.Color;

public class Tile{
	char color;
	
	public Tile(char c) {
		color = c;
	}
	
	public char getColor() {
		return color;
	}
	
	public Color getRGBColor() {
		if(color=='w') return new Color(250,250,250);
		if(color=='b') return new Color(186,225,255);
		if(color=='r') return new Color(255,100,116);
		if(color=='y') return new Color(255,255,186);
		if(color=='f') return new Color(186,255,201); //first player
		return new Color(80,80,80);
	}
}