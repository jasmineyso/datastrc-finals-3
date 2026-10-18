import java.util.ArrayList;

public class Display {
  public static final int MENU_WIDTH = 121;
  public static void displayMenu(String ... choices){
    String lightLine = "-".repeat(MENU_WIDTH);
    for (int i = 0; i < choices.length; i++) {
        System.out.printf("  [%d] %s%n", i + 1, choices[i]);
    }

    System.out.println(lightLine);
  }

  public static void headerTitle(String title){
    String heavyLine = "=".repeat(MENU_WIDTH); 
    int center = Math.max(0, (MENU_WIDTH - title.length()) / 2);

    System.out.println();
    System.out.println(heavyLine);
    System.out.println(" ".repeat(center) + title.toUpperCase());
    System.out.println(heavyLine);
  }

  public static void subHeaderTitle(String title){
    String subHeader = " " + title.toUpperCase() + " ";
        int dashCount = (MENU_WIDTH - subHeader.length()) / 2;

        System.out.println(
                "-".repeat(Math.max(0, dashCount))
                        + subHeader
                        + "-".repeat(Math.max(0,
                                MENU_WIDTH - dashCount - subHeader.length())));
        System.out.println();
  }


  public static void itemsTable(ArrayList<Item> items){
    String format = " %-10s | %-22s | %-40s | %-13s | %-8s | %-12s%n";
    String lightLine = "-".repeat(MENU_WIDTH);

    System.out.printf(format, "ITEM ID", "ITEM NAME", "DESCRIPTION", "DATE", "LOCATION", "STATUS");
    System.out.println(lightLine);

    for(Item item : items){
      System.out.printf(format,
          item.getItemID(),
          fit(item.getItemName(), 22),
          fit(item.getDescription(), 40),
          item.getDateReported(),
          item.getLocation(),
          item.getStatus());
    }

    System.out.println(lightLine);
    System.out.println(" Total items: " + items.size());
  }

  public static void displayItem(Item item){
    String format = " %-10s | %-22s | %-40s | %-13s | %-8s | %-12s%n";
    String lightLine = "-".repeat(MENU_WIDTH);

    System.out.printf(format, "ITEM ID", "ITEM NAME", "DESCRIPTION", "DATE", "LOCATION", "STATUS");
    System.out.println(lightLine);
    System.out.printf(format,
          item.getItemID(),
          fit(item.getItemName(), 22),
          fit(item.getDescription(), 40),
          item.getDateReported(),
          item.getLocation(),
          item.getStatus());
    System.out.println(lightLine);
  }

  private static String fit(String text, int width){
    if(text.length() <= width){
      return text;
    }
    return text.substring(0, width - 3) + "...";
  }
}
