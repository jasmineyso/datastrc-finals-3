import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LostAndFound {

    private ArrayList<Item> items = new ArrayList<>();
    private ActivityHistory activityHistory = new ActivityHistory();

    public static final int ITEM_ID = 0, ITEM_NAME = 1, ITEM_DESCRIPTION = 2, ITEM_DATE_REPORTED = 3, ITEM_LOCATION = 4, ITEM_FIELD_COUNT = 5;

    public LostAndFound() {
        initializeFile();
        loadItems();
    }

    private void initializeFile() {
        File file = new File("ITEMS.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error initializing item file!");
        }
    }

    public String buildItemRecord(Item item){
        String[] itemRecord = new String[ITEM_FIELD_COUNT];
        itemRecord[ITEM_ID] = item.getItemID();
        itemRecord[ITEM_NAME] = item.getItemName();
        itemRecord[ITEM_DESCRIPTION] = item.getDescription();
        itemRecord[ITEM_DATE_REPORTED] = item.getDateReported();
        itemRecord[ITEM_LOCATION] = String.valueOf(item.getLocation());

        return String.join("|", itemRecord);
    }

    private void loadItems() {
        items.clear();

        File file = new File("ITEMS.txt");
        if(!file.exists()){return;}

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()){continue;}
                String[] data = line.split("\\|", -1);

                if (data.length == ITEM_FIELD_COUNT) {
                    try {
                        Item item = new Item(
                            data[ITEM_ID].trim(),
                            data[ITEM_NAME].trim(),
                            data[ITEM_DESCRIPTION].trim(),
                            data[ITEM_DATE_REPORTED].trim(),
                            Integer.parseInt(data[ITEM_LOCATION].trim())
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

    public void saveItems(String itemRecord) {
        try(BufferedWriter out = new BufferedWriter(new FileWriter("ITEMS.txt", true))){
            out.write(itemRecord);
            out.newLine();
        }catch(IOException e){
                System.out.println("Could not save items: " + e.getMessage());
            }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean idExists(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return true;
            }
        }

        return false;
    }

    public void addItem(Item item) {
        items.add(item);
        String itemRecord = buildItemRecord(item);
        saveItems(itemRecord);
    }

    public ArrayList<Item> getItems(){
        loadItems();
        ArrayList<String> claimed = getActivityHistory().getClaimedItemIDs();
        for(Item item : items){
            if (item == null) {
                continue;
            }

            if (claimed.contains(item.getItemID())) {
                item.setStatus("Claimed");
            }
        }
        return items;
    }

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

    public Item findItemByID(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }

        return null;
    }

    public void updateItem(String itemID, String itemName,
                              String description, int location) {

        Item item = findItemByID(itemID);

        if (item == null) {
            System.out.println("No item found.");
            return;
        }

        item.setItemName(itemName);
        item.setDescription(description);
        item.setLocation(location);

        removeItem(itemID);
        addItem(item);
    }

    public void removeItem(String itemID) {
        Item item = findItemByID(itemID);

        if (item == null) {
            System.out.println("No item found.");
            return;
        }

        items.remove(item);
        removeRecord(itemID, ITEM_ID);
    }

    public void removeRecord(String searchFor, int colIndex) {
        File file = new File("ITEMS.txt");
        StringBuilder updated = new StringBuilder();

        try (Scanner read = new Scanner(file)) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length > colIndex && parts[colIndex].trim().equalsIgnoreCase(searchFor)) {
                    continue;
                }
                updated.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error in reading file.");
            return;
        }

        try (FileWriter writer = new FileWriter("ITEMS.txt")) {
            writer.write(updated.toString());
        } catch (IOException e) {
            System.out.println("Error in writing file.");
        }
    }


    public void changeItemStatus(String itemID, String newStatus) {
        
        if(newStatus.equalsIgnoreCase("Claimed")){
            newStatus = "Claimed";
        }else{
            newStatus = "Unclaimed";
        }

        Item item = findItemByID(itemID);
        if(item == null){System.out.println("Item not found."); return;}

        String oldStatus = item.getStatus();
        if(oldStatus.equalsIgnoreCase(oldStatus)){
            System.out.println("Status did not change.");
            return;
        }

        item.setStatus(newStatus);
        saveItems(buildItemRecord(item));

        String activity = "Item ID: " + itemID
                + " | " + oldStatus
                + " -> " + newStatus;

        activityHistory.addActivity(activity);
    }

    public ArrayList<Item> sortItemsByDate() {
        if (items.isEmpty()) {
            return new ArrayList<>();
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
        return items;
    }

    public ActivityHistory getActivityHistory() {
        return activityHistory;
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
        return true;
    }
}