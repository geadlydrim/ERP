package UI;

import ERP.Accounts.CustomerAccount;
import ERP.Utils.*;
import ERP.Vendors.OrderList;

public class CustomerUI {
    CustomerAccount account;
    InputValidation input = new InputValidation();

    public CustomerUI(CustomerAccount account){
        this.account = account;
    }

    private void placeOrder(){
        int option;
        Console.clear();
        System.out.print("""
                Choose a vendor
                [1] - Jollibee
                [2] - McDonald's
                [3] - Dunkin' Donuts
                [4] - Burger King
                [5] - KFC
                [6] - Starbucks
                [0] - Back
                Option: """);
        option = input.getOption(false, 0, 6);

        switch(option){
            case 1:
                OrderUI.Main(account, "jollibee");
                break;
            case 2:
                OrderUI.Main(account, "mcdonalds");
                break;
            case 3:
                OrderUI.Main(account, "dunkindonuts");
                break;
            case 4:
                OrderUI.Main(account, "burgerking");
                break;
            case 5:
                OrderUI.Main(account, "kfc");
                break;
            case 6:
                OrderUI.Main(account, "starbucks");
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
                }
    }

    private void viewOrders(){
        int id = account.getId();
        String vendors[] = {"jollibee", "mcdonalds", "dunkindonuts", "burgerking", "kfc", "starbucks"};

        for(String vendor : vendors){
            OrderList orders = new OrderList(id, vendor);
            if(orders.isEmpty())
                continue;

            System.out.printf("%s Orders\n", vendor.toUpperCase());
            orders.displayAll();
            System.out.println("");
        }
    }

    public static void Main(CustomerAccount account){
        CustomerUI ui = new CustomerUI(account);
        InputValidation input = new InputValidation();
        int choice;
        do{
            Console.clear();
            System.out.print("""
                Welcome to Code Panda. What do you want to do?
                [1] - Place Order
                [2] - View Orders
                [3] - Checkout Orders
                [0] - Logout
                Option: """);
            choice = input.getOption(false, 0, 2);

            switch(choice){
                case 1:
                    ui.placeOrder();
                    break;
                case 2:
                    ui.viewOrders();
                    break;
                case 0:
                    return;
            }
            Console.waitEnter();
        } while(choice != 0);
    }
}
