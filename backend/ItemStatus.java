<<<<<<<< HEAD:backend/ItemStatus.java
package backend;

========
>>>>>>>> 5de78ef63f7e8bfa594f2f713961c6c0b5731936:ItemStatus.java
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
