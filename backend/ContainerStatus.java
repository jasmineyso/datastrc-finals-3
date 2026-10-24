package backend;

public enum ContainerStatus{
  FULL("Full"),
  NEAR_FULL("Near Full"),
  AVAILABLE("Available");

  private final String label;

  ContainerStatus(String label){
    this.label = label;
  }


  @Override 
  public String toString(){return label;}
  
}
