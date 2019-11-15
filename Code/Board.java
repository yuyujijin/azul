
public class Board {

	public int score;
	public Square [][] wall = new square [5][5];
	public Square [][] patternLine = new Square [5][];
	public Square [] floor = new Square [7];
	

	public Board() {
		score=0;
		for (int i=0; i<wall.length; i++) {
			for (int j=0; j<wall.lengtj; j++) {
				wall[i][j] = new Square();
			}
		}
		
		for (int i=0; i<patternLine.length; i++) {
			for (int j=0; j<patternLine.lengtj; j++) {
				patternLine[i][j] = new Square();
			}
		}
		
		for (int i=0; i<wall.length; i++) {
				floor[i] = new Square();
			}
	}
}