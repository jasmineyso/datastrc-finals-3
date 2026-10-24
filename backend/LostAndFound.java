package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LostAndFound {

    private ArrayList<Item> items = new ArrayList<>();
    private ActivityHistory activityHistory = new ActivityHistory();

    public static final int ITEM_ID = 0, ITEM_NAME = 1, ITEM_DESCRIPTION = 2, ITEM_DATE_REPORTED = 3, ITEM_CONTAINER = 4, ITEM_FIELD_COUNT = 5;

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
        itemRecord[ITEM_CONTAINER] = String.valueOf(item.getContainer());

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
                            Integer.parseInt(data[ITEM_CONTAINER].trim())
                        );

                        items.add(item);

                    } catch (NumberFormatException e) {
                        System.out.println(
                            "Skipping record with invalid container."
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

    public static final int CONTAINER_COUNT = 5;
    public static final int CONTAINER_CAPACITY = 50;

    /** Returns the number of currently stored (unclaimed) items in a container. */
    public int getContainerItemCount(int container) {
        loadItems();
        int count = 0;
        for (Item item : items) {
            if (item.getContainer() == container
                    && item.getStatus().equalsIgnoreCase("Unclaimed")) {
                count++;
            }
        }
        return count;
    }

    public boolean isContainerFull(int container) {
        return getContainerItemCount(container) >= CONTAINER_CAPACITY;
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

    public Item findItemByID(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }

        return null;
    }

    public boolean updateItem(String itemID, String itemName, String description, int container){
        Item item = findItemByID(itemID);

        if(item == null){
            System.out.println("No item found.");
            return false;
        }

        item.setItemName(itemName);
        item.setDescription(description);
        item.setContainer(container);

        rewriteFile();
        return true;
    }

    public void removeItem(String itemID){
        Item item = findItemByID(itemID);

        if(item == null){
            System.out.println("No item found.");
            return;
        }

        items.remove(item);
        rewriteFile();
    }

    private void rewriteFile(){
        try(BufferedWriter out = new BufferedWriter(new FileWriter("ITEMS.txt"))){
            for(Item item : items){
                out.write(buildItemRecord(item));
                out.newLine();
            }
        }catch(IOException e){
            System.out.println("Could not save items: " + e.getMessage());
        }
    }

    public ActivityHistory getActivityHistory() {
        return activityHistory;
    }
}