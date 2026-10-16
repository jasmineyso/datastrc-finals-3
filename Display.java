public class Display {
  public static final int MENU_WIDTH = 43;
  private static final int BANNER_FILL = 16;
  public static void displayMenu(String ... choices) {
    for (int i = 0; i < choices.length; i++) {
        System.out.println((i + 1) + " - " + choices[i]);
    }

    System.out.println();
  }

  public static void headerTitle(String title) {
    String fill = "=".repeat(BANNER_FILL);
    System.out.println();
    System.out.println(fill + " " + title.toUpperCase() + " " + fill);
  }

  public static void subHeaderTitle(String title) {
    System.out.println(buildFilledBanner(title.toUpperCase(), MENU_WIDTH, '-'));
  }

  private static String buildFilledBanner(String title, int totalWidth, char fillChar) {
    String label = " " + title + " ";

    if (totalWidth <= label.length()) {
        return label;
    }

    int totalPadding = totalWidth - label.length();
    int left = totalPadding / 2;
    int right = totalPadding - left;
    String fill = String.valueOf(fillChar);

    return fill.repeat(left) + label + fill.repeat(right);
  }
}