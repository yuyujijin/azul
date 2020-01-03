import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Settings extends JPanel {

	// declaring the differents variables that will be used in the file
	private Info i = new Info();
	private boolean sendData;
	private JLabel playerLabel, tailleLabel, jokerLabel;
	private JComboBox player, taille;
	private JCheckBox joker;

	// constructor for Settings, displaying a message
	public Settings() {
		initComponent();
		JOptionPane.showMessageDialog(
	            null, this, "Azul",
	                JOptionPane.INFORMATION_MESSAGE);
		}

	// get informations from the selected parameters (JComboBox)
	public Info showInfo() {
		boolean b = joker.isSelected();
		return new Info((String) player.getSelectedItem(),(String) taille.getSelectedItem(),b);
	}

	/** initialises the diffenrents components for the game parameters
	* such as the differents panels, the JCheckBox, JComboBox
	**/
	private void initComponent() {

		//player pannel
		JPanel panPlayer = new JPanel();
		panPlayer.setBackground(Color.white);
		panPlayer.setPreferredSize(new Dimension(220, 60));
		panPlayer.setBorder(BorderFactory.createTitledBorder("Nombre de joueurs"));
		// creating the scrolling menu to select the nb of players
		player = new JComboBox();
		player.addItem("2");
		player.addItem("3");
		player.addItem("4");
		playerLabel = new JLabel("Players : ");
		panPlayer.add(playerLabel);
		panPlayer.add(player);

		// size pannel
		JPanel panTaille = new JPanel();
		panTaille.setBackground(Color.white);
		panTaille.setBorder(BorderFactory.createTitledBorder("Taille :"));
		// creating the scrolling menu to select the wanted size of the game window
		taille = new JComboBox();
		taille.addItem("1920x1080");
		taille.addItem("3840x2160");
		tailleLabel = new JLabel("Taille :");
		panTaille.add(tailleLabel);
		panTaille.add(taille);

		// pannel for the joker option
		JPanel panJoker = new JPanel();
		panTaille.setBackground(Color.white);
		panTaille.setBorder(BorderFactory.createTitledBorder("Joker :"));
		// creating the joker checkbox
		joker = new JCheckBox();
		jokerLabel = new JLabel("Joker :");
		panJoker.add(jokerLabel);
		panJoker.add(joker);

		JPanel content = new JPanel();
		content.setBackground(Color.white);

		// adding it all to the JFrame
		content.add(panTaille);
		content.add(panPlayer);
		content.add(panJoker);

		add(content, BorderLayout.CENTER);
	}
}
