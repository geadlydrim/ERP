package BackEnd.DAOs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ActiveOrderDAO {
    private String filePath = "./Database/Orders/active_orders.csv";
    protected MenuItem head; // Head of the linked list

    public void addMenuItem(int id, String itemID, String itemName, double price, int calories) {
        MenuItem newItem = new MenuItem(id, itemID, itemName, price, calories);
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
            head = null;
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if(line.trim().isBlank()){
                    continue;
                }
                String[] parts = line.split(",");
                int i = 0;
                int id = Integer.parseInt(parts[i]);
                String itemName = parts[++i].trim();
                String category = parts[++i].trim();
                double price = Double.parseDouble(parts[++i].trim());
                int calories = Integer.parseInt(parts[++i].trim());
                addMenuItem(id, itemName, category, price, calories);
            }
        } catch (IOException e) {
        }
    }

    public void writeToCSV() {
        if(head == null){
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Empty file deleted successfully.");
                return;
            } else {
                System.out.println("Failed to delete the empty file.");
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("AccountID,ItemID,ItemName,Price,Calories\n"); // Write the header
            MenuItem current = head;
            while (current != null) {
                bw.write(String.format("%s,%s,%s,%.2f,%d\n", 
                                    current.id,
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
}
