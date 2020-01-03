import java.util.*;

public class Discards extends Bag{
  
  // constructor for the Discard
  public Discards(){
    pieces = new ArrayList<Tile>();
  }

  // add a list tile to the discard
  public void add(Tile[] ts){
    for(Tile t : ts){
      pieces.add(t);
    }
  }
  
  // add a tile to the discard
  public void add(Tile t) {
	  pieces.add(t);
  }

  // empty
  public Tile[] empty(){
    Tile[] ts = listToArray(pieces);
    pieces.clear();
    return ts;
  }

  // checks if the list is empty
  public boolean isEmpty(){
    return pieces.isEmpty();
  }
  
  // changed the list to an array
  public Tile[] listToArray(ArrayList<Tile> al) {
	  Tile[] treturn = new Tile[al.size()];
      int i = 0;
      for(Tile t : al) {
    	  treturn[i] = t;
    	  i++;
      }
      return treturn;
  }
}
