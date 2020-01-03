
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
	/* our controllerler */
	Controller controller;
	/* container for boards */
	JPanel board;
	viewBoard[] boards;
	/* container for factories */
	JPanel factory;
	JPanel[] factories;
	Center center;
	Hand hand;
	/* Color used */
	Color BACKGROUND_COLOR;
	Color TEXT_COLOR;
	Color BUTTON_COLOR;

	public View(Controller c, int length, int height) {
		super("Azul");
		BACKGROUND_COLOR = new Color(245,245,245);
		TEXT_COLOR = Color.BLACK;
		BUTTON_COLOR = new Color(210,220,230);
		controller = c;
		setSize(length, height);

		int playersNbr = controller.getPlayersNbr();
		int factoriesNbr = controller.getFactoriesNbr();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridBagLayout());

		/* Creating the constraints, in order to have a vertical display */

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

		/* putting the borders */

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

	/* opening a showmessagedialog to great the winner :) */

	public void won(int i,int s) {
		JOptionPane.showMessageDialog(this, "Player " + (i + 1) + " has won with "+s+" points!");
	}

	public void updateBorderActivePlayer(int i){
		for(int j = 0; j < boards.length;j++){
			if(i!=j) boards[j].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TEXT_COLOR),"Player " + (j + 1)));
		}
		boards[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255,130,146),3),"Player " + (i + 1)));
	}

	/* border method */

	public TitledBorder createBorder(String s,Color c) {
		TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TEXT_COLOR), s);
		tb.setTitleColor(c);
		return tb;
	}

	/* Setters */

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

	/* Disabler and Enabler */

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
		for (int i = 0; i < boards.length; i++) {
			boards[i].setEnabledModified(false);
		}
	}

	/* Updaters */

	public void update() {
		updateFactories();
		updateCenter();
	}

	public void updateBoard(int i, Board b) {
		updateFloor(i, b.getFloor());
		updatePattern(i, b.getPattern());
		updateWall(i, b.getWall());
	}

	public void updateFloor(int p, Square[] t) {
		int i = 0;
		Component[] components = boards[p].getFloor().getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				if (!t[i].isEmpty()){
					((JButton) component).setIcon(t[i].getTile().getImage());
					((JButton) component).setDisabledIcon(t[i].getTile().getImage());
				}
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

	public void updatePattern(int p, Square[][] t) {
		JButton[][] jb = boards[p].getPatternArray();
		for (int i = 0; i < jb.length; i++) {
			for (int j = 0; j < jb[i].length; j++) {
				if (!t[i][j].isEmpty()) {
					jb[i][j].setIcon(t[i][j].getTile().getImage());
					jb[i][j].setDisabledIcon(t[i][j].getTile().getImage());
					jb[i][j].setEnabled(false);
				}
			}
		}
		boards[p].revalidate();
		boards[p].repaint();
	}

	public void updateWall(int p, Square[][] t) {
		JButton[][] jb = boards[p].getWallArray();
		for (int i = 0; i < jb.length; i++) {
			for (int j = 0; j < jb[i].length; j++) {
				if (!t[i][j].isEmpty()) {
					jb[i][j].setIcon(t[i][j].getTile().getImage());
					jb[i][j].setDisabledIcon(t[i][j].getTile().getImage());
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
		fillFactories(controller.getFactories());
	}

	public void updateCenter() {
		center.removeAll();
		center.revalidate();
		center.repaint();
		ArrayList<Tile> t = controller.getCenter();
		for (Tile ts : t) {
			center.add(new viewTile(ts, -1));
			center.revalidate();
			center.repaint();
		}
	}

	public void updateBoardScore(int b, int s) {
		boards[b].updateScore(s);
	}

	/* Resetters */

	public void resetPattern(int i) {
		boards[i].resetPattern();
		boards[i].revalidate();
		boards[i].repaint();
	}

	public void resetFloor(int i) {
		boards[i].resetFloor();
		boards[i].revalidate();
		boards[i].repaint();
	}

	/* Our viewFactory */

	private class viewFactory extends JPanel {
		private Factory f;
	}

	/* our viewBoard */

	private class viewBoard extends JPanel {
		/* Container of JButtons for the pattern */
		private JPanel pattern;
		private JButton[][] patterns;
		/* Container of JButtons for the wall */
		private JPanel wall;
		private JButton[][] walls;
		/* Container of JButtons for the floor */
		private JPanel floor;
		private JLabel score;

		/* JButtons are in fact viewTile */

		public viewBoard() {
			setBackground(BACKGROUND_COLOR);
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			pattern = new JPanel(new GridBagLayout());
			pattern.setBackground(BACKGROUND_COLOR);
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_END;
			patterns = new JButton[5][];

			/* Creating a "stair" likes grid, to mimike the patterns
			* and adding everything into the JPannel and the array
			*/

			for (int i = 0; i < 5; i++) {
				c.gridy = i;
				patterns[i] = new JButton[i + 1];
				for (int j = 0; j < i + 1; j++) {

					// mergin

					final int z = i;
					c.insets = new Insets(2, 2, 2, 2);
					c.gridwidth = 1;
					c.gridheight = 1;
					c.gridx = 5 - j;
					JButton x = new JButton(getEmpty());
					x.addActionListener(MouseEvent -> {
						controller.depositPattern(z);
					});
					x.setPreferredSize(new Dimension(32,32));
					patterns[i][j] = x;
					pattern.add(x, c);
				}
			}
			add(pattern, gbc);
			gbc.gridx = 1;
			walls = new JButton[5][5];
			wall = new JPanel(new GridLayout(5, 5, 4, 4));
			wall.setBackground(BACKGROUND_COLOR);

			/* same with the wall */

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					JButton x = new JButton(getEmpty());
					x.setPreferredSize(new Dimension(32,32));
					x.setEnabled(false);
					x.setIcon(getWallImage(i,j,"mur"));
					x.setDisabledIcon(getWallImage(i,j,"mur"));
					walls[i][j] = x;
					wall.add(x);
				}
			}
			add(wall, gbc);
			gbc.gridy = 1;
			gbc.gridx = 0;
			floor = new JPanel(new GridLayout(1, 7, 4, 4));
			floor.setBackground(BACKGROUND_COLOR);

			/* Labelling the point losed, always prevent the customer :) */

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
				JButton x = new JButton(getEmpty());
				x.setBorder(createBorder(s,TEXT_COLOR));
				x.addActionListener(MouseEvent -> {
					controller.depositFloor();
				});
				x.setPreferredSize(new Dimension(32,32));
				floor.add(x);
			}
			add(floor, gbc);
			score = new JLabel("Score : 0");
			score.setForeground(TEXT_COLOR);
			add(score);
		}

		/* in order to update the score field */

		public void updateScore(int s) {
			score.setText("Score : " + s);
		}

		/* to reset the pattern line */

		public void resetPattern() {
			pattern.removeAll();
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_END;
			patterns = new JButton[5][];
			for (int i = 0; i < 5; i++) {
				c.gridy = i;
				patterns[i] = new JButton[i + 1];
				for (int j = 0; j < i + 1; j++) {
					// mergin
					final int z = i;
					c.insets = new Insets(2, 2, 2, 2);
					c.gridwidth = 1;
					c.gridheight = 1;
					c.gridx = 5 - j;
					JButton x = new JButton(getEmpty());
					x.addActionListener(MouseEvent -> {
						controller.depositPattern(z);
					});
					x.setPreferredSize(new Dimension(32,32));
					patterns[i][j] = x;
					pattern.add(x, c);
				}
			}
		}

		/* resetting and relabeling everything */

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
				JButton x = new JButton(getEmpty());
				x.setBorder(createBorder(s,TEXT_COLOR));
				x.addActionListener(MouseEvent -> {
					controller.depositFloor();
				});
				x.setPreferredSize(new Dimension(32,32));
				floor.add(x);
			}
		}

		/* setEnabled(boolean b), but for Floor and Pattern */

		public void setEnabledPattern(boolean b) {
			Component[] comp = pattern.getComponents();
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

		/* Both at the same time ! */

		public void setEnabledModified(boolean b) {
			setEnabledPattern(b);
			setEnabledFloor(b);
		}

		/* Getters */

		public JPanel getFloor() {
			return floor;
		}

		public JPanel getPattern() {
			return pattern;
		}

		public JButton[][] getPatternArray() {
			return patterns;
		}

		public JButton[][] getWallArray() {
			return walls;
		}

		public JPanel getWall() {
			return wall;
		}
	}

	/* our center class */

	private class Center extends JPanel {
		ArrayList<Tile> center;

		public Center() {
			center = new ArrayList<Tile>();
			setLayout(new FlowLayout());
			setBackground(BACKGROUND_COLOR);
		}
	}

	/* viewTile are extending JButton
	* their background color are inherited from the tile
	* they make reference to
	*/

	private class viewTile extends JButton implements MouseListener {
		Tile t;
		int i;
		ImageIcon basicColor;
		ImageIcon hoveredColor;

		/*  the integer i makes a reference to the factory containing them, -1 for center */

		public viewTile(Tile t, int i) {
			super(t.getImage());
			setDisabledIcon(t.getImage());
			basicColor = t.getImage();
			if(t.getColor() == 'f'){
				hoveredColor = basicColor;
			}else{
				hoveredColor = t.getHoveredImage();
			}
			this.i = i;
			this.t = t;
			setEnabled(false);
			setPreferredSize(new Dimension(32,32));
			if(t.getColor() == 'f') add(new JLabel("1st"));
			addMouseListener(this);
		}

		public void getColor() {
			System.out.println(t.getColor());
		}

		/* When clicking on a tile, calls the function corresponding if enabled */

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (isEnabled()) {
				if (i != -1) {
					controller.pickTileFactory(t.getColor(), i);
				} else {
					controller.pickTileCenter(t.getColor());
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Component[] comps;
			if(i!=-1){
				comps = factories[i].getComponents();
			}else{
				comps = center.getComponents();
			}
			for(Component c : comps){
				if((((viewTile) c)).t.getColor() == t.getColor())
					((viewTile) c).setIcon(((viewTile) c).hoveredColor);
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			Component[] comps;
			if(i!=-1){
				comps = factories[i].getComponents();
			}else{
				comps = center.getComponents();
			}
			for(Component c : comps){
				if((((viewTile) c)).t.getColor() == t.getColor())
					((viewTile) c).setIcon(((viewTile) c).basicColor);
			}
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

	/* Our hand, which contains an ArrayList of viewTile */

	private class Hand extends JPanel{
		public ArrayList<viewTile> tiles;

		public Hand() {
			tiles = new ArrayList<viewTile>();
			setLayout( new FlowLayout(FlowLayout.LEFT));
		}
	}


		public ImageIcon getWallImage(int line, int col,String s){
			if(line==col)
				return new ImageIcon("ressources/img/"+s+"blue.png");
			if(line==(col+1)%5)
				return new ImageIcon("ressources/img/"+s+"white.png");
			if(line==(col+2)%5)
				return new ImageIcon("ressources/img/"+s+"black.png");
			if(line==(col+3)%5)
				return new ImageIcon("ressources/img/"+s+"red.png");
			return new ImageIcon("ressources/img/"+s+"yellow.png");
		}

	public ImageIcon getEmpty(){
		return new ImageIcon("ressource/img/empty.png");
	}
}
