package ERP.Login;
import ERP.Customer.*;

public interface LoginDAO {
    void addAccount(CustomerAccount account);
    void updateAccount(CustomerAccount account, CustomerAccount updatedAccount);
    void deleteAccount(CustomerAccount account);
}
// test