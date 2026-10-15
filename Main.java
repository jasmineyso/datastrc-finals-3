import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("CAMPUS LOST AND FOUND MANAGEMENT SYSTEM");

        boolean isExit = false;
        while(!isExit){
            Display.headerTitle("Main Menu");
            Display.displayMenu("Report an Item", "Display All Items", "Search an Item", "Edit Item Record", "Sort Records by Date", "Change Item Status", "View Specific Item Details", "Sort by Item Status", "Exit");                
            int menu = Validations.menuChecker("Enter Menu Choice: ", 9);

            switch(menu){
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

    public static void reportItem(){

    }

    public static void displayAllItems(){

    }

    public static void searchItem(){

    }

    public static void editItemRecord(){

    }

    public static void sortByDate(){

    }

    public static void viewSpecificItemDetails(){

    }

    public static void changeItemStatus(){

    }

    public static void sortByStatus(){

    }


}