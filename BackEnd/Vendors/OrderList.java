package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

// Class to handle the linked list and CSV file operations
public class OrderList extends MenuListDAO {

    public OrderList(int id, String vendorName){
        this.filePath = String.format("./Database/Orders/%s_%s_order_list.csv", id, vendorName);
    }

    public void update(){
        writeToCSV();
    }
}
