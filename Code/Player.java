import java.util.ArrayList;

public class Player {
	private Board board;
	private ArrayList<Tile> hand;

	public Player(Discards d) {
		board = new Board(d);
		hand = new ArrayList<Tile>();
	}

	public void pick(Tile[] ts) {
		for (Tile t : ts) {
			hand.add(t);
		}
	}

	public Tile[] emptyHand() {
		Tile[] treturn = new Tile[hand.size()];
	      int i = 0;
	      for(Tile t : hand) {
	    	  treturn[i] = t;
	    	  i++;
	      }
	      hand.clear();
	      return treturn;
	}

	public void fileLine(int index) {

	}

	public void addFloor(Tile[] t) {
		board.addFloor(t);
	}

	public Board getBoard() {
		return board;
	}

	public int handSize() {
		return hand.size();
	}

	public void showHand() {
		for (Tile t : hand) {
			System.out.print(t.getColor()+",");
		}
	}
	
	public char handColor() {
		return hand.get(0).getColor();
	}
}
