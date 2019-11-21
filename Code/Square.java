public class Square{
	
	private Tile t;

	public Square(){
		t = null;
	}

	public void add(Tile t){
		this.t = t;
	}

	public void remove(){
		t = null;
	}

	public boolean isEmpty(){
		return(t==null);
	}
}