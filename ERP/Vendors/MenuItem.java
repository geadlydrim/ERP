package ERP.Vendors;

public class MenuItem {
    public String itemName;
    public String itemID;
    public double price;
    public int calories;
    MenuItem next; // Pointer to the next node

    public MenuItem(String itemID, String itemName, double price, int calories) {
        this.itemName = itemName;
        this.itemID = itemID;
        this.price = price;
        this.calories = calories;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("%s - %s, Price: %.2f, Calories: %d", 
                              itemID, itemName, price, calories);
    }
}