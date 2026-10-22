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
            try {
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
            } catch (BackToMenuException e) {
                System.out.println("Returning to Main Menu.");
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
        int container = Validations.intValidation("Container (1-5): ", 5);

        String itemID = ItemIDGenerator.nextID(items);

        Item item = new Item(itemID, name, description, date, container);
        lostAndFound.addItem(item);

        logActivity("Item ID: " + item.getItemID() + " | " + item.getItemName());

        System.out.println("Item reported successfully.");

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem(new String[][] {
            {"Item ID", item.getItemID()},
            {"Item Name", item.getItemName()},
            {"Item Description", item.getDescription()},
            {"Item Container", String.valueOf(item.getContainer())},
            {"Date Reported", item.getDateReported()}
        });
    }

    private static void displayItemsMain() {
        Display.headerTitle("Display Items");
        ArrayList<Item> items = lostAndFound.getItems();

        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

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
            {"Item Container", String.valueOf(foundItem.getContainer())},
            {"Date Reported", foundItem.getDateReported()}
        });
        logActivity("User Accessed: Search Item");
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
            {"Item Container", String.valueOf(item.getContainer())},
            {"Date Reported", item.getDateReported()}
        });

        Display.headerTitle("Edit Item Record");
        Display.displayMenu(
            "Edit Item Name",
            "Edit Description",
            "Edit Container",
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
                int container = Validations.intValidation(
                    "Enter New Container (1-5): ", 5
                );

                if (item.getContainer() == container) {
                    System.out.println(
                        "Container is already set to '" + item.getContainer()
                        + "'. No changes were made."
                    );
                } else {
                    int oldContainer = item.getContainer();
                    item.setContainer(container);

                    System.out.println(
                        "'" + oldContainer + "' is updated to '"
                        + container + "' successfully."
                    );

                    logActivity(
                        "Edited " + item.getItemID()
                        + " | Container: " + oldContainer
                        + " -> " + container
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
            {"Item Container", String.valueOf(item.getContainer())},
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
        logActivity("User Accessed: Claimed an Item");
    }


    private static void activityHistoryMenu() {
    ActivityHistory history = lostAndFound.getActivityHistory();

        if (history.isEmpty()) {
            System.out.println("No activities have been recorded yet.");
            return;
        }

        Display.headerTitle("Activity History");
        Display.displayMenu(
            "Display All Activities",
            "Display Recent Activity"
        );

        int menu = Validations.intValidation("Enter Menu Choice: ", 2);

        ArrayList<String> activities = history.getActivitiesNewestFirst();

        switch (menu) {
            case 1 -> {
                Display.subHeaderTitle("All Activities");
                showActivities(activities);
            }
            case 2 -> {
                Display.subHeaderTitle("Recent Activity");

                ArrayList<String> recent = new ArrayList<>();

                if (!activities.isEmpty()) {
                    recent.add(activities.get(0));
                }

                showActivities(recent);
            }
        }
        logActivity("User Accessed: Displayed History Menu");
    }
    private static void showActivities(ArrayList<String> activities) {
        if (activities.isEmpty()) {
            System.out.println("No activities have been recorded yet.");
            return;
        }

        for (String activity : activities) {
            System.out.println(activity);
        }

        System.out.println("----------------------------------------");
        System.out.println("Total activities: " + activities.size());
    }

    private static void showActivityTable(ArrayList<String> itemIDs) {
        ArrayList<String> claimedIDs =
            lostAndFound.getActivityHistory().getClaimedItemIDs();
        ArrayList<Item> rows = new ArrayList<>();

        for (String itemID : itemIDs) {
            Item item = lostAndFound.findItemByID(itemID);

            if (item == null) {
                continue;
            }

            if (claimedIDs.contains(item.getItemID())) {
                item.setStatus("Claimed");
            }

            rows.add(item);
        }

        Display.itemsTable(rows, "Total activities", true);
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
        Display.itemsTable(items, "Total items", false);
        logActivity("User Accessed: Displayed All Items");
    }

    private static void sortByDate() {
        ArrayList<Item> sorted = lostAndFound.sortItemsByDate();

        if (sorted.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        Display.subHeaderTitle("Items Sorted by Date (Oldest to Newest)");
        Display.itemsTable(sorted, "Total items", false);
        logActivity("User Accessed: Sort by Date");
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
        Display.itemsTable(lostItems, "Total items", false);
        logActivity("User Accessed: Sort Item by Lost");
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
        Display.itemsTable(foundItems, "Total items", false);
        logActivity("User Accessed: Sorted Items Found");
    }
}