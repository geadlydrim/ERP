package BackEnd.DAOs;
import BackEnd.Accounts.CustomerAccount;
import BackEnd.DAOs.OrderDAO;
import BackEnd.Utils.*;
import BackEnd.Vendors.*;

public class OrderDAO {
    InputValidation input = new InputValidation();
    public String vendorName;
    public CustomerAccount account;
    public MenuListDAO menu;

    public OrderDAO(CustomerAccount account,String vendorName){
        this.account = account;
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

    public void addOrder(String itemID){
        OrderList order = new OrderList(account.getId(), vendorName);
        order.read();
        MenuItem item = menu.getItem(itemID);

        if(item != null){
            order.addMenuItem(item.itemID, item.itemName, item.price, item.calories);
            order.update();
        }
        else{
            System.out.println("Invalid input. Add order failed.");
        }
    }

    public void removeOrder(String itemID){
        OrderList order = new OrderList(account.getId(), vendorName);
        order.read();
        MenuItem item = order.getItem(itemID);
        if(item != null){
            order.removeItem(itemID);
            order.update();
        }
        else{
            System.out.println("Invalid input. Remove order failed.");
        }
    }

    public void deleteFile(){
        OrderList order = new OrderList(account.getId(), vendorName);
        order.deleteFile();
    }
}
