
public class Info {

	private String nbPlayers, taille;
	public static boolean joker;
	public static int height;
	public static int length;
	public static int nbGamePlayer;
	public static String[] sizes = { "1280x720", "1920x1080", "3840x2160" };

	public Info() {
	}

	public Info(String nbPlayers, String taille, boolean b) {
		this.nbPlayers = nbPlayers;
		this.taille = taille;
		this.joker = b;
		updateSize();
	}

	public String toString() {
		String str;
		if (this.nbPlayers != null && this.taille != null) {
			str = "Paramètres choisis : " + "\n";
			str += "Nombres de joueurs : " + this.nbPlayers + "\n";
			str += "Hauteur de la fenêtre : " + this.taille + "\n";
		} else {
			str = "Aucune information rentrée !";
		}
		return str;
	}

	public void updateSize() {
		if (this.taille.equals(sizes[0])) {
			height = 720;
			length = 1280;
		}
		if (this.taille.equals(sizes[1])) {
			height = 1080;
			length = 1920;
		}
		if (this.taille.equals(sizes[2])) {
			height = 2160;
			length = 3840;
		}
	}

	public void updateNbPlayers() {
		nbGamePlayer = Integer.parseInt(this.nbPlayers);
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}

	public int getNbPlayer() {
		return Integer.parseInt(nbPlayers);
	}
	
	public boolean getJoker() {
		return joker;
	}

}
