
public class Info {

	// initiating the different variables that will be used in the file

	private String nbPlayers, taille;
	public static boolean joker;
	public static int height;
	public static int length;
	public static int nbGamePlayer;
	public static String[] sizes = { "1920x1080", "3840x2160" };

	// empty constructor to display informations regarding the game (nb player, size)
	public Info() {
	}

	// second constructor for info
	public Info(String nbPlayers, String taille, boolean b) {
		this.nbPlayers = nbPlayers;
		this.taille = taille;
		this.joker = b;
		updateSize();
	}

	/** updates the global variable height and length according to the selected game size parameters
	**/
	public void updateSize() {
		if (this.taille.equals(sizes[0])) {
			height = 1080;
			length = 1920;
		}
		if (this.taille.equals(sizes[1])) {
			height = 2160;
			length = 3840;
		}
	}

	/** updates the global variable nbPlayers according to the selected nbPlayers in the game parameters
	**/
	public void updateNbPlayers() {
		nbGamePlayer = Integer.parseInt(this.nbPlayers);
	}

	// getter for height
	public int getHeight() {
		return height;
	}

	// getter for length
	public int getLength() {
		return length;
	}

	// getter for the nb of players
	public int getNbPlayer() {
		return Integer.parseInt(nbPlayers);
	}

	// getter for the joker
	public boolean getJoker() {
		return joker;
	}

}
