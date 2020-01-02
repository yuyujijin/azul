//We should had import to a xml file, for all package
import java.util.*;

public class Bag{

	protected ArrayList<Tile> pieces;

    /**
     * construcfor for the bag
     * we add 20 tiles for each color in it
    **/
    public Bag(){
    	ArrayList<Tile> p = new ArrayList<Tile>(100);
    	for(int i = 0 ; i < 100 ; i++){
    		if(i<20) p.add(new Tile('r')); //Red
    		if(i>=20 && i<40) p.add(new Tile('b')); //Blue
    		if(i>=40 && i<60) p.add(new Tile('y')); //Yellow
    		if(i>=60 && i<80) p.add(new Tile('w')); //White
    		if(i>=80) p.add(new Tile('n')); //Noir
	    }
	    pieces = p;
    }

	//Shuffle the bag
	public void shuffle(){
		Collections.shuffle(pieces);
	}

    //checks if the bag is empty
    public boolean isEmpty(){
    	return pieces.isEmpty();
    }

    //îck a tile from the bag
    public Tile pick(){
    	if(!pieces.isEmpty()){
    		shuffle();
    		return pieces.remove(0);
    	}
    	return null;
    }

    // refill the bag 
    public void refill(Tile[] ts){
      if(isEmpty()){
        for(Tile t : ts) pieces.add(t);
      }
    }
    
    // getter for the size
    public int getSize() {
    	return pieces.size();
    }
}
