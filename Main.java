import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static LostAndFound lostAndFound;
    public Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        FileInitializer.initializeFiles();
        lostAndFound = new LostAndFound();

        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;

        while (!isExit) {
            Display.headerTitle("Main Menu");
            Display.displayMenu(
                "Report an Item",
                "Display Items",
                "Search an Item",
                "Edit Item Record",
                "Mark as Claimed",
                "Activity History",
                "Exit"
            );

            int menu = Validations.intValidation("Enter Menu Choice: ", 7);

            switch (menu) {
                case 1 -> reportItem();
                case 2 -> displayItemsMain();
                case 3 -> searchItem();
                case 4 -> editItemRecord();
                case 5 -> markAsClaimed();
                case 6 -> activityHistoryMenu();
                case 7 -> isExit = true;
            }
        }

        System.out.println("Exiting the program. Goodbye!");
    }

    private static void reportItem() {
        ArrayList<Item> items = lostAndFound.getItems();

        Display.headerTitle("Report An Item");
        Display.subHeaderTitle("Enter Item Details");

        String name = Validations.stringValidation("Item Name: ");
        String description = Validations.stringValidation("Item Description: ");
        String date = LocalDate.now().toString();
        int location = Validations.intValidation("Location (1-5): ", 5);

        String itemID = ItemIDGenerator.nextID(items);

        Item item = new Item(itemID, name, description, date, location);
        lostAndFound.addItem(item);

        logActivity("Reported " + item.getItemID() + " | " + item.getItemName());

        System.out.println("Item reported successfully.");

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem(new String[][] {
            {"Item ID", item.getItemID()},
            {"Item Name", item.getItemName()},
            {"Item Description", item.getDescription()},
            {"Item Location", String.valueOf(item.getLocation())},
            {"Date Reported", item.getDateReported()}
        });
    }

    private static void displayItemsMain() {
        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        Display.headerTitle("Display Items:");
        Display.displayMenu(
            "All Items",
            "Sort by Date",
            "Sort by Items Lost (Unclaimed)",
            "Sort by Items Found (Claimed)",
            "Return to Main Menu"
        );

        int menu = Validations.intValidation("Enter Menu Choice: ", 5);

        switch (menu) {
            case 1 -> displayAllItems(items);
            case 2 -> sortByDate();
            case 3 -> sortByItemLost();
            case 4 -> sortByItemsFound();
        }
    }

    private static void searchItem() {
        Display.headerTitle("Search an Item");

        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation(
            "Enter Item ID to Search: "
        );

        Item foundItem = lostAndFound.searchItem(itemID);

        if (foundItem == null) {
            System.out.println("Item " + itemID + " not found.");
            return;
        }

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem(new String[][] {
            {"Item ID", foundItem.getItemID()},
            {"Item Name", foundItem.getItemName()},
            {"Item Description", foundItem.getDescription()},
            {"Item Location", String.valueOf(foundItem.getLocation())},
            {"Date Reported", foundItem.getDateReported()}
        });
    }

    private static void editItemRecord() {
        Display.headerTitle("Edit Item Record");

        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation(
            "Enter Item ID to Edit: "
        );

        Item item = lostAndFound.searchItem(itemID);

        if (item == null) {
            System.out.println("Item " + itemID + " not found.");
            return;
        }

        if (item.getStatus().equalsIgnoreCase("Claimed")) {
            System.out.println(
                "Item " + itemID
                + " is already claimed. You cannot change its record."
            );
            return;
        }

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem(new String[][] {
            {"Item ID", item.getItemID()},
            {"Item Name", item.getItemName()},
            {"Item Description", item.getDescription()},
            {"Item Location", String.valueOf(item.getLocation())},
            {"Date Reported", item.getDateReported()}
        });

        Display.headerTitle("Edit Item Record");
        Display.displayMenu(
            "Edit Item Name",
            "Edit Description",
            "Edit Location",
            "Return"
        );

        int choice = Validations.intValidation("Enter Menu Choice: ", 4);

        switch (choice) {
            case 1:
                String name = Validations.stringValidation("Enter New Item Name: ");

                if (item.getItemName().equalsIgnoreCase(name)) {
                    System.out.println(
                        "Name is already set to '" + item.getItemName()
                        + "'. No changes were made."
                    );
                } else {
                    String oldName = item.getItemName();
                    item.setItemName(name);

                    System.out.println(
                        "'" + oldName + "' is updated to '" + name + "' successfully."
                    );

                    logActivity(
                        "Edited " + item.getItemID()
                        + " | Name: " + oldName + " -> " + name
                    );
                }
                break;

            case 2:
                String description = Validations.stringValidation(
                    "Enter New Description: "
                );

                if (item.getDescription().equalsIgnoreCase(description)) {
                    System.out.println(
                        "Description is already set to '" + item.getDescription()
                        + "'. No changes were made."
                    );
                } else {
                    String oldDescription = item.getDescription();
                    item.setDescription(description);

                    System.out.println(
                        "'" + oldDescription + "' is updated to '"
                        + description + "' successfully."
                    );

                    logActivity(
                        "Edited " + item.getItemID()
                        + " | Description: " + oldDescription
                        + " -> " + description
                    );
                }
                break;

            case 3:
                int location = Validations.intValidation(
                    "Enter New Location (1-5): ", 5
                );

                if (item.getLocation() == location) {
                    System.out.println(
                        "Location is already set to '" + item.getLocation()
                        + "'. No changes were made."
                    );
                } else {
                    int oldLocation = item.getLocation();
                    item.setLocation(location);

                    System.out.println(
                        "'" + oldLocation + "' is updated to '"
                        + location + "' successfully."
                    );

                    logActivity(
                        "Edited " + item.getItemID()
                        + " | Location: " + oldLocation
                        + " -> " + location
                    );
                }
                break;
        }
    }

    private static void markAsClaimed() {
        Display.headerTitle("Mark as Claimed");

        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation("Enter Item ID: ");
        Item item = lostAndFound.searchItem(itemID);

        if (item == null) {
            System.out.println("Item " + itemID + " not found.");
            return;
        }

        if (item.getStatus().equalsIgnoreCase("Claimed")) {
            System.out.println(
                "Status is already set to " + item.getStatus()
                + ". No changes were made."
            );
            return;
        }

        System.out.println("Item found!");

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem(new String[][] {
            {"Item ID", item.getItemID()},
            {"Item Name", item.getItemName()},
            {"Item Description", item.getDescription()},
            {"Item Location", String.valueOf(item.getLocation())},
            {"Date Reported", item.getDateReported()}
        });

        char choice = Validations.charValidation(
            "\nDo you want to mark this item as claimed? (y/n): "
        );

        String status = (choice == 'y') ? "Claimed" : "Unclaimed";

        if (status.equalsIgnoreCase("Unclaimed")) {
            System.out.println(
                "Status is still unclaimed. No changes were made."
            );
            return;
        }

        item.setStatus(status);
        logActivity(
            "Item ID: " + item.getItemID() + " | Unclaimed -> Claimed"
        );

        System.out.println("\nItem claimed successfully.");
    }

    private static void activityHistoryMenu() {
        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        ArrayList<String> claimedIDs =
            lostAndFound.getActivityHistory().getClaimedItemIDs();

        for (Item item : items) {
            if (claimedIDs.contains(item.getItemID())) {
                item.setStatus("Claimed");
            }
        }

        Display.headerTitle("Activity History");
        Display.itemsTable(claimedFirst(items));
    }

    private static ArrayList<Item> claimedFirst(ArrayList<Item> items) {
        ArrayList<Item> ordered = new ArrayList<>();

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Claimed")) {
                ordered.add(item);
            }
        }

        for (Item item : items) {
            if (!item.getStatus().equalsIgnoreCase("Claimed")) {
                ordered.add(item);
            }
        }

        return ordered;
    }

    private static void logActivity(String message) {
        String time = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        lostAndFound.getActivityHistory().addActivity(
            "[" + time + "] " + message
        );
    }

    private static void displayAllItems(ArrayList<Item> items) {
        Display.subHeaderTitle("All Items");
        Display.itemsTable(items);
    }

    private static void sortByDate() {
        ArrayList<Item> sorted = lostAndFound.sortItemsByDate();

        if (sorted.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        Display.subHeaderTitle("Items Sorted by Date (Oldest to Newest)");
        Display.itemsTable(sorted);
    }

    private static void sortByItemLost() {
        ArrayList<Item> items = lostAndFound.getItems();
        ArrayList<Item> lostItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Unclaimed")) {
                lostItems.add(item);
            }
        }

        if (lostItems.isEmpty()) {
            System.out.println("No unclaimed items found.");
            return;
        }

        Display.subHeaderTitle("Lost Items");
        Display.itemsTable(lostItems);
    }

    private static void sortByItemsFound() {
        ArrayList<Item> items = lostAndFound.getItems();
        ArrayList<Item> foundItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Claimed")) {
                foundItems.add(item);
            }
        }

        if (foundItems.isEmpty()) {
            System.out.println("No claimed items found.");
            return;
        }

        Display.subHeaderTitle("Found Items");
        Display.itemsTable(foundItems);
    }
}