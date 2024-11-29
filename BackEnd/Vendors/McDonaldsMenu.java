package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class McDonaldsMenu extends MenuListDAO {
    public McDonaldsMenu(){
        this.filePath = "./Database/Menu/mcdonalds_menu.csv";
    }
}
