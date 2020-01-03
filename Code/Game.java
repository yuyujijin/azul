import java.util.ArrayList;
import java.util.Scanner;

/* this class contains every of the methods to make the game works */

public class Game {
	Player[] players;
	ArrayList<Tile> center;
	Factory[] factories;
	Bag bag;
	Discards discards;
	Controller controler;
	int round = 0;
	boolean firstTilePicked;

	/* first is the index of the player starting the round */

	int first;

	/* active player is used to check which player is actually playing */

	int activePlayer;
	public boolean hasWon;

	/* We instantiate every of its attribute */

	public Game(int p, Controller controler, boolean joker) {
		this.controler = controler;
		players = new Player[p];
		discards = new Discards();
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(discards);
		}
		factories = new Factory[getFactoriesNbr()];
		for (int i = 0; i < factories.length; i++) {
			factories[i] = new Factory();
		}
		center = new ArrayList<Tile>();
		bag = new Bag();
		hasWon = false;
		firstTilePicked= false;
	}

	public void phase1() {

		// give random player 1st player tile;

		firstTilePicked = false;

		if (round == 0)
			first = (int) Math.random() * (players.length+1 - 0);
		;

		activePlayer = first;

		/* 1st phase */

		if (bag.isEmpty()) {
			if (!discards.isEmpty()) {
				bag.refill(discards.empty());
			} else {
				// stop the 1st phase
			}
		}

		/* We then fill up every factories */

		for (Factory f : factories) {
			int i = 0;
			while (i < 4 && !bag.isEmpty()) {
				f.add(bag.pick());
				i++;
			}
		}
	}

	/* This methods now process to do the 3rd phase, which reset everything and count points */

	public void phase3() {
		for (int i = first; i < players.length + first; i++) {
			Board b = players[i % players.length].getBoard();
			for (int j = 0; j < 5; j++) {
				if (b.isPatternLineFull(j)) {
					b.addWall(j,b.emptyPattern(j));
				}
				if(b.isWallLineFull(j)) hasWon = true;
			}
			b.emptyFloor();
		}
	}

	/* This adds the first player tile in the center */

	public void putFirstPlayerTile() {
		center.add(new TileFirstPlayer());
	}

	public void tileFirstPicked() {
		firstTilePicked = true;
		first = activePlayer; //player who picked it now becomes the next first player
		Tile[] t = new Tile[1];
		t[0] = center.remove(0);
		players[activePlayer].addFloor(t); //removing the first player tile
	}

	/* The active player picks the tiles in argument */

	public void pickPlayer(Tile[] t) {
		players[activePlayer].pick(t);
	}

	/* Make the next player plays, % players.length (aka number of players) */

	public void nextPlayer() {
		activePlayer = (activePlayer + 1) % players.length;
	}

	/* Booleans methods */

	/* Check if factories and center are empty, return true if so */

	public boolean canPhase3() {
		for (Factory f : factories) {
			if (!f.isEmpty())
				return false;
		}
		if (!center.isEmpty())
			return false;
		return true;
	}

	/* Did somebody won? */

	public boolean hasWon() { return hasWon; }

	public boolean firstPicked() { return firstTilePicked; }

	/* Check if the active player can put what he has in his hands in the pattern line i
	* following azul rules
	*/

	public boolean canAddPattern(int i) {
		return (players[activePlayer % players.length].getBoard().isPatternAddable(i,
				players[activePlayer % players.length].handColor())
				&& !(players[activePlayer % players.length].getBoard().isWallColor(i,
						players[activePlayer % players.length].handColor())));
	}

	/* Used in textual mode, to check if you can pick color c in center */

	public boolean centerHasColor(char c) {
		for (Tile t : center) {
			if (t.getColor() == c)
				return true;
		}
		return false;
	}

	/* Same here, with color c and factory i */

	public boolean factoryHasColor(char c, int i) {
		ArrayList<Tile> factory = factories[i].getTile();
		for (Tile t : factory) {
			if (t.getColor() == c)
				return true;
		}
		return false;
	}

	/* Check if center is empty */

	public boolean centerIsEmpty() {
		return center.isEmpty();
	}

	/* Return players[i]'s  score */

	public int getScore(int i) {
		return players[i].getBoard().getScore();
	}

	/* Count the score of every players, using the end game count from Azul's rules */

	public void finalCount() {
		for(int i = 0; i < players.length; i++) {
			for(int j = 0 ; j < 5 ;j ++) {
				players[i].getBoard().countWallCollumnFull(j);
				players[i].getBoard().countWallLineFull(j);
			}
		}
	}

	/* Check who won (who as the maximum ammount of points) */

	public int getWinner() {
		int winner = 0;
		for(int i = 1 ; i < players.length ; i++) {
			if(players[i].getBoard().getScore() > players[winner].getBoard().getScore()) winner = i;
		}
		return winner;
	}

	/* Deposit players[i]'s hand in his floor */

	public void depositFloor(int i) {
		players[i].addFloor(players[i].emptyHand());
	}

	/* Deposit players[i]'s hand in his l pattern line */

	public void depositPattern(int i, int l) {
		players[i % players.length].getBoard().addPattern(players[i % players.length].emptyHand(), l);
	}

	/* Retturn board of players[i] */

	public Board getBoard(int i) {
		return players[i].getBoard();
	}

	/* Used in textual mode, to select a color */

	public char colorPicker() {
		String c;
		Scanner color = new Scanner(System.in);
		c = color.next();
		while (!c.equals("w") && !c.equals("r") && !c.equals("b") && !c.equals("g") && !c.equals("n")) { // mettre toute
																											// les
																											// couleurs
																											// ici
			System.out.println("Mauvaise couleur");
			c = color.next();
		}
		return c.charAt(0);
	}

	/* Used in textual mode, to select a factory */

	public int factoryPicker() {
		int c;
		Scanner nbr = new Scanner(System.in);
		c = Integer.parseInt(nbr.next());
		while (c < 0 && c > factories.length && !factories[c].isEmpty()) {
			System.out.println("Fabrique non existante ou vide.");
			c = Integer.parseInt(nbr.next());
		}
		return c;
	}

	/* Select a color c, and pick every tiles in the center from them and return them */

	public Tile[] pickFromCenter(char c) {
		if(!firstPicked()){
			tileFirstPicked();
		}
		ArrayList<Tile> ts = new ArrayList<Tile>();
		for (int i = 0; i < center.size(); i++) {
			if (center.get(i).getColor() == c) {
				ts.add(center.remove(i));
				i--;
			}
		}
		return ts.toArray(new Tile[ts.size()]);
	}

	/* Select a color c and a int i, and pick every tiles in the factory i, empty factory and return them */

	public Tile[] pickFromFactory(char c, int i) {
		Tile[] t = factories[i].pick(c);
		Tile[] rest = factories[i].rest();
		if(rest != null && rest.length > 0) addCenter(rest);
		return t;
	}
	public void addCenter(Tile[] t) {
		if (t.length > 0)
			for (Tile ts : t) {
				center.add(ts);
			}
	}


	/* this method is used to play the game textually, used when firs coding */

	public void textualRound() {

		phase1();

		/*
		 * We now make every players play starting from the first one to the last one
		 * (the one before him in the array) using %
		 */
		while (!canPhase3()) {
			for (int i = first; i < players.length + first; i++) {

				activePlayer = i;

				System.out.println("Tour du Joueur #" + (i % (players.length) + 1));

				textualDisplay();

				picking(i % (players.length));

				deposit(i % (players.length));
			}
		}

		phase3();
	}

	/* This methods is used to pick in texutal mode */

	public void picking(int i) {
		if (!center.isEmpty()) {
			System.out.println("Piocher depuis : ");
			System.out.println("Fabriques 'f' ou Centre 'c'?");
			Scanner sc = new Scanner(System.in);
			String s = sc.next();
			while (!s.equals("f") && !s.equals("c")) {
				System.out.println("Réponse non valable;");
				System.out.println("Format : Fabrique 'f' ou centre 'c'.");
				s = sc.next();
			}

			/* if chosen center */

			if (s.equals("c")) {
				/* choose a color */
				System.out.println("Veuillez choisir une couleur");
				char c = colorPicker();
				/* check if color is in center */
				while (!centerHasColor(c)) {
					System.out.println("Couleur non existante dans le centre");
					c = colorPicker();
				}
				/* player pick from center the color */
				players[i].pick(pickFromCenter(c));
			}

			/* if chosen factories */

			if (s.equals("f")) {
				System.out.println("Veuillez choisir une fabrique");
				int nbr = factoryPicker();
				System.out.println("Veuillez choisir une couleur");
				char c = colorPicker();
				while (!factoryHasColor(c, nbr - 1)) {
					System.out.println("Couleur non existante dans la fabrique");
					System.out.println("Veuillez rechoisir une fabrique");
					nbr = factoryPicker();
					System.out.println("Veuillez rechoisir une couleur");
					c = colorPicker();
				}
				players[i % players.length].pick(pickFromFactory(c, nbr - 1));
			}
		} else {

			/* when center is empty */

			System.out.println("Veuillez choisir une fabrique");
			int nbr = factoryPicker();
			System.out.println("Veuillez choisir une couleur");
			char c = colorPicker();
			while (!factoryHasColor(c, nbr - 1)) {
				System.out.println("Couleur non existante dans la fabrique");
				System.out.println("Veuillez rechoisir une fabrique");
				nbr = factoryPicker();
				System.out.println("Veuillez rechoisir une couleur");
				c = colorPicker();
			}
			players[i % players.length].pick(pickFromFactory(c, nbr - 1));
		}
	}

	/* This methos is used when using the game textually */

	public void deposit(int i) {

		/* Now you got to deposit */

		/* Wall or floor */

		Scanner wf = new Scanner(System.in);
		System.out.println("Déposer dans la Pattern 'd' ou le sol 's'?");
		String ds = wf.next();
		while (!ds.equals("d") && !ds.equals("s")) {
			System.out.println("Réponse non valable;");
			System.out.println("Format : Pattern 'd' ou sol 's'");
			ds = wf.next();
		}
		if (ds.equals("s")) {
			System.out.println("floor");
			players[i].addFloor(players[i].emptyHand());
		}
		if (ds.equals("d")) {
			/* you must choose a line */
			int ligne;
			Scanner l = new Scanner(System.in);
			System.out.println("Choisir une ligne entre 1 et 5");
			ligne = Integer.parseInt(l.next());
			while (!players[i % players.length].getBoard().isPatternAddable(ligne - 1,
					players[i % players.length].handColor())
					|| players[i % players.length].getBoard().isWallColor(ligne - 1,
							players[i % players.length].handColor())) {
				System.out.println("Ligne invalide");
				System.out.println("Choisir une nouvelle ligne");
				ligne = Integer.parseInt(l.next());
			}
			players[i % players.length].getBoard().addPattern(players[i % players.length].emptyHand(), ligne - 1);
		}
		System.out.print("In your hand : [");
		players[i % players.length].showHand();
		System.out.println("]");
		players[i % players.length].getBoard().boardDisplay();
	}

	/* and this methods prints the state of the actual factories and center */

	public void textualDisplay() {
		int i = 0;
		for (Factory f : factories) {
			i++;
			System.out.print("(" + i + ") [");
			for (Tile t : f.getTile()) {
				System.out.print(t.getColor() + ",");
			}
			System.out.print("]  ");
		}
		System.out.println();
		for (Tile t : center) {
			System.out.print(t.getColor() + "-");
		}
		System.out.println();
	}

	/* Getters */

	/* Retturn the numbers of factories we should make */

	public int getFactoriesNbr() {
		if (players.length == 2)
			return 5;
		if (players.length == 3)
			return 7;
		if (players.length == 4)
			return 9;
		return -1;
	}

	/* return the factories */

	public Factory[] getFactories() {
		return factories;
	}

	/* Return bag's size */

	public int getBagSize() {
		return bag.getSize();
	}

	/* Return discard's size */

	public int getDiscardSize() {
		return discards.getSize();
	}

	/* return hand of the active player */

	public Tile[] getHand() {
		return players[activePlayer].getHand();
	}
}
