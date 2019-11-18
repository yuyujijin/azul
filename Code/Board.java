
public class Board {

	public int score;
	public Square [][] wall = new Square [5][5];
	public Square [][] patternLine = new Square [5][];
	public Square [] floor = new Square [7];
	
	/** 
	 * Constructor for board
	 */
	public Board() {
		score=0;
		for (int i=0; i<wall.length; i++) {
			for (int j=0; j<wall.length; j++) {
				wall[i][j] = new Square();
			}
		}
		
		for (int i=0; i<patternLine.length; i++) {
			for (int j=0; j<=i; j++) {
				patternLine[i][j] = new Square();
			}
		}
		
		for (int i=0; i<wall.length; i++) {
				floor[i] = new Square();
			}
	}
	
	/**
	 * checks if a wall's line is full
	 * @return true is one line is full, false otherwise
	 */
	public boolean isWallLineFull() {
		for (int i=0; i<wall.length; i++) {
			int c=0;
			for (int j=0; j<wall[i].length; j++) {
				if (!wall[i][j].isEmpty()) c=c+1;
			}
			if (c==5) return true;
		}
		return false;
	}
}