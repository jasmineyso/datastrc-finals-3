import java.util.ArrayList;

public class Display {
  public static final int MENU_WIDTH = 121;
  public static void displayMenu(String ... choices){
    String lightLine = "-".repeat(MENU_WIDTH);
    for (int i = 0; i < choices.length; i++) {
        System.out.printf("\t[%d] %s%n", i + 1, choices[i]);
    }

    System.out.println(lightLine);
  }

  public static void headerTitle(String title){
    String note = "Note: Press 0 to go back to Main Menu anytime";
    String heavyLine = "=".repeat(MENU_WIDTH); 
    int centerTitle = Math.max(0, (MENU_WIDTH - title.length()) / 2);
    int centerNote = Math.max(0, (MENU_WIDTH - note.length()) / 2);

    System.out.println();
    System.out.println(heavyLine);
    System.out.println(" ".repeat(centerTitle) + title.toUpperCase());
    System.out.println(heavyLine);

    System.out.println(" ".repeat(centerNote) + note + "\n");
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


  public static void itemsTable(ArrayList<Item> items, String totalLabel, boolean withStatus){
    String format = withStatus ? " %-10s | %-22s | %-40s | %-13s | %-8s | %-12s%n" : " %-10s | %-22s | %-40s | %-13s | %-8s%n";
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

  public static void displayAnItem(String[][] itemDetails){
    int labelWidth = 0;
    for (String[] row : itemDetails) {
      labelWidth = Math.max(labelWidth, row[0].length());
    }

    for (String[] row : itemDetails) {
      System.out.printf("\t%-" + labelWidth + "s : %s%n", row[0], row[1]);
    }
  }

  private static String fit(String text, int width){
    if(text.length() <= width){
      return text;
    }
    return text.substring(0, width - 3) + "...";
  }
}