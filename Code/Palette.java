import java.awt.*;

public class Palette {

	public Palette() {
		new Vue();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new Palette();
		});
	}

}