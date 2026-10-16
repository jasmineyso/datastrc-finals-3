public class Main {

    private static LostAndFound lostAndFound;

    public static void main(String[] args) {
        lostAndFound = new LostAndFound();


        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;

        while (!isExit) {
            Display.headerTitle("Main Menu");

            Display.displayMenu(
                "Report an Item",
                "Display All Items",
                "Search an Item",
                "Edit Item Record",
                "Sort Records by Date",
                "Change Item Status",
                "View Specific Item Details",
                "Sort by Item Status",
                "Exit"
            );

            int menu = Validations.menuChecker("Enter Menu Choice: ", 9);

            switch (menu) {
                case 1:
                    reportItem();
                    break;

                case 2:
                    displayAllItems();
                    break;

                case 3:
                    searchItem();
                    break;

                case 4:
                    editItemRecord();
                    break;

                case 5:
                    sortByDate();
                    break;

                case 6:
                    changeItemStatus();
                    break;

                case 7:
                    viewSpecificItemDetails();
                    break;

                case 8:
                    sortByStatus();
                    break;

                case 9:
                    isExit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
            }
        }
    }

        
    public static void reportItem() {
        System.out.println("\n--- Report an Item ---");

        String itemName = "";

        while (itemName.isEmpty()) {
            System.out.print("Enter item name: ");
            itemName = Validations.input.nextLine();

            if (itemName.isEmpty()) {
                System.out.println("Item name cannot be empty!");
            }
        }

        String description = "";

        while (description.isEmpty()) {
            System.out.print("Enter item description: ");
            description = Validations.input.nextLine();

            if (description.isEmpty()) {
                System.out.println("Description cannot be empty!");
            }
        }

        int location = 0;
        boolean validLocation = false;

        while (!validLocation) {
            System.out.print("Enter location number: ");
            String locationInput = Validations.input.nextLine();

            try {
                location = Integer.parseInt(locationInput);

                if (location > 0) {
                    validLocation = true;
                } else {
                    System.out.println("Location number must be greater than 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }

        String itemID = lostAndFound.generateID();
            if (itemID.isEmpty()) {
                System.out.println("Unable to generate an Item ID. Try again later.");
                return;
                }
        String dateReported = java.time.LocalDate.now().toString();
        String status = "Unclaimed";

        Item item = new Item(
            itemID,
            itemName,
            description,
            dateReported,
            status,
            location
        );

        if (lostAndFound.addItem(item)) {
            System.out.println("Item reported successfully!");
            System.out.println("Generated Item ID: " + itemID);
        } else {
            System.out.println("Failed to report item.");
        }
    }
  

    public static void displayAllItems() {
        System.out.println("\n--- All Reported Items ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        for (Item item : lostAndFound.getItems()) {
            System.out.println("-----------------------------------");
            System.out.println("Item ID: " + item.getItemID());
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Description: " + item.getDescription());
            System.out.println("Date Reported: " + item.getDateReported());
            System.out.println("Status: " + item.getStatus());
            System.out.println("Location: " + item.location());
        }

        System.out.println("-----------------------------------");
    }

    
    public static void searchItem() {
        System.out.println("\n--- Search an Item ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        System.out.print("Enter item ID, name, or keyword: ");
        String keyword = Validations.input.nextLine();

        if (keyword.isEmpty()) {
            System.out.println("Search input cannot be empty!");
            return;
        }

        Item item = lostAndFound.searchItem(keyword);

        if (item == null) {
            System.out.println("No matching item found!");
        } else {
            System.out.println("\nItem Found!");
            System.out.println("-----------------------------------");
            System.out.println("Item ID: " + item.getItemID());
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Description: " + item.getDescription());
            System.out.println("Date Reported: " + item.getDateReported());
            System.out.println("Status: " + item.getStatus());
            System.out.println("Location: " + item.location());
            System.out.println("-----------------------------------");
        }
    }

    
    public static void editItemRecord() {
        System.out.println("\n--- Edit Item Record ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        System.out.print("Enter Item ID to edit: ");
        String itemID = Validations.input.nextLine();

        Item item = lostAndFound.findItemByID(itemID);

        if (item == null) {
            System.out.println("Item ID does not exist!");
            return;
        }

        System.out.println("\nCurrent Item Details:");
        System.out.println("Item Name: " + item.getItemName());
        System.out.println("Description: " + item.getDescription());
        System.out.println("Location: " + item.location());

        String itemName = "";

        while (itemName.isEmpty()) {
            System.out.print("\nEnter new item name: ");
            itemName = Validations.input.nextLine();

            if (itemName.isEmpty()) {
                System.out.println("Item name cannot be empty!");
            }
        }

        String description = "";

        while (description.isEmpty()) {
            System.out.print("Enter new description: ");
            description = Validations.input.nextLine();

            if (description.isEmpty()) {
                System.out.println("Description cannot be empty!");
            }
        }

        int location = 0;
        boolean validLocation = false;

        while (!validLocation) {
            System.out.print("Enter new location number: ");
            String locationInput = Validations.input.nextLine();

            try {
                location = Integer.parseInt(locationInput);

                if (location > 0) {
                    validLocation = true;
                } else {
                    System.out.println("Location must be greater than 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }

        boolean updated = lostAndFound.updateItem(
            itemID,
            itemName,
            description,
            location
        );

        if (updated) {
            System.out.println("Item record updated successfully!");
        } else {
            System.out.println("Failed to update item record.");
        }
    }

    
    public static void sortByDate() {
        System.out.println("\n--- Sort Records by Date ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        boolean sorted = lostAndFound.sortItemsByDate();

        if (sorted) {
            System.out.println("Records sorted by date successfully!");
            System.out.println("Order: Oldest to Newest");
        } else {
            System.out.println("Unable to sort records.");
        }
    }

    
    public static void viewSpecificItemDetails() {
        System.out.println("\n--- View Specific Item Details ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        System.out.print("Enter Item ID: ");
        String itemID = Validations.input.nextLine();

        Item item = lostAndFound.findItemByID(itemID);

        if (item == null) {
            System.out.println("Item ID does not exist!");
            return;
        }

        System.out.println("\n--- Item Details ---");
        System.out.println("Item ID: " + item.getItemID());
        System.out.println("Item Name: " + item.getItemName());
        System.out.println("Description: " + item.getDescription());
        System.out.println("Date Reported: " + item.getDateReported());
        System.out.println("Status: " + item.getStatus());
        System.out.println("Location: " + item.location());
    }

    
    public static void changeItemStatus() {
        System.out.println("\n--- Change Item Status ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        System.out.print("Enter Item ID: ");
        String itemID = Validations.input.nextLine();

        Item item = lostAndFound.findItemByID(itemID);

        if (item == null) {
            System.out.println("Item ID does not exist!");
            return;
        }

        System.out.println("Current Status: " + item.getStatus());
        System.out.println("\nSelect New Status:");
        System.out.println("1. Unclaimed");
        System.out.println("2. Claimed");

        int choice = Validations.menuChecker("Enter choice: ", 2);

        String newStatus;

        if (choice == 1) {
            newStatus = "Unclaimed";
        } else {
            newStatus = "Claimed";
        }

        if (item.getStatus().equalsIgnoreCase(newStatus)) {
            System.out.println("Item already has this status!");
            return;
        }

        boolean changed = lostAndFound.changeItemStatus(itemID, newStatus);

        if (changed) {
            System.out.println("Item status updated successfully!");
        } else {
            System.out.println("Failed to update item status.");
        }
    }

    
    public static void sortByStatus() {
        System.out.println("\n--- Sort by Item Status ---");

        if (lostAndFound.isEmpty()) {
            System.out.println("No items found yet.");
            return;
        }

        boolean sorted = lostAndFound.sortItemsByStatus();

        if (sorted) {
            System.out.println("Records sorted by status successfully!");
            System.out.println("Order: Claimed, then Unclaimed");
        } else {
            System.out.println("Unable to sort records.");
        }
    }

}
