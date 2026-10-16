import java.util.*;

public class Main{

    private static ArrayList<Item> items = new ArrayList<>();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;
        while(!isExit){
            Display.headerTitle("Main Menu");
            Display.displayMenu("Report an Item", "Display Items", "Search an Item", "Edit Item Record", "Change Item Status", "Exit");                
            int menu = Validations.menuChecker("Enter Menu Choice: ", 6);

            switch(menu){
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
        System.out.println("Enter Item Details:");
        
        String itemID = Validations.itemIDValidation("Item ID: ", items);
        String name = Validations.stringValidation("Item Name: ");
        String description = Validations.stringValidation("Item Description: ");
        String date = Validations.dateValidation("Date Reported (YYYY-MM-DD): ");
        int location = Validations.locationValidation("Location (1-5): ");

        items.add(new Item(itemID, name, description, date, "Unclaimed", location));
        System.out.println("Item reported successfully.");
    }

    private static void displayItems(){
        boolean returnMainMenu = false;
        
        while(!returnMainMenu){
            Display.headerTitle("Display Items:");
            Display.displayMenu("All Items", "Sort by Date", "Sort by Items Lost", "Sort by Items Found", "Return to Main Menu");
            int menu = Validations.menuChecker("Enter Menu Choice: ", 5);

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
            System.out.println("No items have been reported yet.");
            return;
        }

        String itemID = Validations.itemIDSearchValidation("Enter Item ID to Search: ");

        Item foundItem = null;
        for(Item item : items){
            if(item.getItemID().equals(itemID)){
                foundItem = item;
                break;
            }
        }

        if(foundItem == null){
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

    private static void editItemRecord(){

    }

    private static void changeItemStatus(){

    }


    // --------------- methods for display items -----------------------

    private static void displayAllItems(){

    }

    private static void sortByDate(){

    }

    private static void sortByItemLost(){

    }

    private static void sortByItemsFound(){

    }

}