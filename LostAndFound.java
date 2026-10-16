
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class LostAndFound {

    private ArrayList<Item> items = new ArrayList<>();
    private Stack<String> statusHistory = new Stack<>();

    // Initialize the file and load saved records
    public LostAndFound() {
        initializeFile();
        initializeActivityFile();
        loadItems();
        loadActivityHistory();
    }

    // Create ITEMS.txt if it does not exist
    private void initializeFile() {
        File file = new File("ITEMS.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error initializing item file!");
        }
    }

    // Load item records from the file
    private void loadItems() {
        items.clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("ITEMS.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 6) {
                    try {
                        Item item = new Item(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            Integer.parseInt(data[5])
                        );

                        items.add(item);

                    } catch (NumberFormatException e) {
                        System.out.println(
                            "Skipping record with invalid location."
                        );
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading item records!");
        }
    }

    // Save all item records to the file
    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ITEMS.txt"))) {

            for (Item item : items) {
                writer.write(
                    item.getItemID() + "|" +
                    item.getItemName() + "|" +
                    item.getDescription() + "|" +
                    item.getDateReported() + "|" +
                    item.getStatus() + "|" +
                    item.location()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving item records!");
        }
    }

    // Check whether there are no item records
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Check whether an item ID already exists
    public boolean idExists(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return true;
            }
        }

        return false;
    }

    // Generate a unique ID using the current year and four digits   
    public String generateID() {
        Random random = new Random();
        String itemID;
        int attempts = 0;
        boolean exists = true;

        while (exists && attempts < 10000) {
            int number = random.nextInt(10000);

            itemID = LocalDate.now().getYear()
                    + String.format("%04d", number);

            exists = idExists(itemID);
            attempts++;

            if (!exists) {
                return itemID;
            }
        }

        return "";
    }


    // Add a new item
    public boolean addItem(Item item) {
        if (idExists(item.getItemID())) {
            return false;
        }

        items.add(item);
        saveItems();

        return true;
    }

    // Get all item records for other classes to use
    public ArrayList<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Linear Search: find an item by ID, name, or description
    public Item searchItem(String keyword) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(keyword)
                    || item.getItemName().toLowerCase()
                        .contains(keyword.toLowerCase())
                    || item.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase())) {

                return item;
            }
        }

        return null;
    }

    // Find an item using its exact ID
    public Item findItemByID(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }

        return null;
    }

        
    // Edit an item's name, description, and location
    public boolean updateItem(String itemID, String itemName,
                              String description, int location) {

        Item item = findItemByID(itemID);

        if (item == null) {
            return false;
        }

        item.setItemName(itemName);
        item.setDescription(description);
        item.setLocation(location);

        saveItems();
        return true;
    }

    // Delete an item record
    public boolean removeItem(String itemID) {
        Item item = findItemByID(itemID);

        if (item == null) {
            return false;
        }

        items.remove(item);
        saveItems();

        return true;
    }

    
        
    // Change an item's status and record the change
    public boolean changeItemStatus(String itemID, String newStatus) {
        
        if (!newStatus.equalsIgnoreCase("Unclaimed")
                && !newStatus.equalsIgnoreCase("Claimed")) {
            return false;
        }

        Item item = findItemByID(itemID);

        if (item == null) {
            return false;
        }

        String oldStatus = item.getStatus();

        if (oldStatus.equalsIgnoreCase(newStatus)) {
            return false;
        }

        String activity = "Item ID: " + itemID
                + " | " + oldStatus
                + " -> " + newStatus;

        item.setStatus(newStatus);
        saveItems();

        statusHistory.push(activity);
        saveActivity(activity);

        return true;
    }



    
    // Insertion Sort: sort item records by report date
    // Dates must use the yyyy-MM-dd format
    public boolean sortItemsByDate() {
        if (items.isEmpty()) {
            return false;
        }

        for (int i = 1; i < items.size(); i++) {
            Item current = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getDateReported()
                    .compareTo(current.getDateReported()) > 0) {

                items.set(j + 1, items.get(j));
                j--;
            }

            items.set(j + 1, current);
        }

        saveItems();
        return true;
    }

        
    // Return the most recent status changes first
    public ArrayList<String> getRecentStatusChanges() {
        ArrayList<String> history = new ArrayList<>();

        Stack<String> temporaryStack = new Stack<>();
        temporaryStack.addAll(statusHistory);

        while (!temporaryStack.isEmpty()) {
            history.add(temporaryStack.pop());
        }

        return history;
    }

        
    // Create ACTIVITY.txt if it does not exist
    private void initializeActivityFile() {
        File file = new File("ACTIVITY.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error initializing activity file!");
        }
    }

    // Load saved status changes into the Stack
    private void loadActivityHistory() {
        statusHistory.clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("ACTIVITY.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    statusHistory.push(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading activity history!");
        }
    }

    // Save a status change to ACTIVITY.txt
    private void saveActivity(String activity) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ACTIVITY.txt", true))) {

            writer.write(activity);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving activity history!");
        }
    }   

    
    
    public boolean sortItemsByStatus() {
        if (items.isEmpty()) {
            return false;
        }

        for (int i = 1; i < items.size(); i++) {
            Item current = items.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    items.get(j).getStatus()
                        .compareToIgnoreCase(current.getStatus()) > 0) {
                items.set(j + 1, items.get(j));
                j--;
            }

            items.set(j + 1, current);
        }

        saveItems();
        return true;
    }
}
