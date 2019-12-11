
public class Player{
  private Board board;
  private ArrayList<Tile> hand;

  public Player(){
    board = new Board();
    hand = new ArrayList<Tile>();
  }

  public void pick(Tile[] ts){
    for(Tile t : ts){
      hand.add(t);
    }
  }

  public Tile[] emptyHand(){
    Tile[] t = hand.toArray();
    hand.clear();
    return t;
  }

  public void fileLine(int index){

  }
}
