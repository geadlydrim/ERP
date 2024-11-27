package ERP.Accounts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerAccTemp extends CustomerAccount{
    public CustomerAccTemp(String username, String password){
        super(username, password);
    }

    public CustomerAccTemp(){
        super();
    }

    @Override
    protected int getCount(){
        int count = 0;
        final String FILE_PATH = "id_count.txt ";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            count = Integer.parseInt(reader.readLine());
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return count;
    }
}
