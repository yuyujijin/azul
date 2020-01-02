
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class View extends JFrame {
	Controleur control;
	JPanel board;
	viewBoard[] boards;
	JPanel factory;
	JPanel[] factories;
	Center center;
	Hand hand;
	Color BACKGROUND_COLOR;
	Color TEXT_COLOR;
	Color BUTTON_COLOR;

	public View(Controleur c, int length, int height) {
		super("Azul");
		BACKGROUND_COLOR = new Color(245,245,245);
		TEXT_COLOR = Color.BLACK;
		BUTTON_COLOR = new Color(210,220,230);
		control = c;
		setSize(length, height);
		/* temp */
		int playersNbr = control.getPlayersNbr();
		int factoriesNbr = control.getFactoriesNbr();
		/* */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridBagLayout());

		hand = new Hand();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JPanel[] factories;

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		/* creating the different parts */
		factory = new JPanel();
		factory.setBackground(BACKGROUND_COLOR);
		center = new Center();
		center.setBackground(BACKGROUND_COLOR);
		board = new JPanel();
		board.setBackground(BACKGROUND_COLOR);
		hand = new Hand();
		hand.setBackground(BACKGROUND_COLOR);

		factory.setLayout(new FlowLayout());
		factory.setBorder(createBorder("Factories",TEXT_COLOR));
		center.setBorder(createBorder("Center",TEXT_COLOR));
		board.setBorder(createBorder("Board",TEXT_COLOR));
		hand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR),"Current Hand :"));

		/* creating the factories */

		int lines = 1;
		if (playersNbr > 2)
			lines = 2;
		board.setLayout(new GridLayout(2, lines)); // the grid will be the size of the players
		boards = new viewBoard[playersNbr];
		for (int i = 0; i < playersNbr; i++) {
			boards[i] = new viewBoard();
			boards[i].setBorder(createBorder("Player " + (i + 1),TEXT_COLOR));
			board.add(boards[i]);
		}
		/* adding everyhting */
		
		add(factory, gbc);
		add(center, gbc);
		add(board, gbc);
		add(hand,gbc);
		getContentPane().setBackground(BACKGROUND_COLOR);
	}

	public void won(int i,int s) {
		JOptionPane.showMessageDialog(this, "Player " + (i + 1) + " has won with "+s+" points!");
	}

	public void setFactories(int factoriesNbr) {
		this.factories = new JPanel[factoriesNbr];
		for (int i = 0; i < factoriesNbr; i++) {
			viewFactory factory = new viewFactory();
			factory.setBackground(BACKGROUND_COLOR);
			factory.setBorder(createBorder(
					"Factory " + (i + 1),TEXT_COLOR));
			factory.setLayout(new GridLayout(2, 2, 5, 5));
			this.factories[i] = factory;
			this.factory.add(factory);
		}
	}
	
	public TitledBorder createBorder(String s,Color c) {
		TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TEXT_COLOR), s);
		tb.setTitleColor(c);
		return tb;
	}

	public void fillFactories(Factory[] f) {
		int i = 0;
		for (Factory fac : f) {
			if (!fac.isEmpty())
				for (Tile t : fac.getTile()) {
					factories[i].add(new viewTile(t, i));
				}
			i++;
		}
	}

	public void disableTiles() {
		for (JPanel j : factories) {
			Component[] jp = j.getComponents();
			for (Component component : jp) {
				if (component instanceof viewTile) {
					((viewTile) component).setEnabled(false);
				}
			}
		}
	}

	public void enableTiles() {
		for (JPanel j : factories) {
			Component[] jp = j.getComponents();
			for (Component component : jp) {
				if (component instanceof viewTile) {
					((viewTile) component).setEnabled(true);
				}
			}
		}
	}

	public void enableCenter() {
		Component[] jp = center.getComponents();
		for (Component component : jp) {
			if (component instanceof viewTile) {
				((viewTile) component).setEnabled(true);
			}
		}
	}

	public void disableCenter() {
		Component[] jp = center.getComponents();
		for (Component component : jp) {
			if (component instanceof viewTile) {
				((viewTile) component).setEnabled(false);
			}
		}
	}

	public void enableBoard(int i) {
		boards[i].setEnabledModified(true);
	}

	public void disableBoards() {
		// must disable every boards
		for (int i = 0; i < boards.length; i++) {
			boards[i].setEnabledModified(false);
		}
	}

	public void update() {
		updateFactories();
		updateCenter();
	}

	public void updateBoard(int i, Board b) {
		updateFloor(i, b.getFloor());
		updateDeco(i, b.getDeco());
		updateWall(i, b.getWall());
	}

	public void updateFloor(int p, Square[] t) {
		int i = 0;
		Component[] components = boards[p].getFloor().getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				if (!t[i].isEmpty())
					((JButton) component).setBackground(t[i].getRGBColor());
			}
			i++;
		}
		boards[p].revalidate();
		boards[p].repaint();
	}
	
	public void updateHand(Tile[] t) {
		hand.removeAll();
		for(Tile ts : t) {
			hand.add(new viewTile(ts,-1));
		}
	}

	public void updateDeco(int p, Square[][] t) {
		JButton[][] jb = boards[p].getDecoArray();
		for (int i = 0; i < jb.length; i++) {
			for (int j = 0; j < jb[i].length; j++) {
				if (!t[i][j].isEmpty()) {
					jb[i][j].setBackground(t[i][j].getRGBColor());
					jb[i][j].setEnabled(false);
				}
			}
		}
		boards[p].revalidate();
		boards[p].repaint();
	}

	public void resetDeco(int i) {
		boards[i].resetDeco();
		boards[i].revalidate();
		boards[i].repaint();
	}

	public void resetFloor(int i) {
		boards[i].resetFloor();
		boards[i].revalidate();
		boards[i].repaint();
	}

	public void updateWall(int p, Square[][] t) {
		JButton[][] jb = boards[p].getWallArray();
		for (int i = 0; i < jb.length; i++) {
			for (int j = 0; j < jb[i].length; j++) {
				if (!t[i][j].isEmpty()) {
					jb[i][j].setBackground(t[i][j].getRGBColor());
					jb[i][j].setEnabled(false);
				}
			}
		}
		boards[p].revalidate();
		boards[p].repaint();
	}

	public void updateFactories() {
		for (int i = 0; i < factories.length; i++) {
			factories[i].removeAll();
			factories[i].revalidate();
			factories[i].repaint();
		}
		fillFactories(control.getFactories());
	}

	public void updateCenter() {
		center.removeAll();
		center.revalidate();
		center.repaint();
		ArrayList<Tile> t = control.getCenter();
		for (Tile ts : t) {
			center.add(new viewTile(ts, -1));
			center.revalidate();
			center.repaint();
		}
	}

	public void updateBoardScore(int b, int s) {
		boards[b].updateScore(s);
	}

	private class Center extends JPanel {
		ArrayList<Tile> center;

		public Center() {
			center = new ArrayList<Tile>();
			setLayout(new FlowLayout());
			setBackground(BACKGROUND_COLOR);
		}
	}

	private class viewFactory extends JPanel {
		private Factory f;
	}

	private class viewBoard extends JPanel {
		private JPanel deco;
		private JButton[][] decos;
		private JPanel wall;
		private JButton[][] walls;
		private JPanel floor;
		private JLabel score;

		public viewBoard() {
			setBackground(BACKGROUND_COLOR);
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			deco = new JPanel(new GridBagLayout());
			deco.setBackground(BACKGROUND_COLOR);
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_END;
			decos = new JButton[5][];
			for (int i = 0; i < 5; i++) {
				c.gridy = i;
				decos[i] = new JButton[i + 1];
				for (int j = 0; j < i + 1; j++) {
					// mergin
					final int z = i;
					c.insets = new Insets(2, 2, 2, 2);
					c.gridwidth = 1;
					c.gridheight = 1;
					c.gridx = 5 - j;
					JButton x = new JButton(" ");
					x.addActionListener(MouseEvent -> {
						control.depositeDeco(z);
					});
					decos[i][j] = x;
					deco.add(x, c);
				}
			}
			add(deco, gbc);
			gbc.gridx = 1;
			walls = new JButton[5][5];
			wall = new JPanel(new GridLayout(5, 5, 4, 4));
			wall.setBackground(BACKGROUND_COLOR);
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					JButton x = new JButton();
					x.add(new JLabel(" "));
					x.setEnabled(false);
					walls[i][j] = x;
					wall.add(x);
				}
			}
			add(wall, gbc);
			gbc.gridy = 1;
			gbc.gridx = 0;
			floor = new JPanel(new GridLayout(1, 7, 4, 4));
			floor.setBackground(BACKGROUND_COLOR);
			for (int i = 0; i < 7; i++) {
				String s = "";
				switch (i) {
				case 0:
					s = "-1";
					break;
				case 1:
					s = "-1";
					break;
				case 2:
					s = "-2";
					break;
				case 3:
					s = "-2";
					break;
				case 4:
					s = "-2";
					break;
				case 5:
					s = "-3";
					break;
				case 6:
					s = "-3";
					break;
				}
				JButton x = new JButton(s);
				x.addActionListener(MouseEvent -> {
					control.depositeFloor();
				});
				floor.add(x);
			}
			add(floor, gbc);
			score = new JLabel("Score : 0");
			score.setForeground(TEXT_COLOR);
			add(score);
		}

		public void updateScore(int s) {
			score.setText("Score : " + s);
		}

		public void resetDeco() {
			deco.removeAll();
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_END;
			decos = new JButton[5][];
			for (int i = 0; i < 5; i++) {
				c.gridy = i;
				decos[i] = new JButton[i + 1];
				for (int j = 0; j < i + 1; j++) {
					// mergin
					final int z = i;
					c.insets = new Insets(2, 2, 2, 2);
					c.gridwidth = 1;
					c.gridheight = 1;
					c.gridx = 5 - j;
					JButton x = new JButton(" ");
					x.addActionListener(MouseEvent -> {
						control.depositeDeco(z);
					});
					decos[i][j] = x;
					deco.add(x, c);
				}
			}
		}

		public void resetFloor() {
			floor.removeAll();
			floor.setLayout(new GridLayout(1, 7, 4, 4));
			for (int i = 0; i < 7; i++) {
				String s = "";
				switch (i) {
				case 0:
					s = "-1";
					break;
				case 1:
					s = "-1";
					break;
				case 2:
					s = "-2";
					break;
				case 3:
					s = "-2";
					break;
				case 4:
					s = "-2";
					break;
				case 5:
					s = "-3";
					break;
				case 6:
					s = "-3";
					break;
				}
				JButton x = new JButton(s);
				x.addActionListener(MouseEvent -> {
					control.depositeFloor();
				});
				floor.add(x);
			}
		}

		public void setEnabledDeco(boolean b) {
			Component[] comp = deco.getComponents();
			for (Component component : comp) {
				if (component instanceof JButton) {
					if (((JButton) component).getBackground() == new JButton().getBackground()) {
						((JButton) component).setEnabled(b);
					}
				}
			}
		}

		public void setEnabledFloor(boolean b) {
			Component[] comp = floor.getComponents();
			for (Component component : comp) {
				if (component instanceof JButton) {
					((JButton) component).setEnabled(b);
				}
			}
		}

		public void setEnabledModified(boolean b) {
			setEnabledDeco(b);
			setEnabledFloor(b);
		}

		public JPanel getFloor() {
			return floor;
		}

		public JPanel getDeco() {
			return deco;
		}

		public JButton[][] getDecoArray() {
			return decos;
		}

		public JButton[][] getWallArray() {
			return walls;
		}

		public JPanel getWall() {
			return wall;
		}
	}

	public void addHand(ArrayList<Tile> t) {
		for (Tile ts : t) {
			hand.add(new viewTile(ts, 0));
		}
	}

	private class viewTile extends JButton implements MouseListener {
		Tile t;
		int i;

		public viewTile(Tile t, int i) {
			this.i = i;
			this.t = t;
			setEnabled(false);
			setBackground(t.getRGBColor());
			if(t.getColor() == 'f') add(new JLabel("1st"));
			addMouseListener(this);
		}

		public void getColor() {
			System.out.println(t.getColor());
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (isEnabled()) {
				if (i != -1) {
					control.pickTile(t.getColor(), i);
				} else {
					control.pickTileCenter(t.getColor());
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	private class Hand extends JPanel{
		public ArrayList<viewTile> tiles;

		public Hand() {
			tiles = new ArrayList<viewTile>();
			setLayout( new FlowLayout(FlowLayout.LEFT));
		}
	}
}
