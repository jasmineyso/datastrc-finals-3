package backend;

public enum ItemStatus {
  CLAIMED("Claimed"),
  UNCLAIMED("Unclaimed");

  private final String label;

  ItemStatus(String label){
    this.label = label;
  }

  @Override 
  public String toString(){return label;}
  
}
