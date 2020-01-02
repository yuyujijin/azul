import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Settings extends JPanel {

	private Info i = new Info();
	private boolean sendData;
	private JLabel playerLabel, tailleLabel, jokerLabel;
	private JComboBox player, taille;
	private JCheckBox joker;

	public Settings() {
		initComponent();
		JOptionPane.showMessageDialog(
	            null, this, "Azul",
	                JOptionPane.INFORMATION_MESSAGE);
		}
	

	public Info showInfo() {
		boolean b = joker.isSelected();
		return new Info((String) player.getSelectedItem(),(String) taille.getSelectedItem(),b);
	}

	private void initComponent() {

		JPanel panPlayer = new JPanel();
		panPlayer.setBackground(Color.white);
		panPlayer.setPreferredSize(new Dimension(220, 60));
		panPlayer.setBorder(BorderFactory.createTitledBorder("Nombre de joueurs"));
		player = new JComboBox();
		player.addItem("2");
		player.addItem("3");
		player.addItem("4");
		playerLabel = new JLabel("Players : ");
		panPlayer.add(playerLabel);
		panPlayer.add(player);

		JPanel panTaille = new JPanel();
		panTaille.setBackground(Color.white);
		panTaille.setBorder(BorderFactory.createTitledBorder("Taille :"));
		taille = new JComboBox();
		taille.addItem("1280x720");
		taille.addItem("1920x1080");
		taille.addItem("3840x2160");
		tailleLabel = new JLabel("Taille :");
		panTaille.add(tailleLabel);
		panTaille.add(taille);
		
		JPanel panJoker = new JPanel();
		panTaille.setBackground(Color.white);
		panTaille.setBorder(BorderFactory.createTitledBorder("Joker :"));
		joker = new JCheckBox();
		jokerLabel = new JLabel("Joker :");
		panJoker.add(jokerLabel);
		panJoker.add(joker);

		JPanel content = new JPanel();
		content.setBackground(Color.white);

		content.add(panTaille);
		content.add(panPlayer);
		content.add(panJoker);

		add(content, BorderLayout.CENTER);
	}
}