import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

<<<<<<< HEAD
public class Main{

    private static ArrayList<Item> items = new ArrayList<>();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
=======
public class Main {
    private static LostAndFound lostAndFound;
    public Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        FileInitializer.initializeFiles();
        lostAndFound = new LostAndFound();
>>>>>>> origin/master

        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;

        while (!isExit) {
<<<<<<< HEAD
            Display.headerTitle("Main Menu");
            Display.displayMenu("Report an Item", "Display Items", "Search an Item", "Edit Item Record", "Change Item Status", "Exit");                
            int menu = Validations.intValidation("Enter Menu Choice: ", 6);
=======
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
>>>>>>> origin/master

                int menu = Validations.intValidation("Enter Menu Choice: ", 7);

<<<<<<< HEAD
                case 2:
                    displayItems();
                    break;

                case 3 -> searchItem();

                case 4 -> editItemRecord();

                case 5:
                    changeItemStatus();
                    break;
                case 6:
                    isExit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
            }
        }
    }

    private static void reportItem(){
        System.out.println("Enter Item Details:");
        
        String itemID = Validations.itemIDValidation("Item ID: ", items);
=======
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

>>>>>>> origin/master
        String name = Validations.stringValidation("Item Name: ");
        String description = Validations.stringValidation("Item Description: ");
        String date = Validations.dateValidation("Date Reported (YYYY-MM-DD): ");
        int location = Validations.intValidation("Location (1-5): ", 5);

<<<<<<< HEAD
        items.add(new Item(itemID, name, description, date, "Unclaimed", location));
        System.out.println("Item reported successfully.");
    }

    private static void displayItems(){
        boolean returnMainMenu = false;
        
        while(!returnMainMenu){
            Display.headerTitle("Display Items:");
            Display.displayMenu("All Items", "Sort by Date", "Sort by Items Lost", "Sort by Items Found", "Return to Main Menu");
            int menu = Validations.intValidation("Enter Menu Choice: ", 5);

            switch(menu){
                case 1:
                    displayAllItems();
                    break;
                case 2:
                    sortByDate();
                    break;
                case 3:
                    sortByItemLost();
                    break;
                case 4:
                    sortByItemsFound();
                    break;                    
                case 5:
                    returnMainMenu = true;
                    break;
            }
        }
    }

    private static void searchItem(){
        if(items.isEmpty()){
=======
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
>>>>>>> origin/master
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation(
            "Enter Item ID to Search: "
        );

        Item foundItem = null;
        for(Item item : items){
            if(item.getItemID().equals(itemID)){
                foundItem = item;
                break;
            }
        }

        if (foundItem == null) {
            System.out.println("Item " + itemID + " not found.");
        }else{
            Display.headerTitle("Item Details");
            System.out.println("Item ID: " + foundItem.getItemID());
            System.out.println("Item Name: " + foundItem.getItemName());
            System.out.println("Description: " + foundItem.getDescription());
            System.out.println("Date Reported: " + foundItem.getDateReported());
            System.out.println("Location: " + foundItem.getLocation());
            System.out.println("Status: " + foundItem.getStatus());
        }
        

    }

<<<<<<< HEAD
    private static void editItemRecord(){

    }

    private static void changeItemStatus(){

    }


    // --------------- methods for display items -----------------------

    private static void displayAllItems(){
        if(items.isEmpty()){
=======
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
>>>>>>> origin/master
            System.out.println("No items have been reported yet.");
            return;
        }

<<<<<<< HEAD
        Display.subHeaderTitle("All Items");
        Display.itemsTable(items);
    }

    private static void sortByDate(){
        if(items.isEmpty()){
=======
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

        ArrayList<String> itemIDs = history.getItemIDsNewestFirst();

        switch (menu) {
            case 1 -> {
                Display.subHeaderTitle("All Activities");
                showActivityTable(itemIDs);
            }
            case 2 -> {
                Display.subHeaderTitle("Recent Activity");

                ArrayList<String> recent = new ArrayList<>();
                if (!itemIDs.isEmpty()) {
                    recent.add(itemIDs.get(0));
                }
                showActivityTable(recent);
            }
        }
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
    }

    private static void sortByDate() {
        ArrayList<Item> sorted = lostAndFound.sortItemsByDate();

        if (sorted.isEmpty()) {
>>>>>>> origin/master
            System.out.println("No items have been reported yet.");
            return;
        }

<<<<<<< HEAD
        ArrayList<Item> sorted = new ArrayList<>(items);

        for(int i = 1; i < sorted.size(); i++){
            Item current = sorted.get(i);
            int j = i - 1;

            while(j >= 0 && sorted.get(j).getDateReported().compareTo(current.getDateReported()) > 0){
                sorted.set(j + 1, sorted.get(j));
                j--;
            }
            sorted.set(j + 1, current);
        }

=======
>>>>>>> origin/master
        Display.subHeaderTitle("Items Sorted by Date (Oldest to Newest)");
        Display.itemsTable(sorted, "Total items", false);
    }

<<<<<<< HEAD
    private static void sortByItemLost(){

    }

    private static void sortByItemsFound(){

    }

=======
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
    }
>>>>>>> origin/master
}