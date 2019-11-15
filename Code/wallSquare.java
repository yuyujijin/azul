public class wallSquare extends Square{
  private boolean activated;

  public wallSquare(Tile t){
    super;
    add(t);
    activated = false;
  }

  public void activate(){
    activated = true;
  }

  public boolean isActivated(){
    return activated;
  }

}
