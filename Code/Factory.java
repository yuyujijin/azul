public class Factory{
  ArrayList<Tile> tiles;

  public Factory(){
    tiles = new ArrayList<Tile>();
  }

  public isEmpty(){
    return tiles.size() > 0;
  }


  public void add(Tile t){
    tiles.add(t);
  }

  public Tile[] pick(char c){
    if(!isEmpty()){
      ArrayList<Tile> subList = new ArrayList<Tile>();
      for(Tile t : tiles){
        if(t.getColor() == c) subList.add(tiles.remove(t));
      }
      return subList.toArray();
    }
    System.out.println("Error : factory is empty.");
    return null;
  }

  public Tile[] rest(){
    if(!isEmpty){
      return tiles;
    }
    System.out.println("No rest to get.");
    return null;
  }
}
