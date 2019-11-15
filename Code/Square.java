public class Square{
  private Tile t;

  public Square(){
    t = null;
  }

  public add(Tile t){
    this.t = t;
  }

  public remove(){
    t = null;
  }

  public boolean isEmpty(){
    return(t==null);
  }
}
