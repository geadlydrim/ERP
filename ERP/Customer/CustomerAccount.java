package ERP.Customer;

public class CustomerAccount {
    private int id;

    private String username;
    private String password;
    private static int id_counter = 0;

    public CustomerAccount(String username, String password){
        this.username = username;
        this.password = password;
        id = ++id_counter;
    }

    public CustomerAccount() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
