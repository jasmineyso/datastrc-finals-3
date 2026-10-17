import java.util.ArrayList;

public class ItemIDGenerator {
  private static final String PREFIX = "IT";
  private static final String PATTERN = "^IT\\d{4, }$";

  public static String nextID(ArrayList<Item> items){
    int max = 0;
    for(Item item : items){
      max = Math.max(max, idNum(item.getItemID()));
    }
    return String.format("%s%04d", PREFIX, max+1);
  }

  //GET TRANSACTION NUMBER
  private static int idNum(String id){
    if(id != null && id.matches(PATTERN)){
      return Integer.parseInt(id.substring(PREFIX.length()));
    }
    return 0;
  }

}
