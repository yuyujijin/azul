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
	
	// if the floor is full, return true, else return false
	public boolean isFloorFull() {
		for (int i=0; i<floor.length; i++) {
			if (floor[i].isEmpty()) return false;
		}
		return true;
	}
	
	// if the indexed patternLine's line is full, return true, else return false
	public boolean isPatternLineFull(int index) {
		for (int i=0; i<patternLine.length; i++) {
			if (patternLine[index][i].isEmpty()) return false;
		}
		return true;
	}
	
    //	public boolean addLines(Tile [] t, int index) { } 
	
	/*
	 * checks if the indexed line's color is already used (on the wall)
	 * if so, we can't add tile with this color at the same patternLine's line
	 */
	public boolean isActivated(char color, int index) {
		for (int i=0; i<wall.length; i++) {
			if (wall[index][i].getColor()==color) return true;
		}
		return false;
	}
	
	// add tile to the floor when there's too much tile in the patternline
	public void addFloor(Tile[] t) {
		
	}
		
	// 
	public void refillBag() {
	}
}