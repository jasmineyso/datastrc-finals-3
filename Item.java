public class Item {
  private String itemID;
  private String itemName;
  private String description;
  private String dateReported;
  private String status;
  private int location;

  public Item(String itemID, String itemName, String description, String dateReported, String status, int location){
    this.itemID = itemID;
    this.itemName = itemName;
    this.description = description;
    this.dateReported = dateReported;
    this.status = status;
    this.location = location;
  }

  private String[] itemStatus = {"Claimed", "Unclaimed"};
  public boolean validStatus(String status){
    for(String s : itemStatus){
      if(s.equalsIgnoreCase(status)){
        return true;
      }
    }
    return false;
  }


  public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getDateReported() {
        return dateReported;
    }

    public String getStatus() {
        return status;
    }

    public int location(){
      return location;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation(int location){
      this.location = location;
    }

}
