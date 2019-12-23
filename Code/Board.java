import java.util.ArrayList;

public class Board {

	public int score;
	public Square[][] wall = new Square[5][5];
	public Square[][] patternLine = new Square[5][];
	public Square[] floor = new Square[7];
	int floorIndex = 0;
	Discards discard;

	/**
	 * Constructor for board
	 */
	public Board(Discards d) {
		discard = d;
		score = 0;
		for (int i = 0; i < wall.length; i++) {
			for (int j = 0; j < wall.length; j++) {
				wall[i][j] = new Square();
			}
		}

		for (int i = 0; i < patternLine.length; i++) {
			patternLine[i] = new Square[i + 1];
			for (int j = 0; j <= i; j++) {
				patternLine[i][j] = new Square();
			}
		}

		for (int i = 0; i < floor.length; i++) {
			floor[i] = new Square();
		}
	}

	/**
	 * checks if a wall's line is full
	 * 
	 * @return true is one line is full, false otherwise
	 */
	public boolean isWallLineFull() {
		for (int i = 0; i < wall.length; i++) {
			int c = 0;
			for (int j = 0; j < wall[i].length; j++) {
				if (!wall[i][j].isEmpty())
					c = c + 1;
			}
			if (c == 5)
				return true;
		}
		return false;
	}

	// if the floor is full, return true, else return false
	public boolean isFloorFull() {
		for (int i = 0; i < floor.length; i++) {
			if (floor[i].isEmpty())
				return false;
		}
		return true;
	}

	// if the indexed patternLine's line is full, return true, else return false
	public boolean isPatternLineFull(int index) {
		for (int i = 0; i < patternLine[index].length; i++) {
			if (patternLine[index][i].isEmpty())
				return false;
		}
		return true;
	}

	public boolean isPatternAddable(int index, char c) {
		if (patternLine[index][0].isEmpty())
			return true;
		if (patternLine[index][0].getColor() != c)
			return false;
		return true;
	}
	
	public boolean isWallColor(int index, char c) {
		if(wall[index][0].isEmpty()) return false; // a changer
		if(wall[index][0].getColor() == c) return true;
		return false;
		
	}

	public boolean canAddPattern(int index, int taille) {
		int count = 0;
		for (int i = 0; i < patternLine[index].length; i++) {
			if (patternLine[index][i].isEmpty())
				count++;
		}
		return count <= taille;
	}

	// public boolean addLines(Tile [] t, int index) { }

	/*
	 * checks if the indexed line's color is already used (on the wall) if so, we
	 * can't add tile with this color at the same patternLine's line
	 */
	public boolean isActivated(char color, int index) {
		for (int i = 0; i < wall.length; i++) {
			if (wall[index][i].getColor() == color)
				return true;
		}
		return false;
	}

	// add tile to the floor when there's too much tile in the patternline

	public void addFloor(Tile[] t) {
		ArrayList<Tile> al = new ArrayList<Tile>();
		for(Tile ts : t) {
			al.add(ts);
		}
		for(int i = floorIndex; i < floor.length - floorIndex;i++) {
			if(al.size() > 0) floor[i].add(al.remove(0));
		}
		if(al.size()>0) {
			Tile[] disc = new Tile[al.size()];
			for(int i = 0; i < al.size();i++)
				disc[i] = al.remove(0);
			refillDiscards(disc);
		}
	}

	public void addPattern(Tile[] t, int index) {
		ArrayList<Tile> al = new ArrayList<Tile>();
		for(Tile ts : t) {
			al.add(ts);
		}
		for(int i = 0; i < patternLine[index].length;i++){
			if(patternLine[index][i].isEmpty() && al.size()>0) patternLine[index][i].add(al.remove(0));
		}
		if(al.size()>0) {
			Tile[] treturn = new Tile[al.size()];
			int i = 0;
			for(Tile tx : al) {
				treturn[i] = tx;
				i++;
			}
			addFloor(treturn);
		}
	}

	//
	public void refillDiscards(Tile[] t) {
		discard.add(t);
	}

	public void boardDisplay() {
		for (int i = 0; i < 5; i++) {
			for (int l = 0; l < 12 - 3 * i; l++)
				System.out.print(" ");
			for (int j = 0; j < patternLine[i].length; j++) {
				char c = ' ';
				if (!patternLine[i][j].isEmpty())
					c = patternLine[i][j].getColor();
				System.out.print("[" + c + "]");
			}
			System.out.print(" | ");
			for (int k = 0; k < wall[i].length; k++) {
				char c = ' ';
				if (!wall[i][k].isEmpty())
					c = wall[i][k].getColor();
				System.out.print("[" + c + "]");
			}
			System.out.println();
		}
		System.out.println();
		for (int m = 0; m < floor.length; m++) {
			char c = ' ';
			if (!floor[m].isEmpty())
				c = floor[m].getColor();
			System.out.print("[" + c + "]");
		}
		System.out.println();
	}
}
