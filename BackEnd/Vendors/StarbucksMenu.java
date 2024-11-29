package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class StarbucksMenu extends MenuListDAO {
    public StarbucksMenu(){
        this.filePath = "./Database/Menu/starbucks_menu.csv";
    }
}
