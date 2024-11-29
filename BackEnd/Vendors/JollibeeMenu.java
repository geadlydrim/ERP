package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class JollibeeMenu extends MenuListDAO {
    public JollibeeMenu(){
        this.filePath = "./Database/Menu/jollibee_menu.csv";
    }
}
