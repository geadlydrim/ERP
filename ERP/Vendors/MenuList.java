package ERP.Vendors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Class to handle the linked list and CSV file operations
public abstract class MenuList {
    protected String filePath = "./Database/jollibee_menu.csv";
    protected MenuItem head; // Head of the linked list

    public void read(){
        readFromCSV();
    }

    public void update(){
        writeToCSV();
    }

    public boolean isEmpty(){
        return (head == null ? true : false);
    }

    // Method to add a new menu item to the linked list
    public void addMenuItem(String itemID, String itemName, double price, int calories) {
        MenuItem newItem = new MenuItem(itemID, itemName, price, calories);
        if (head == null) {
            head = newItem;
        } else {
            MenuItem current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newItem;
        }
    }

    // Method to read the CSV file and populate the linked list
    public void readFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String itemName = parts[0].trim();
                    String category = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int calories = Integer.parseInt(parts[3].trim());
                    addMenuItem(itemName, category, price, calories);
                }
            }
        } catch (IOException e) {
        }
    }

    public void writeToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("ItemID,ItemName,Price,Calories\n"); // Write the header
            MenuItem current = head;
            while (current != null) {
                bw.write(String.format("%s,%s,%.2f,%d\n", 
                                    current.itemID, 
                                    current.itemName, 
                                    current.price, 
                                    current.calories));
                current = current.next;
            }
            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            System.err.println("Error updating the CSV file: " + e.getMessage());
        }
    }


    // Method to display all menu items
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("Menu is empty.");
        } 
        else {
            MenuItem current = head;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
        }
    }

    public MenuItem getItem(String itemID){
        if(isEmpty()){
            System.out.println("Menu is empty.");
        }
        else if(head.itemID.equals(itemID)){
            return head;
        }
        else {
            MenuItem current = head;
            while(current.next != null && !(current.itemID.equals(itemID))){
                current = current.next;
            }

            if(current != null){
                return current;
            }
        }
        return null;
    }

    public void removeItem(String itemID){
        if(isEmpty()){
            System.out.println("Menu is empty.");
        }
        else if(head.itemID.equals(itemID)){
            head = head.next;
        }
        else {
            MenuItem current = head;
            while(current.next != null && !(current.next.itemID.equals(itemID))){
                current = current.next;
            }

            if(current.next != null){
                current.next = current.next.next;
            }
        }
        return;
    }
}
