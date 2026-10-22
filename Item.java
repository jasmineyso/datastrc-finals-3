public class Item {
  private String itemID;
  private String itemName;
  private String description;
  private String dateReported;
  private Status status;
  private int location;

  public Item(String itemID, String itemName, String description, String dateReported, int location){
    this.itemID = itemID;
    this.itemName = itemName;
    this.description = description;
    this.dateReported = dateReported;
    this.status = Status.UNCLAIMED;
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
        return status.toString();
    }

    public int getLocation(){
      return location;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status){
      if(status != null){
        this.status = Status.valueOf(status.toUpperCase());
      }
    }

    public void setLocation(int location){
      this.location = location;
    }

}
