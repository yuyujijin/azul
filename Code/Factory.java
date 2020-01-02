import java.util.ArrayList;

public class Factory{
  
  ArrayList<Tile> tiles;

  // constructor for a factory
  public Factory(){
    tiles = new ArrayList<Tile>();
  }

  // checks if a factory is empty
  public boolean isEmpty(){
    return !(tiles.size() > 0);
  }
  
  // getter for tiles
  public ArrayList<Tile> getTile(){
	  return tiles;
  }

  // add tiles in a factory
  public void add(Tile t){
    tiles.add(t);
  }

  // picks a tile from a factory is its not empty, else returns null
  public Tile[] pick(char c){
    if(!isEmpty()){
      ArrayList<Tile> subList = new ArrayList<Tile>();
      for(int i = 0 ; i < tiles.size() ; i++) {
    	  if(tiles.get(i).getColor() == c) {
    		  subList.add(tiles.get(i));
    		  tiles.remove(i);
    		  i--;
    	  }
      }
      Tile[] treturn = listToArray(subList);
      return treturn;
    }
    System.out.println("Error : factory is empty.");
    return null;
  }

  // checks is there are still remaining tiles in a factory
  public Tile[] rest(){
    if(!isEmpty()){
    	Tile[] treturn = listToArray(tiles);
        tiles.clear();
        return treturn;
    }
    System.out.println("No rest to get.");
    return null;
  }
  
  // transforms a list to an array
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
