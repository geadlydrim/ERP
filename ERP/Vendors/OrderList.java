package ERP.Vendors;

// Class to handle the linked list and CSV file operations
public class OrderList extends MenuList {

    public OrderList(int id, String vendorName){
        this.filePath = String.format("./Database/Orders/%s_%s_order_list.csv", id, vendorName);
    }

    public void update(){
        writeToCSV();
    }

    // Method to display all menu items
    public void displayAll() {
        if (head == null) {
            System.out.println("Orders is empty.");
        } else {
            MenuItem current = head;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
        }
    }


}
