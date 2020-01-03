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
			for (int j = 0; j < wall[i].length; j++) {
				if (wall[i][j].isEmpty())
					return false;
			}
		}
		return true;
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

    // checks if a pattern can be added to the wall
	public boolean isPatternAddable(int index, char c) {
		if (patternLine[index][0].isEmpty())
			return true;
		if (patternLine[index][0].getColor() != c)
			return false;
		return true;
	}

	public boolean isWallColor(int index, char c) {
		for(int i = 0; i < wall[index].length; i++) {
			if(!wall[index][i].isEmpty() && wall[index][i].getColor() == c) {
				return true;
			}
		}
		return false;

	}

    // Checks if a tile can be added to the indexed patterLIne
	public boolean canAddPattern(int index, int taille) {
		int count = 0;
		for (int i = 0; i < patternLine[index].length; i++) {
			if (patternLine[index][i].isEmpty())
				count++;
		}
		return count <= taille;
	}




	// add tile to the floor when there's too much tile in the patternline

	public void addFloor(Tile[] t) {
		ArrayList<Tile> al = new ArrayList<Tile>();
		for (Tile ts : t) {
			al.add(ts);
		}
		for (int i = floorIndex; i < floor.length - floorIndex; i++) {
			if (al.size() > 0 && floor[i].isEmpty())
				floor[i].add(al.remove(0));
		}
		if (al.size() > 0) {
			Tile[] disc = new Tile[al.size()];
			for (int i = 0; i < al.size(); i++)
				disc[i] = al.remove(0);
			refillDiscards(disc);
		}
	}

    // add the pattern to the indexed wall's line
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

	// refill the discard
	public void refillDiscards(Tile[] t) {
		discard.add(t);
	}

	public void refillDiscards(Tile t){
		discard.add(t);
	}

    // textual dysplay for the board
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


    // getter for the wall
	public Square[] getFloor() {
		return floor;
	}

    // getter for the patten
	public Square[][] getPattern() {
		return patternLine;
	}

	// getter fir the wall
	public Square[][] getWall(){
		return wall;
	}

	// cound the score from floor
	public void countFloor() {
		for (int i = 0; i < floor.length; i++) {
			if (!floor[i].isEmpty()) {
				System.out.println(score);
				if (i == 0 || i == 1)
					score-=1;
				if (i == 2 || i == 3 || i == 4)
					score-=2;
				if (i == 5 || i == 6)
					score-=3;
			}
		}
	}

	// uptades score according to horizontal adjacent tiles on the wall
	public void countHozirontalAdja(int l, int c) {
		if (c - 1 >= 0 && c + 1 < 5) {
			if (!wall[l][c - 1].isEmpty())
				score = score + 1;
			if (!wall[l][c + 1].isEmpty())
				score = score + 1;
		}
	}

	// uptades score according to vertical adjacent tiles on the wall
	public void countVerticalAdja(int l, int c) {
		if (l - 1 >= 0 && l + 1 < 5) {
			if (!wall[l - 1][c].isEmpty())
				score = score + 1;
			if (!wall[l + 1][c].isEmpty())
				score = score + 1;
		}
	}

	// for each wall's line full, gain 2 points
	public void countWallLineFull(int l) {
		if (isWallLineFull(l))
			score = score + 2;
	}

	// for each wall's collumn full, gain 2 points
	public void countWallCollumnFull(int c) {
		if (isWallCollumnFull(c))
			score = score + 7;
	}

	public void countWallColorFull() {
		char[] color = { 'r', 'b', 'y', 'w', 'n' };
		for (int i = 0; i < color.length; i++) {
			if (isColorWallFull(color[i]) == true)
				score = score + 10;
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

    // checks if a color is fully completed on the wall
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

    // indexes the right color to the right square in the wall
	public void addWall(int l, Tile t) {
		/* 0 is blue, 1 yellow, 2 red, 3 black, 4 white */
		char c = t.getColor();
		int j = 0;
		switch (c) {
		case 'b':
			j = l;
			break;
		case 'y':
			j = (l + 1) % 5;
			break;
		case 'r':
			j = (l + 2) % 5;
			break;
		case 'n':
			j = (l + 3) % 5;
			break;
		case 'w':
			j = (l + 4) % 5;
			break;
		}
		wall[l][j].add(t);
		countPoint(l, j);
	}

    // counter for points
	public void countPoint(int l, int c) {
		score++;
		countHozirontalAdja(l,c);
		countVerticalAdja(l,c);
	}

	// empty the floor
	public void emptyFloor() {
		countFloor();
		for(int i = 0; i < floor.length; i++) {
			if(!floor[i].isEmpty()) refillDiscards(floor[i].remove());
		}
	}

    // keep 1 tile from a fully completed patternLine and puts the rest in the discard
	public Tile emptyPattern(int l) {
		Tile t = patternLine[l][0].remove();
		for (int i = 1; i < patternLine[l].length; i++) {
			if(!patternLine[l][i].isEmpty()) refillDiscards(patternLine[l][i].remove());
		}
		return t;
	}

	// getter for the score
	public int getScore() {
		return score;
	}
}
