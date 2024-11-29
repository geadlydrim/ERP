package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class DunkinDonutsMenu extends MenuListDAO {
    public DunkinDonutsMenu(){
        this.filePath = "./Database/Menu/dunkindonuts_menu.csv";
    }
}
