package ERP.Accounts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CustomerAccount implements Account {
    private int id;
    private static final String FILE_PATH = "id_count.txt ";

    private String username;
    private String password;

    public CustomerAccount(String username, String password){
        this.username = username;
        this.password = password;
        id = getCount();
    }

    protected int getCount(){
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            count = Integer.parseInt(reader.readLine());
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Integer.toString(++count));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    public CustomerAccount() {
        id = getCount();
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
