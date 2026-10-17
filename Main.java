import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static LostAndFound lostAndFound = new LostAndFound();
    public Scanner sc = new Scanner(System.in);

    public static void main(String[] args){

        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;

        while (!isExit) {
            Display.headerTitle("Main Menu");
            Display.displayMenu("Report an Item", "Display Items", "Search an Item", "Edit Item Record", "Change Item Status", "Exit");                
            int menu = Validations.intValidation("Enter Menu Choice: ", 6);

            switch (menu) {
                case 1:
                    reportItem();
                    break;

                case 2:
                    displayItems();
                    break;

                case 3:
                    searchItem();
                    break;

                case 4:
                    editItemRecord();
                    break;

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
        Display.displayItem(item);
        System.out.println("Item reported successfully.");
    }

    private static void displayItems(){
        ArrayList<Item> items = lostAndFound.getItems();
        if(items.isEmpty()){
            System.out.println("No items have been reported yet.");
            return;
        }
        boolean returnMainMenu = false;
        
        while(!returnMainMenu){
            Display.headerTitle("Display Items:");
            Display.displayMenu("All Items", "Sort by Date", "Sort by Items Lost", "Sort by Items Found", "Return to Main Menu");
            int menu = Validations.intValidation("Enter Menu Choice: ", 5);

            switch(menu){
                case 1:
                    displayAllItems(items);
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
        ArrayList<Item> items = lostAndFound.getItems();
        if(items.isEmpty()){
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation("Enter Item ID to Search: ");

        Item foundItem = lostAndFound.searchItem(itemID);

        if(foundItem == null){
            System.out.println("Item " + itemID + " not found.");
            return;
        }

        Display.headerTitle("Item Details");
        Display.displayItem(foundItem);
    }

    private static void editItemRecord(){

    }

    private static void changeItemStatus(){

    }


    // --------------- methods for display items -----------------------

    private static void displayAllItems(ArrayList<Item> items){
        Display.subHeaderTitle("All Items");
        Display.itemsTable(items);
    }

    private static void sortByDate(){
        ArrayList<Item> sorted = lostAndFound.sortItemsByDate();
        if(sorted.isEmpty()){
            System.out.println("No items have been reported yet.");
            return;
        }
        
        Display.subHeaderTitle("Items Sorted by Date (Oldest to Newest)");
        Display.itemsTable(sorted);
    }

    private static void sortByItemLost(){

    }

    private static void sortByItemsFound(){

    }

}