package ERP.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidation {
    static Scanner scanner = new Scanner(System.in);

    public int getIntInput() {
        int var1 = 0;
        boolean var2 = false;

    while(!var2) {
       try {
          var1 = scanner.nextInt();
          var2 = true;
          scanner.nextLine();
       } catch (InputMismatchException var4) {
          System.out.println("Invalid input. Please enter integer only.");
          System.out.println("Enter again: ");
          scanner.next();
       }
    }
    return var1;
    }
    
    int getIntInput(boolean signed) {
        int var1 = 0;
        boolean var2 = false;

        while(!var2){
            var1 = getIntInput();
            if(var1 < 0 && signed == false){
                System.out.println("Please enter positive number.");
                System.out.println("Enter again: ");
            }
            else{
                return var1;
            }
        }
        return var1;
    }
    
    public int getOption(boolean signed, int rangeLow, int rangeHigh) {
        int var1 = 0;
        boolean var2 = false;

        while(!var2) {
            if(signed == false){
                var1 = getIntInput(signed);
                if(var1 > rangeHigh || var1 < rangeLow){
                    System.out.println("Input out of range (" + rangeLow + " - " + rangeHigh + ").");
                    System.out.print("Please enter again: ");
                }
                else{
                    return var1;
                }
            }
        }
        return var1;
    }
    
    int get6DPin(){
        int pin;
        int digits = 0;
        
        while(true){
            pin = getIntInput(false);
            digits = String.valueOf(pin).length();
            if(digits != 6){
                System.out.println("Please enter 6 digit number: ");
            }
            else{
                return pin;
            }
        }
    }
    
    String getEmailInput(){
        String email;
        String required = "@aki.com.ph";
        do{
            email = getStringInput();
            if (email.length() >= 11 && email.substring(email.length() - 11).equals(required)) {
                return email;
            } else {
                System.out.println("Email must end with \"@aki.com.ph\" ");
                System.out.println("Please enter again: ");
            }
        }while(true);
    }
   
    public String getStringInput() {
        String var1 = scanner.nextLine();
        return var1;
    }
}