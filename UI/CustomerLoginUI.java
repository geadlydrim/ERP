package UI;

import ERP.Utils.*;
import ERP.Accounts.CustomerAccTemp;
import ERP.Accounts.CustomerAccount;
import ERP.Login.CustomerDAO;

public class CustomerLoginUI {
    CustomerDAO dao = new CustomerDAO();
    InputValidation input = new InputValidation();

    private void Login(){
        String username, password;
        System.out.print("Enter username: ");
        username = input.getStringInput();
        System.out.print("Enter password: ");
        password = input.getStringInput();

        CustomerAccount account = new CustomerAccTemp(username, password);
        if(dao.loginValid(account)){
            System.out.println("Login sucess.");
            CustomerUI.Main(account);
        }
        else{
            System.out.println("Login failed.");
        }
    }

    private void Signup(){
        String username, password;
        boolean done = false;
        do{    
            System.out.print("Enter username: ");
            username = input.getStringInput();
            System.out.print("Enter password: ");
            password = input.getStringInput();

            CustomerAccount account = new CustomerAccount(username, password);
            if(dao.accountExist(account)){
                System.out.println("Username already exists. Please enter a different username.");
            }
            else{
                dao.addAccount(account);
                done = true;
            }
        } while(!done);
    }

    public static void Main(){
        InputValidation input = new InputValidation();
        CustomerLoginUI customerLogin = new CustomerLoginUI();
        int option;
        do{
            Console.clear();
            System.out.print("""
                Customer Login
                [1] - Login
                [2] - Signup
                [0] - Exit
                Enter option: """);
            option = input.getOption(false, 0, 2);
            switch (option) {
                case 1:
                    customerLogin.Login();
                    break;
                case 2:
                    customerLogin.Signup();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }while(option != 0);

    }
}
