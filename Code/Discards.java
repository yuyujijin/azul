import java.util.*;

public class Discards extends Bag{
  
  public Discards(){
    pieces = new ArrayList<Tile>();
  }

  public void add(Tile[] ts){
    for(Tile t : ts){
      pieces.add(t);
    }
  }
  
  public void add(Tile t) {
	  pieces.add(t);
  }

  public Tile[] empty(){
    Tile[] ts = listToArray(pieces);
    pieces.clear();
    return ts;
  }

  public boolean isEmpty(){
    return pieces.isEmpty();
  }
  
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
