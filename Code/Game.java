public class Game{
  Player[] players;
  ArrayList<Tile> center;
  Factory[] factories;
  Bag bag;
  Discards discards;
  int round = 0;
  int first;

  public Game(int p){
    players = new Player[p];
    if(p==2) factories = new Factory[5];
    if(p==3) factories = new Factory[7];
    if(p==4) factories = new Factory[9];
    center = new ArrayList<Tile>();
  }

  public void round(){
    if(round==0) first = Math.random(0,3); ; //give random player 1st player tile;

    /* 1st phase */

    if(bag.isEmpty()){
      if(!discards.isEmpty()){
        bag.refile(discards.empty());
      }else{
        //stop the 1st phase
      }
    }

    for(Factory f : factories){
      int i = 0;
      while(i<4 && !bag.isEmpty()){
        f.add(bag.pick());
      }
    }

    /*
    * We now make every players play
    * starting from the first one
    * to the last one (the one before him in the array)
    * using %
    */

    for(int i = first; i < player.length+first; i++){

      /* Selecting if picking from Factories or Center */
      
    }
  }

  public boolean centerIsEmpty(){
    return center.isEmpty();
  }
}
