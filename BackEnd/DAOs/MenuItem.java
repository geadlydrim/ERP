package BackEnd.DAOs;

public class MenuItem {
    public int id;
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

    public MenuItem(int id, String itemID, String itemName, double price, int calories) {
        this(itemID, itemName, price, calories);
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s - %s, Price: %.2f, Calories: %d", 
                              itemID, itemName, price, calories);
    }
}