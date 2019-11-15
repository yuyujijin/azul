public class Board {

	public int score;
	public Square [][] wall = new Square [5][5];
	public Square [][] patternLine = new Square [5][];
	public Square [] floor = new Square [7];
	
    /**
     * Constructor to initialize the board
     * /
	public Board() {
		score=0;
		for (int i=0; i<wall.length; i++) {
			for (int j=0; j<wall.length; j++) {
				wall[i][j] = new Square();
			}
		}
		
		for (int i=0; i<patternLine.length; i++) {
			for (int j=0; j<patternLine.length; j++) {
				patternLine[i][j] = new Square();
			}
		}
		
		for (int i=0; i<wall.length; i++) {
				floor[i] = new Square();
			}
	}
	
	/**
	 * Add the needed methods
	 */
}