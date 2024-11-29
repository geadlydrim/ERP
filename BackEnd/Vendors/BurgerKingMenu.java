package BackEnd.Vendors;

import BackEnd.DAOs.MenuListDAO;

public class BurgerKingMenu extends MenuListDAO {
    public BurgerKingMenu(){
        this.filePath = "./Database/Menu/burgerking_menu.csv";
    }
}
