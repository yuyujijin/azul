import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Vue extends JFrame {

	public Vue() {
		super("Azul");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1080, 720));

		setLocation(200, 200);
		pack();
		setVisible(true);
		this.setContentPane(new Panneau());
		this.setBackground(Color.LIGHT_GRAY);
		
	}
}
