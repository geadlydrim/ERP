package BackEnd.DAOs;

public class OrderItem extends MenuItem {
    int accountID;
    public OrderItem(int accountID, String itemID, String itemName, double price, int calories){
        super(itemID, itemName, price, calories);
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return String.format("%s - %s, %s, Price: %.2f, Calories: %d", 
                              accountID, itemID, itemName, price, calories);
    }
}
