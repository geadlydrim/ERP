package BackEnd.DAOs;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import BackEnd.Accounts.CustomerAccTemp;
import BackEnd.Accounts.CustomerAccount;

public class CustomerDAO {
    private static final String FILE_PATH = "./Database/Accounts/customer_acc_DB.csv";
    private static final String FILE_PATH2 = "./Database/Accounts/customer_acc_id.csv ";

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

    public void addAccount(CustomerAccount account) {
        Map<String, String> accounts = readAccountsFromFile();
        Map<String, Integer> account_id = readAccountId();
        accounts.put(account.getUsername(), account.getPassword());
        account_id.put(account.getUsername(), account.getId());
        writeRecordToFile(account_id);
        writeCustomersToFile(accounts);
        System.out.println("Account added successfully.");
    }

    public void updateAccount(CustomerAccount account, CustomerAccount updatedAccount) {
        deleteAccount(account);
        addAccount(updatedAccount);
        System.out.println("Account updated successfully.");
    }

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

    public CustomerAccount getAccount(String username){
        Map<String, String> accounts = readAccountsFromFile();
        Map<String,Integer> account_id = readAccountId();
        CustomerAccTemp account = new CustomerAccTemp(username, accounts.get(username));
        account.setId(account_id.get(username));

        return account;
    }

    public boolean loginValid(CustomerAccount account){
        Map<String, String> accounts = readAccountsFromFile();
        if(accountExist(account)){
            if(account.getPassword().equals(accounts.get(account.getUsername())))
                return true;
        }
        return false;
    }   
}
