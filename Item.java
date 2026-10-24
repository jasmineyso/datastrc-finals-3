public class Item {
  private String itemID;
  private String itemName;
  private String description;
  private String dateReported;
  private ItemStatus status;
  private ContainerStatus containerStatus;
  private int container;

  public Item(String itemID, String itemName, String description, String dateReported, int container){
    this.itemID = itemID;
    this.itemName = itemName;
    this.description = description;
    this.dateReported = dateReported;
    this.container = container;

    this.status = ItemStatus.UNCLAIMED;
    this.containerStatus = ContainerStatus.AVAILABLE;
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

    public int getContainer(){
      return container;
    }

    public String getContainerStatus(){
      return containerStatus.toString();
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status){
      if(status != null){
        this.status = ItemStatus.valueOf(status.toUpperCase());
      }
    }

    public void setContainer(int container){
      this.container = container;
    }

    public void setContainerStatus(int remaining){
      if(remaining == 0) {
        this.containerStatus = ContainerStatus.FULL;
      }else if(remaining <= 10) {
        this.containerStatus = ContainerStatus.NEAR_FULL;
      }else{
        this.containerStatus = ContainerStatus.AVAILABLE;
      }
    }
}
