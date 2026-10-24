package guide;

import java.util.ArrayList;

import backend.Item;
import backend.LostAndFound;

public class Display {
  public static final int MENU_WIDTH = 121;

  public static void containerSelection(ArrayList<Item> items) {
    LostAndFound lostAndFound = new LostAndFound();
    subHeaderTitle("Select Container");
    String containerStatus = "";

    for(int container = 1; container <= LostAndFound.CONTAINER_COUNT; container++){
      int ctr = lostAndFound.getContainerItemCount(container);
      int remaining = LostAndFound.CONTAINER_CAPACITY - ctr;

      for(Item item : items){
        if(item.getContainer() == container){
          item.setContainerStatus(remaining);
          containerStatus = item.getContainerStatus();
        }
      }
      System.out.printf("        [%d] Container %d    Capacity: %d/%d    Status: %s%n", container, container, ctr, LostAndFound.CONTAINER_CAPACITY, containerStatus);
    }
    System.out.println("-".repeat(MENU_WIDTH));
    System.out.println("        Note: Full containers cannot be selected.");
    System.out.println();
  }


  public static void displayMenu(String ... choices){
    String lightLine = "-".repeat(MENU_WIDTH);
    for (int i = 0; i < choices.length; i++) {
        System.out.printf("\t[%d] %s%n", i + 1, choices[i]);
    }

    System.out.println(lightLine);
  }

  public static void centerText(String text){
    int center = Math.max(0, (MENU_WIDTH - text.length()) / 2);

    System.out.println(" ".repeat(center) + text.toUpperCase());
  }

  public static void headerTitle(String title){
    String heavyLine = "=".repeat(MENU_WIDTH); 

    System.out.println();
    System.out.println(heavyLine);
    centerText(title);
    System.out.println(heavyLine);

    if(!title.equalsIgnoreCase("Main menu")){
      String note = "Note: Press 0 to go back to Main Menu anytime";
      centerText(note);
      if (!title.equalsIgnoreCase("Report An Item")) {
        System.out.println("\n"); 
      }
    }
  }

  public static void subHeaderTitle(String title){
    System.out.println("\n");
    String subHeader = " " + title.toUpperCase() + " ";
        int dashCount = (MENU_WIDTH - subHeader.length()) / 2;

        System.out.println(
                "-".repeat(Math.max(0, dashCount))
                        + subHeader
                        + "-".repeat(Math.max(0,
                                MENU_WIDTH - dashCount - subHeader.length())));
        System.out.println();
  }


  public static void itemsTable(ArrayList<Item> items, String totalLabel, boolean withStatus){
    String format = withStatus ? " %-10s | %-22s | %-40s | %-13s | %-10s | %-12s%n" : " %-10s | %-22s | %-40s | %-13s | %-10s%n";
    String[] headers = withStatus
    ? new String[] {"ITEM ID", "ITEM NAME", "DESCRIPTION", "DATE", "CONTAINER", "STATUS"}
    : new String[] {"ITEM ID", "ITEM NAME", "DESCRIPTION", "DATE", "CONTAINER"};
    String lightLine = "-".repeat(MENU_WIDTH);

    System.out.printf(format, (Object[]) headers);
    System.out.println(lightLine);

    for(Item item : items){
      System.out.printf(format,
          item.getItemID(),
          fit(item.getItemName(), 22),
          fit(item.getDescription(), 40),
          item.getDateReported(),
          item.getContainer(),
          item.getStatus());
    }

    System.out.println(lightLine);
    System.out.println(" " + totalLabel + ": " + items.size());
  }

  public static void itemsContainer(ArrayList<Item> items, String totalLabel){
    String lightLine = "-".repeat(MENU_WIDTH);

    System.out.printf("%-20s | %-25s | %-40s | %-13s%n", "ITEM ID", "ITEM NAME", "DESCRIPTION", "DATE");
    System.out.println(lightLine);

    for(Item item : items){
      System.out.printf("%-20s | %-25s | %-40s | %-13s%n",
        item.getItemID(),
        fit(item.getItemName(), 22),
        fit(item.getDescription(), 40),
        item.getDateReported()
      );
    }

    System.out.println(lightLine);
    System.out.println(" " + totalLabel + ": " + items.size());
  }

  public static void displayAnItem(String action, String[][] itemDetails){
    int labelWidth = 0;
    for (String[] row : itemDetails) {
      labelWidth = Math.max(labelWidth, row[0].length());
    }

    for (String[] row : itemDetails) {
      System.out.printf("\t%-" + labelWidth + "s : %s%n", row[0], row[1]);
    }

    System.out.println("-".repeat(MENU_WIDTH));
    
    if(action.equalsIgnoreCase("report")){
      System.out.println("Item reported successfully.");
    }else if(action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("mark")){
      System.out.println("Item found.");
    }
  }

  private static String fit(String text, int width){
    if(text.length() <= width){
      return text;
    }
    return text.substring(0, width - 3) + "...";
  }
}