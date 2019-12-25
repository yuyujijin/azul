import java.util.ArrayList;

public class Board {

	public static int score;
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

	// checks if a wall's line is full
	public boolean isWallLineFull(int l) {
		int c = 0;
		for (int i = 0; i < wall[l].length; i++) {
			if (!wall[l][i].isEmpty())
				c = c + 1;
		}
		if (c == 5)
			return true;

		return false;
	}

	// checks if a wall's collumn is full
	public boolean isWallCollumnFull(int l) {
		int c = 0;
		for (int i = 0; i < wall[l].length; i++) {
			if (!wall[i][l].isEmpty())
				c = c + 1;
		}
		if (c == 5)
			return true;

		return false;
	}

	public boolean isColorWallFull(char color) {
		int c = 0;
		for (int i = 0; i < wall.length; i++) {
			for (int j = 0; j < wall[i].length; j++) {
				if (wall[i][j].getColor() == color) {
					c = c + 1;
				}
				if (c == 5)
					return true;
			}
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
		if (wall[index][0].isEmpty())
			return false; // a changer
		if (wall[index][0].getColor() == c)
			return true;
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
		for (Tile ts : t) {
			al.add(ts);
		}
		for (int i = floorIndex; i < floor.length - floorIndex; i++) {
			if (al.size() > 0)
				floor[i].add(al.remove(0));
		}
		if (al.size() > 0) {
			Tile[] disc = new Tile[al.size()];
			for (int i = 0; i < al.size(); i++)
				disc[i] = al.remove(0);
			refillDiscards(disc);
		}
	}

	public void addPattern(Tile[] t, int index) {
		ArrayList<Tile> al = new ArrayList<Tile>();
		for (Tile ts : t) {
			al.add(ts);
		}
		for (int i = 0; i < patternLine[index].length; i++) {
			if (patternLine[index][i].isEmpty() && al.size() > 0)
				patternLine[index][i].add(al.remove(0));
		}
		if (al.size() > 0) {
			Tile[] treturn = new Tile[al.size()];
			int i = 0;
			for (Tile tx : al) {
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

	// cound the score from floor
	public void countFloor() {
		for (int i = 0; i < floor.length; i++) {
			if (floor[i] != null) {
				if (i == 0 || i == 1)
					score = score - 1;
				if (i == 2 || i == 3 || i == 4)
					score = score - 2;
				if (i == 5 || i == 6)
					score = score - 3;
			}
		}
	}

	// uptades score according to horizontal adjacent tiles on the wall
	public void countHozirontalAdja() {
		for (int i = 0; i < wall.length; i++) {
			for (int j = 0; j < wall[i].length; i++) {
				if (j - 1 != 0 && j + 1 < 5) {
					if (wall[i][j - 1] != null)
						score = score + 1;
					if (wall[i][j + 1] != null)
						score = score + 1;
				}
			}
		}
	}

	// uptades score according to vertical adjacent tiles on the wall
	public void countVerticalAdja() {
		for (int i = 0; i < wall.length; i++) {
			for (int j = 0; j < wall[i].length; i++) {
				if (i - 1 != 0 && i + 1 < 5) {
					if (wall[i - 1][j] != null)
						score = score + 1;
					if (wall[i + 1][j] != null)
						score = score + 1;
				}
			}
		}
	}

	// for each wall's line full, gain 2 points
	public void countWallLineFull() {
		for (int i = 0; i < wall.length; i++) {
			if (isWallLineFull(i) == true)
				score = score + 2;
		}
	}

	// for each wall's collumn full, gain 2 points
	public void countWallCollumnFull() {
		for (int i = 0; i < wall.length; i++) {
			if (isWallCollumnFull(i) == true)
				score = score + 7;
		}
	}

	public void countWallColorFull() {
		char[] color = { 'r', 'b', 'g', 'w', 'n' };
		for (int i = 0; i < color.length; i++) {
			if (isColorWallFull(color[i]) == true)
				score = score + 10;
		}
	}

}