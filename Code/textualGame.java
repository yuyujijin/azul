import java.util.ArrayList;
import java.util.Scanner;

public class textualGame {
	Player[] players;
	ArrayList<Tile> center;
	Factory[] factories;
	Bag bag;
	Discards discards;
	int round = 0;
	int first;

	public textualGame(int p) {
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
	}

	public int getFactoriesNbr() {
		if (players.length == 2)
			return 5;
		if (players.length == 3)
			return 7;
		if (players.length == 4)
			return 9;
		return -1;
	}
	
	public Factory[] getFactories() {
		return factories;
	}
	
	public void phase1() {
		if (round == 0)
			first = (int) Math.random() * (players.length - 0);
		; // give random player 1st player tile;

		/* 1st phase */

		if (bag.isEmpty()) {
			if (!discards.isEmpty()) {
				bag.refile(discards.empty());
			} else {
				// stop the 1st phase
			}
		}

		for (Factory f : factories) {
			int i = 0;
			while (i < 4 && !bag.isEmpty()) {
				f.add(bag.pick());
				i++;
			}
		}

	}
	
	public void round() {
		phase1();
		
		/*
		 * We now make every players play starting from the first one to the last one
		 * (the one before him in the array) using %
		 */
		for (int i = first; i < players.length + first; i++) {

			/* Selecting if picking from Factories or Center */
			
			System.out.println("Tour du Joueur #" + (i % (players.length) + 1));
			textualDisplay();
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
			textualDisplay();
			System.out.print("In your hand : [");
			players[i % players.length].showHand();
			System.out.println("]");

			players[i % players.length].getBoard().boardDisplay();

			/* Now you got to deposite */

			/* Wall or floor */

			Scanner wf = new Scanner(System.in);
			System.out.println("Déposer dans la deco 'd' ou le sol 's'?");
			String ds = wf.next();
			while (!ds.equals("d") && !ds.equals("s")) {
				System.out.println("Réponse non valable;");
				System.out.println("Format : Deco 'd' ou sol 's'");
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
	}

	public boolean centerHasColor(char c) {
		for (Tile t : center) {
			if (t.getColor() == c)
				return true;
		}
		return false;
	}

	public boolean factoryHasColor(char c, int i) {
		ArrayList<Tile> factory = factories[i].getTile();
		for (Tile t : factory) {
			if (t.getColor() == c)
				return true;
		}
		return false;
	}

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

	public Tile[] pickFromCenter(char c) {
		ArrayList<Tile> ts = new ArrayList<Tile>();
		for (int i = 0; i < center.size(); i++) {
			if (center.get(i).getColor() == c) {
				ts.add(center.remove(i));
				i--;
			}
		}
		return ts.toArray(new Tile[ts.size()]);
	}

	public Tile[] pickFromFactory(char c, int i) {
		Tile[] t = factories[i].pick(c);
		Tile[] rest = factories[i].rest();
		addCenter(rest);
		return t;
	}

	public boolean centerIsEmpty() {
		return center.isEmpty();
	}

	public void addCenter(Tile[] t) {
		for (Tile ts : t) {
			center.add(ts);
		}
	}

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
}
