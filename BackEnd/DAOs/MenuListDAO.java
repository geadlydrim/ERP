package BackEnd.DAOs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Class to handle the linked list and CSV file operations
public abstract class MenuListDAO {
    protected String filePath = "./Database/jollibee_menu.csv";
    protected MenuItem head; // Head of the linked list

    public void read(){
        readFromCSV();
    }

    public void update(){
        writeToCSV();
    }

    public boolean isEmpty(){
        read();
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
            head = null;
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if(line.trim().isBlank()){
                    continue;
                }
                String[] parts = line.split(",");
                int i = 0;
                    String itemName = parts[i].trim();
                    String category = parts[++i].trim();
                    double price = Double.parseDouble(parts[++i].trim());
                    int calories = Integer.parseInt(parts[++i].trim());
                    addMenuItem(itemName, category, price, calories);
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

    public String[] readHeader(){
        String[] columnNames =  null;;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            columnNames = line.split(",");

            for(String name : columnNames){
                name = name.trim();
            }
            return columnNames;
        } catch (IOException e) {
            return columnNames;
        }
    }

    public String[][] readRecords(){
        readFromCSV();
        int size = 0;
        if(isEmpty()){
            return new String[0][0];
        }
        else{
            MenuItem current = head;
            while (current != null) {
                size++;
                current = current.next;
            }
            String records[][] = new String[size][4];

            current = head;
            for(int i = 0; i < size; i++) {
                records[i][0] = current.itemID;
                records[i][1] = current.itemName;
                records[i][2] = Double.toString(current.price);
                records[i][3] = Integer.toString(current.calories);
                current = current.next;
            }

            return records;
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

    public void deleteFile(){
        File file = new File(filePath);
        if(file.delete()){
            System.out.println("File deleted successfully.");
        }
        else{
            System.out.println("File deletion failed.");
        }
    }
}
