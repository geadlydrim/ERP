package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class KFCMenu extends MenuListDAO {
    public KFCMenu(){
        this.filePath = "./Database/Menu/kfc_menu.csv";
    }
}
