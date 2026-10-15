import java.util.Scanner;

public class Validations {
  public static Scanner input = new Scanner(System.in); 
    public static int menuChecker(String prompt, int max){
    boolean isValidMenu = false;
    int menu = 0;

    while(!isValidMenu){
      System.out.print(prompt);
      String number = input.nextLine();

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
}
