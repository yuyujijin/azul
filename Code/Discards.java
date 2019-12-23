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

  public Tile[] empty(){
    Tile[] ts = (Tile[]) pieces.toArray();
    pieces.clear();
    return ts;
  }

  public boolean isEmpty(){
    return pieces.isEmpty();
  }
}
