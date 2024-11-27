package UI;
import ERP.Accounts.CustomerAccount;
import ERP.Utils.*;
import ERP.Vendors.*;

public class OrderUI {
    InputValidation input = new InputValidation();
    protected String vendorName;
    MenuList menu;

    public OrderUI(String vendorName){
        this.vendorName = vendorName;
        switch(vendorName){
            case "jollibee":
                menu = new JollibeeMenu();
                break;
            case "mcdonalds":
                menu = new McDonaldsMenu();
                break;
            case "dunkindonuts":
                menu = new DunkinDonutsMenu();
                break;
            case "burgerking":
                menu = new BurgerKingMenu();
                break;
            case "kfc":
                menu = new KFCMenu();
                break;
            case "starbucks":
                menu = new StarbucksMenu();
                break;
            default:
                System.out.println("Invalid fast food type. Please try again.");
                menu = null; // Optional: Set to null if no valid match
                break;
        }
    }

    private void addOrder(CustomerAccount account){
        menu.read();
        OrderList order = new OrderList(account.getId(), vendorName);

        int option = -1;
        do{
            System.out.println(String.format("%s Menu", vendorName.toUpperCase()));
            menu.displayAll();

            System.out.print("Enter item to add (A1): ");
            String itemID = input.getStringInput();

            MenuItem item = menu.getItem(itemID);

            if(item != null){
                order.addMenuItem(item.itemID, item.itemName, item.price, item.calories);
                System.out.println("""
                        Add item success. Would you like to add another item? 
                        [1] - Yes
                        [2] - No
                        """);
                option = input.getOption(false, 0, 2);
                if(option == 1){
                    Console.clear();
                    continue;
                }
                else if (option == 2){
                    order.update();
                }
            }
            else{
                System.out.println("Invalid input. Add order failed.");
            }
        } while(option != 2);
    }

    private void removeOrder(CustomerAccount account){
        OrderList order = new OrderList(account.getId(), vendorName);
        order.read();

        if(order.isEmpty()){
            System.out.println("Orders is empty.");
            return;
        }

        int option = -1;
        do{
            order.displayAll();
            System.out.print("Enter item to remove (A1): ");
            String itemID = input.getStringInput();
            MenuItem item = order.getItem(itemID);
            

            if(item != null){
                order.removeItem(itemID);
                System.out.println("""
                        Remove item success. Would you like to remove another item? 
                        [1] - Yes
                        [2] - No
                        """);
                option = input.getIntInput();
                if(option == 1){
                    Console.clear();
                    continue;
                }
                else if (option == 2){
                    order.update();
                }
            }
            else{
                System.out.println("Invalid input. Remove order failed.");
            }
        } while(option != 2);
    }

    public static void Main(CustomerAccount account, String vendorName){
        InputValidation input = new InputValidation();
        
        OrderUI ui = new OrderUI(vendorName);
        int choice;

        do{
            Console.clear();
            System.out.printf(String.format("""
                    %s Order
                    [1] - Add order
                    [2] - Remove Order
                    [0] - Back
                    Option: 
                    """, vendorName.toUpperCase()));
            choice = input.getOption(false, 0, 2);

            switch(choice){
                case 1:
                    ui.addOrder(account);
                    break;
                case 2:
                    ui.removeOrder(account);
                    break;
                case 0:
                    return;
            }
        } while(choice != 0);
    }
}
