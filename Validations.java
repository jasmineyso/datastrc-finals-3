import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;
import java.time.format.*;

public class Validations {
  public static Scanner input = new Scanner(System.in); 
    public static int intValidation(String prompt, int max){
    boolean isValidMenu = false;
    int menu = 0;

    while(!isValidMenu){
      System.out.print(prompt);
      String number = input.nextLine();

      if(number.equals("0")){
        throw new BackToMenuException();
      }

      if(number.length() > 1 && !(Character.isLetterOrDigit(number.charAt(0)))){
        System.out.println("Invalid input. Number cannot start with a symbol or space.");
        continue;
      }

      if(number.length() > 1 && number.charAt(0) == '0'){
        System.out.println("Invalid input. Number cannot start with 0.");
        continue;
      }

      try{
        menu = Integer.parseInt(number);

        if(menu >= 1 && menu <= max){
          isValidMenu = true;
        }else{
          System.out.println("Invalid input. Please enter a number between 1 to " + max + ".");
        }
      }catch(NumberFormatException e){
        System.out.println("Invalid input. You must enter an integer number.");
      }
    }
    return menu;
  }

  public static char charValidation(String prompt){
        boolean isValidChar = false;
        char ans = ' ';

        while(!isValidChar){
          System.out.print(prompt);
          String txt = input.nextLine();

          if(txt.equals("0")){
            throw new BackToMenuException();
          }

          if(txt.isEmpty()){
            System.out.println("Invalid input. You cannot leave this empty.");
            continue;
          }

          if(txt.length() > 1){
            System.out.println("Invalid input. Enter y or n only.");
            continue;
          }

          char char1 = Character.toLowerCase(txt.charAt(0));

          if(!((char1 == 'y') || (char1 == 'n'))){
            System.out.println("Invalid input. Enter y or n only.");
            continue;
          }else{
            ans = char1;
            isValidChar = true;
          }
          
        }
        return ans;
    }

    public static String stringValidation(String prompt){
      boolean isValidString = false;
      String str = "";

      while(!isValidString){
          System.out.print(prompt);
          String txt = input.nextLine().trim().replaceAll("\\s+", " ");

          if(txt.equals("0")){
              throw new BackToMenuException();
          }

          if(txt.isEmpty()){
              System.out.println("Invalid input. You cannot leave this empty.");
              continue;
          }else{
              str = txt;
              isValidString = true;
          }
      }
      return str;
  }

  // --------------- itemIDValidation method -----------------------

  public static String itemIDValidation(String prompt, ArrayList<Item> items){
    boolean isValidID = false;
    String itemID = "";

    while(!isValidID){
        System.out.print(prompt);
        String txt = input.nextLine().trim().toUpperCase();

        if(txt.equals("0")){
            throw new BackToMenuException();
        }

        if(txt.isEmpty()){
            System.out.println("Invalid input. You cannot leave this empty.");
            continue;
        }

        if(!txt.matches("ITM-\\d{4}")){
            System.out.println("Invalid input. Item ID must follow the format ITM-0001.");
            continue;
        }

        if(txt.equals("ITM-0000")){
            System.out.println("Invalid input. Item ID cannot be ITM-0000.");
            continue;
        }

        boolean alreadyExists = false;
        for(Item item : items){
            if(item.getItemID().equalsIgnoreCase(txt)){
                alreadyExists = true;
                break;
            }
        }

        if(alreadyExists){
            System.out.println("Invalid input. Item ID " + txt + " already exists.");
            continue;
        }else{
            itemID = txt;
            isValidID = true;
        }
    }
    return itemID;
  }

  // -------------- item id search validation method -----------------------

  public static String itemIDSearchValidation(String prompt){
    boolean isValidID = false;
    String itemID = "";

    while(!isValidID){
        System.out.print(prompt);
        String txt = input.nextLine().trim().toUpperCase();

        if(txt.equals("0")){
            throw new BackToMenuException();
        }

        if(txt.isEmpty()){
            System.out.println("Invalid input. You cannot leave this empty.");
            continue;
        }

        if(!txt.matches("ITM-\\d{4}")){
            System.out.println("Invalid input. Item ID must follow the format ITM-0001.");
            continue;
        }else{
            itemID = txt;
            isValidID = true;
        }
    }
    return itemID;
  }

  public static String dateValidation(String prompt){
    boolean isValidDate = false;
    String date = "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                                                   .withResolverStyle(ResolverStyle.STRICT);

    while(!isValidDate){
        System.out.print(prompt);
        String txt = input.nextLine().trim();

        if(txt.equals("0")){
            throw new BackToMenuException();
        }

        if(txt.isEmpty()){
            System.out.println("Invalid input. You cannot leave this empty.");
            continue;
        }

        LocalDate parsedDate;
        try{
            parsedDate = LocalDate.parse(txt, formatter);
        }catch(DateTimeParseException e){
            System.out.println("Invalid input. Enter a valid date in YYYY-MM-DD format.");
            continue;
        }

        if(parsedDate.isAfter(LocalDate.now())){
            System.out.println("Invalid input. Date reported cannot be in the future.");
            continue;
        }else{
            date = txt;
            isValidDate = true;
        }
    }
    return date;
  }
}