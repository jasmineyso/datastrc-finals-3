public enum Status {
  CLAIMED("Claimed"),
  UNCLAIMED("Unclaimed");

  private final String label;

  Status(String label){
    this.label = label;
  }

  @Override 
  public String toString(){return label;}
  
}
