package ERP.Login;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ERP.Utils.InputValidation;
import ERP.Accounts.CustomerAccTemp;
import ERP.Accounts.CustomerAccount;
import ERP.Customer.*;


public class CustomerDAO implements LoginDAO {
    private static final String FILE_PATH = "customer_acc_DB.csv";
    private static final String FILE_PATH2 = "customer_acc_id.csv ";

    boolean checkFileExist(){
        File file = new File(FILE_PATH);
        return (file.exists() ? true : false);
    }

    private Map<String, String> readAccountsFromFile(){
        Map<String, String> accounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                accounts.put(username, password);
            }
        } catch (IOException e) {
            if(!checkFileExist()){
                System.out.println("file not found.");
            }
        }

        return accounts;
    }

    private void writeCustomersToFile(Map<String,String> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("username,password\n");
            for(String key : accounts.keySet()){
                writer.write(key.toString() + "," + accounts.get(key).toString() + "\n");
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String,Integer> readAccountId(){
        Map<String,String> accounts = readAccountsFromFile();
        Map<String, Integer> account_id = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH2))){
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                int id = Integer.parseInt(data[1]);
                account_id.put(username, id);
            }
        }
        catch(IOException e){
            System.out.println("test");
            e.printStackTrace();
        }

        return account_id;
    }

    private void writeRecordToFile(Map<String, Integer> account_id){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH2))) {
            writer.write("username,id\n");
            for(String key : account_id.keySet()){
                writer.write(key.toString() + "," + account_id.get(key).toString() + "\n");
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(CustomerAccount account) {
        Map<String, String> accounts = readAccountsFromFile();
        Map<String, Integer> account_id = readAccountId();
        accounts.put(account.getUsername(), account.getPassword());
        account_id.put(account.getUsername(), account.getId());
        writeRecordToFile(account_id);
        writeCustomersToFile(accounts);
        System.out.println("Account added successfully.");
    }

    @Override
    public void updateAccount(CustomerAccount account, CustomerAccount updatedAccount) {
        deleteAccount(account);
        addAccount(updatedAccount);
        System.out.println("Account updated successfully.");
    }

    @Override
    public void deleteAccount(CustomerAccount account) {
        Map<String, String> accounts = readAccountsFromFile();
        accounts.remove(account.getUsername());
        writeCustomersToFile(accounts);
        System.out.println("Account removed successfully.");
    }

    public boolean accountExist(CustomerAccount account){
        Map<String, String> accounts = readAccountsFromFile();
        return((accounts.containsKey(account.getUsername())));
    }

    public boolean loginValid(CustomerAccount account){
        Map<String, String> accounts = readAccountsFromFile();
        if(accountExist(account)){
            if(account.getPassword().equals(accounts.get(account.getUsername())))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        CustomerAccount account;
        InputValidation input = new InputValidation();
        CustomerDAO test = new CustomerDAO();
        int option;

        while(true){
            System.out.println("""
                    [1] - add accounts
                    [2] - update account
                    [3] - remove account
                    [0] - exit
                    """);
            option = input.getIntInput();
            switch(option){
                case 1:
                    account = new CustomerAccount();
                    System.out.println("Username: ");
                    account.setUsername(input.getStringInput());
                    System.out.println("Password: ");
                    account.setPassword(input.getStringInput());
                    test.addAccount(account);
                    break;
                case 2:
                    account = new CustomerAccTemp();
                    CustomerAccount updatedAccount = new CustomerAccTemp();
                    System.out.println("Username: ");
                    account.setUsername(input.getStringInput());
                    System.out.println("Password: ");
                    account.setPassword(input.getStringInput());
                    System.out.println("Updated Username: ");
                    updatedAccount.setUsername(input.getStringInput());
                    System.out.println("Updated Password: ");
                    updatedAccount.setPassword(input.getStringInput());

                    if(test.accountExist(account)){
                        test.updateAccount(account, updatedAccount);
                    }
                    else{
                        System.out.println("account does not exist.");
                    }
                    
                    break;

            }
        }
    }
    
}
