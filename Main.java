import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import backend.ActivityHistory;
import backend.FileInitializer;
import backend.Item;
import backend.LostAndFound;
import guide.BackToMenuException;
import guide.Display;
import guide.ItemIDGenerator;
import guide.Validations;

public class Main {
    private static LostAndFound lostAndFound;
    public Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        FileInitializer.initializeFiles();
        lostAndFound = new LostAndFound();

        System.out.println("\n\n");
        Display.centerText("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

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
                System.out.println(e.getMessage());
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
        int container = selectAvailableContainer(items, "Store Item");

        String itemID = ItemIDGenerator.nextID(items);

        Item item = new Item(itemID, name, description, date, container);
        lostAndFound.addItem(item);

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem("Report",new String[][] {
            {"Item ID", item.getItemID()},
            {"Item Name", item.getItemName()},
            {"Item Description", item.getDescription()},
            {"Item Container", String.valueOf(item.getContainer())},
            {"Date Reported", item.getDateReported()}
        });

        logActivity("Item ID: " + item.getItemID() + " | " + item.getItemName());
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
            "Sort By Container",
            "Sort by Date",
            "Sort by Items Lost (Unclaimed)",
            "Sort by Items Found (Claimed)"
        );

        int menu = Validations.intValidation("Enter Menu Choice: ", 5);

        switch (menu) {
            case 1 -> displayAllItems(items);
            case 2 -> sortByContainer();
            case 3 -> sortByDate(items);
            case 4 -> sortByItemLost(items);
            case 5 -> sortByItemsFound(items);
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

        Item foundItem = lostAndFound.findItemByID(itemID);

        if (foundItem == null) {
            System.out.println("Item " + itemID + " not found.");
            return;
        }

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem("Search", new String[][] {
            {"Item ID", foundItem.getItemID()},
            {"Item Name", foundItem.getItemName()},
            {"Item Description", foundItem.getDescription()},
            {"Item Container", String.valueOf(foundItem.getContainer())},
            {"Date Reported", foundItem.getDateReported()},
            {"Item Status", foundItem.getStatus()}
        });
        logActivity("User Accessed: Search Item " + itemID);
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

        Item item = lostAndFound.findItemByID(itemID);

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
        Display.displayAnItem("Edit", new String[][] {
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
            "Edit Container"
        );

        int choice = Validations.intValidation("Enter Menu Choice: ", 3);

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
                    if (lostAndFound.updateItem(
                            item.getItemID(),
                            name,
                            item.getDescription(),
                            item.getContainer())) {
                        System.out.println(
                            "'" + oldName + "' is updated to '"
                            + name + "' successfully."
                        );

                        logActivity(
                            "Edited " + item.getItemID()
                            + " | Name: " + oldName + " -> " + name
                        );
                    }
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
                    if (lostAndFound.updateItem(
                            item.getItemID(),
                            item.getItemName(),
                            description,
                            item.getContainer())) {
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
                }
                break;

            case 3:
                int container = selectAvailableContainer(items, "Change Container");

                if (item.getContainer() == container) {
                    System.out.println(
                        "Container is already set to '" + item.getContainer()
                        + "'. No changes were made."
                    );
                } else {
                    int oldContainer = item.getContainer();
                    if (lostAndFound.updateItem(
                            item.getItemID(),
                            item.getItemName(),
                            item.getDescription(),
                            container)) {
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
        Item item = lostAndFound.findItemByID(itemID);

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

        Display.subHeaderTitle("Item Details");
        Display.displayAnItem("Mark", new String[][] {
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
    }


    private static void activityHistoryMenu() {
    ActivityHistory history = lostAndFound.getActivityHistory();

        if (history.isEmpty()) {
            System.out.println("No activities have been recorded yet.");
            return;
        }

        Display.headerTitle("Activity History");
        Display.displayMenu(
            "Display 10 Recent Activities",
            "Display Recent Activity"
        );

        int menu = Validations.intValidation("Enter Menu Choice: ", 2);

        ArrayList<String> activities = history.getRecentActivities();

        switch (menu) {
            case 1 -> {
                Display.subHeaderTitle("Recent Activities");
                showActivities(activities);
            }
            case 2 -> {
                Display.subHeaderTitle("Recent Activity");

                String recent = history.viewLatestActivity();

                System.out.println(recent);
                System.out.println("----------------------------------------");
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

    private static void logActivity(String message) {
        String time = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        lostAndFound.getActivityHistory().addActivity(
            "[" + time + "] " + message
        );
    }

    private static int selectAvailableContainer(ArrayList<Item> items, String action){
        boolean doneSelect = false;
        int container = 0;
        while(!doneSelect){
            Display.headerTitle(action);
            Display.containerSelection(items);

            container = Validations.intValidation("Select Container (1-5): ", 5);
            if(lostAndFound.isContainerFull(container)){
                System.out.println("        Container " + container + " is FULL. Select another container.");
                doneSelect = false;
            }
        

            Display.subHeaderTitle("CONTAINER " + container + " selected.");

            int ctr = lostAndFound.getContainerItemCount(container);
            int remaining = LostAndFound.CONTAINER_CAPACITY - ctr;

            System.out.println("\n        Capacity of container " + container + ": " + ctr + "/" + LostAndFound.CONTAINER_CAPACITY);

            System.out.println("        Available slots: " + remaining);

            System.out.println();
            System.out.println("=".repeat(Display.MENU_WIDTH));

            char confirm = Validations.charValidation("        Confirm Container " + container + "? (y/n): ");

            if(confirm == 'n'){
                System.out.println("        Container selection cancelled.");
                doneSelect = false;
            }else{
                doneSelect = true;
            }
        }

        return container;
    }

    private static void displayAllItems(ArrayList<Item> items) {
        Display.subHeaderTitle("All Items");
        Display.itemsTable(items, "Total items", true);
        logActivity("User Accessed: Displayed All Items");
    }

    private static void sortByContainer(){
        Display.headerTitle("Sort by container");
        ArrayList<Item> items = lostAndFound.getItems();

        if(items.isEmpty()){
            System.out.println("No items have been reported yet.");
            return;
        }

        Display.displayMenu(
            "Container 1",
            "Container 2",
            "Container 3",
            "Container 4",
            "Container 5"
        );

        int menu = Validations.intValidation("Enter Menu Choice: ", 7);

        switch (menu) {
            case 1 -> sortContainer(menu);
            case 2 -> sortContainer(menu);
            case 3 -> sortContainer(menu);
            case 4 -> sortContainer(menu);
            case 5 -> sortContainer(menu);
        }
    }

    private static void sortContainer(int container){
        ArrayList<Item> items = lostAndFound.getItems();
        ArrayList<Item> itemContainer = new ArrayList<>();

        for(Item item : items){
            if(item.getContainer() == container){
                itemContainer.add(item);
            }
        }

        Display.subHeaderTitle("Sort Container " + container);

        if(itemContainer.isEmpty()){
            System.out.println("Container is empty.");
            return;
        }

        Display.itemsContainer(itemContainer, "Total items");
        logActivity("User Accessed: Sort by Container");
    }

    private static void sortByDate(ArrayList<Item> items) {
        Display.headerTitle("Sort by Date");
        if (items.isEmpty()) {
            System.out.println("No items have been reported yet.");
            return;
        }

        ArrayList<Item> sorted = new ArrayList<>(items);

        for (int i = 1; i < sorted.size(); i++) {
            Item current = sorted.get(i);
            int j = i - 1;

            while (j >= 0 && sorted.get(j).getDateReported()
                    .compareTo(current.getDateReported()) > 0) {

                sorted.set(j + 1, sorted.get(j));
                j--;
            }

            sorted.set(j + 1, current);
        }

        Display.subHeaderTitle("Items Sorted by Date (Oldest to Newest)");
        Display.itemsTable(sorted, "Total items", false);
        logActivity("User Accessed: Sort by Date");
    }

    private static void sortByItemLost(ArrayList<Item> items) {
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

    private static void sortByItemsFound(ArrayList<Item> items) {
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