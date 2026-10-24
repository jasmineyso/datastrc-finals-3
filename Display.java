import java.util.ArrayList;

public class Display {
  public static final int MENU_WIDTH = 121;

<<<<<<< HEAD
=======
  public static void containerSelection(ArrayList<Item> items) {
      headerTitle("Select Container");

      System.out.println("Available Storage Containers");
      System.out.println("-".repeat(MENU_WIDTH));

      for (int container = 1; container <= LostAndFound.CONTAINER_COUNT; container++) {
          int count = 0;

          for (Item item : items) {
              if (item.getContainer() == container
                      && item.getStatus().equalsIgnoreCase("Unclaimed")) {
                  count++;
              }
          }

          int remaining = LostAndFound.CONTAINER_CAPACITY - count;

          String status;

          if (remaining == 0) {
              status = "FULL";
          } else if (remaining <= 10) {
              status = "NEAR FULL";
          } else {
              status = "AVAILABLE";
          }

          System.out.printf(
              "        [%d] Container %d    Capacity: %d/%d    Status: %s%n",
              container,
              container,
              count,
              LostAndFound.CONTAINER_CAPACITY,
              status
          );
      }

      System.out.println("-".repeat(MENU_WIDTH));
      System.out.println("        [0] Return to Main Menu");
      System.out.println();

      System.out.println(
          "        Note: Full containers cannot be selected."
      );

      System.out.println();
  }


>>>>>>> origin/master
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