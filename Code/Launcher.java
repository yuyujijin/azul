

import java.awt.EventQueue;

public class Launcher {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Settings s = new Settings();
			Info i = s.showInfo();
			System.out.println(i.getNbPlayer());
			Controleur c = new Controleur(i.getNbPlayer(),i.getLength(),i.getHeight(),i.getJoker());
			c.round();
			
		});
	}

}
