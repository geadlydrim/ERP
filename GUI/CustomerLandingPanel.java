package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BackEnd.Accounts.CustomerAccount;
import BackEnd.DAOs.ActiveOrderDAO;
import BackEnd.DAOs.OrderDAO;
import BackEnd.Utils.BackBtn;
import BackEnd.Vendors.OrderList;

public class CustomerLandingPanel extends JPanel{
    public CustomerLandingPanel(JPanel cardPanel, CardLayout cardLayout, CustomerAccount account){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel header = new JLabel("Welcome to Code Panda!");
        add(header, gbc);

        gbc.gridy++;
        JButton placeOrderBtn = new JButton("Place Order");
        gbc.gridy++;
        add(placeOrderBtn, gbc);
        gbc.gridy++;
        JButton viewOrderBtn = new JButton("View Orders");
        add(viewOrderBtn, gbc);
        gbc.gridy++;
        JButton checkoutOrderBtn = new JButton("Checkout Orders");
        add(checkoutOrderBtn, gbc);

        placeOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(new PlaceOrderPanel(cardPanel, cardLayout, account), "PlaceOrderPanel");
                cardLayout.show(cardPanel, "PlaceOrderPanel");
            }
        });

        viewOrderBtn.addActionListener(e -> {
            cardPanel.add(new ViewOrdersPanel(cardPanel, cardLayout, account), "ViewOrdersPanel");
                cardLayout.show(cardPanel, "ViewOrdersPanel");
        });

        checkoutOrderBtn.addActionListener(e -> {
            cardPanel.add(new CheckoutOrderPanel(cardPanel, cardLayout, account), "CheckoutOrderPanel");
            cardLayout.show(cardPanel, "CheckoutOrderPanel");
        });
    }
}

class ViewOrdersPanel extends JPanel{
    public ViewOrdersPanel(JPanel cardPanel, CardLayout cardLayout, CustomerAccount account){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
    
        int id = account.getId();
        String vendors[] = {"jollibee", "mcdonalds", "dunkindonuts", "burgerking", "kfc", "starbucks"};
    
        // Find non-empty order lists
        List<String> existingVendors = new ArrayList<>();
        List<DefaultTableModel> models = new ArrayList<>();
    
        for (String vendor : vendors) {
            OrderList orders = new OrderList(id, vendor);
            if (!orders.isEmpty()) {
                existingVendors.add(vendor);
                models.add(new DefaultTableModel(orders.readRecords(), orders.readHeader()));
            }
        }
    
        // Wrapper panel to hold tables
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.GRAY);
        GridBagConstraints innerGbc = new GridBagConstraints();
    
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.CENTER;
        innerGbc.insets = new Insets(5, 0, 5, 0);
    
        for (int i = 0; i < models.size(); i++) {
            JLabel label= new JLabel(String.format("%s Orders", existingVendors.get(i).toUpperCase()));
            wrapper.add(label, innerGbc);
    
            innerGbc.gridy++;
            JTable table = new JTable(models.get(i));
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            JScrollPane tableScrollPane = new JScrollPane(table);
    
            wrapper.add(tableScrollPane, innerGbc);
            innerGbc.gridy++;
        }

        if(models.size() == 0){
            JOptionPane.showMessageDialog(wrapper, "Orders is currently empty.");
        }
        
        innerGbc.gridy++;
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "CustomerLandingPanel");
        wrapper.add(backBtn, innerGbc);
        // Add wrapper into the main scroll panel
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setPreferredSize(new Dimension(500,400));
        add(scrollPane, gbc);
        
        
    }
}

class CheckoutOrderPanel extends JPanel{
    public CheckoutOrderPanel(JPanel cardPanel, CardLayout cardLayout, CustomerAccount account){
        setLayout(new GridBagLayout());

        System.out.println(account.getId());
        int id = account.getId();
        String vendors[] = {"jollibee", "mcdonalds", "dunkindonuts", "burgerking", "kfc", "starbucks"};
    
        ActiveOrderDAO dao = new ActiveOrderDAO();
        List<String[][]> tables = new ArrayList<>();
        List<String> exist = new ArrayList<>();
    
        for (String vendor : vendors) {
            OrderList orders = new OrderList(id, vendor);
            if (!orders.isEmpty()) {
                tables.add(orders.readRecords());
                exist.add(vendor);
            }
        }

        int j = 0;
        for(String[][] array : tables){
            for(int i = 0; i < array.length; i++){
                dao.readFromCSV();
                dao.addMenuItem(id, array[i][0], array[i][1], Double.parseDouble(array[i][2]), Integer.parseInt(array[i][3]));
                dao.writeToCSV();
            }
            OrderDAO listDao = new OrderDAO(account, exist.get(j++));
            listDao.deleteFile();
        }
        
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "CustomerLandingPanel");
        add(backBtn);
        JOptionPane.showMessageDialog(cardPanel, "Checkout order success! Please wait for a rider to accept.");
        
        
    }
}