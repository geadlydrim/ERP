package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BackEnd.Accounts.CustomerAccount;
import BackEnd.DAOs.OrderDAO;
import BackEnd.Utils.BackBtn;
import BackEnd.Utils.Header;
import BackEnd.Vendors.OrderList;

public class PlaceOrderPanel extends JPanel{
    public PlaceOrderPanel(JPanel cardPanel, CardLayout cardLayout, CustomerAccount account){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setPreferredSize(new Dimension(500, 400));
        Font btnFont = new Font("", Font.PLAIN, 25);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(wrapper, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 2;               // Number of columns to occupy
        JLabel header = new JLabel("Choose a Vendor");
        header.setFont(new Header());
        wrapper.add(header,gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0,0,0,0);
        JButton jbBtn = new JButton("Jollibee");
        jbBtn.setFont(btnFont);
        wrapper.add(jbBtn,gbc);

        gbc.gridx++;
        JButton kfcBtn = new JButton("KFC");
        kfcBtn.setFont(btnFont);
        wrapper.add(kfcBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton ddBtn = new JButton("Dunkin Donuts");
        ddBtn.setFont(btnFont);
        wrapper.add(ddBtn, gbc);

        gbc.gridx++;
        JButton mcdoBtn = new JButton("McDonalds");
        mcdoBtn.setFont(btnFont);
        wrapper.add(mcdoBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton bkBtn = new JButton("Burger King");
        bkBtn.setFont(btnFont);
        wrapper.add(bkBtn, gbc);

        gbc.gridx++;
        JButton sbBtn = new JButton("Starbucks");
        sbBtn.setFont(btnFont);
        wrapper.add(sbBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "CustomerLandingPanel");
        add(backBtn, gbc);

        jbBtn.addActionListener(new SetVendor(account, "jollibee", cardPanel, cardLayout));
        kfcBtn.addActionListener(new SetVendor(account, "kfc", cardPanel, cardLayout));
        ddBtn.addActionListener(new SetVendor(account, "dunkindonuts", cardPanel, cardLayout));
        mcdoBtn.addActionListener(new SetVendor(account, "mcdonalds", cardPanel, cardLayout));
        bkBtn.addActionListener(new SetVendor(account, "burgerking", cardPanel, cardLayout));
        sbBtn.addActionListener(new SetVendor(account, "starbucks", cardPanel, cardLayout));
    }
}

class SetVendor implements ActionListener{
    CustomerAccount account;
    String vendorName;
    JPanel cardPanel;
    CardLayout cardLayout;
    public SetVendor(CustomerAccount account, String vendorName, JPanel cardPanel, CardLayout cardLayout){
        this.account = account;
        this.vendorName = vendorName;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        OrderDAO dao = new OrderDAO(account, vendorName);
        cardPanel.add(new VendorChosenPanel(cardPanel, cardLayout, dao), "VendorChosenPanel");
        cardLayout.show(cardPanel, "VendorChosenPanel");
    }

}

class VendorChosenPanel extends JPanel{
    public VendorChosenPanel(JPanel cardPanel, CardLayout cardLayout, OrderDAO dao){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton addBtn = new JButton("Add order");
        JButton removeBtn = new JButton("Remove order");
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "PlaceOrderPanel");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(addBtn, gbc);
        gbc.gridy++;
        add(removeBtn, gbc);
        gbc.gridy++;
        add(backBtn, gbc);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(new AddMenuItemPanel(cardPanel, cardLayout, dao), "AddMenuItemPanel");
                cardLayout.show(cardPanel, "AddMenuItemPanel");
            }
        });
        
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(new RemoveMenuItemPanel(cardPanel, cardLayout, dao), "RemoveMenuItemPanel");
                cardLayout.show(cardPanel, "RemoveMenuItemPanel");
            }
        });
    }
}

class AddMenuItemPanel extends JPanel{
    public AddMenuItemPanel(JPanel cardPanel, CardLayout cardLayout, OrderDAO dao){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setPreferredSize(new Dimension(400,370));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(wrapper, gbc);
        
        JLabel header = new JLabel(String.format("%s Menu", dao.vendorName.toUpperCase()));
        JLabel info = new JLabel("Select an item to add/remove");
        header.setFont(new Header());
        wrapper.add(header, gbc);
        
        String[] columnNames = dao.menu.readHeader();
        String[][] menuItems = dao.menu.readRecords();
        DefaultTableModel model = new DefaultTableModel(menuItems, columnNames);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;  // Allow horizontal expansion
        gbc.weighty = 1.0;  // Allow vertical expansion
        JScrollPane scrollPane = new JScrollPane(table);
        wrapper.add(scrollPane, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        wrapper.add(info, gbc);

        gbc.gridy++;
        JButton addItemBtn = new JButton("Add item");
        wrapper.add(addItemBtn, gbc);
        
        gbc.gridy++;
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "VendorChosenPanel");
        wrapper.add(backBtn, gbc);

        addItemBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                String itemID = (String) table.getValueAt(selectedRow, 0);
                dao.addOrder(itemID);
            }
            else{
                JOptionPane.showMessageDialog(addItemBtn, "Please select an item to add");
            }
        });
    }
}

class RemoveMenuItemPanel extends JPanel {
    public RemoveMenuItemPanel(JPanel cardPanel, CardLayout cardLayout, OrderDAO dao){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setPreferredSize(new Dimension(400,370));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(wrapper, gbc);

        JLabel header = new JLabel(String.format("Your %s Orders", dao.vendorName.toUpperCase()));
        JLabel info = new JLabel("Select an item to add/remove");
        header.setFont(new Header());
        wrapper.add(header, gbc);

        OrderList orderList = new OrderList(dao.account.getId(), dao.vendorName);

        String[] columnNames = orderList.readHeader();
        String[][] menuItems = orderList.readRecords();
        DefaultTableModel model = new DefaultTableModel(menuItems, columnNames);
        
        JTable table = new JTable(model);
        if(!orderList.isEmpty()){
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getColumnModel().getColumn(0).setPreferredWidth(30);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
        }
        else{
            JOptionPane.showMessageDialog(this, String.format("%s orders is currently empty.", dao.vendorName.toUpperCase()));
        }
        
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;  // Allow horizontal expansion
        gbc.weighty = 1.0;  // Allow vertical expansion
        JScrollPane scrollPane = new JScrollPane(table);
        wrapper.add(scrollPane, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy++;
        wrapper.add(info, gbc);

        gbc.gridy++;
        JButton removeItemBtn = new JButton("Remove item");
        wrapper.add(removeItemBtn, gbc);
        
        gbc.gridy++;
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "VendorChosenPanel");
        wrapper.add(backBtn, gbc);

        removeItemBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                String itemID = (String) table.getValueAt(selectedRow, 0);
                orderList.read();
                orderList.removeItem(itemID);
                orderList.update();
                
                String[][] updatedMenuItems = orderList.readRecords(); // Fetch updated records
                DefaultTableModel updatedModel = new DefaultTableModel(updatedMenuItems, columnNames);
                table.setModel(updatedModel); // Set the new model to the table
                
                // Adjust column widths if necessary
                if (!orderList.isEmpty()) {
                    table.getColumnModel().getColumn(0).setPreferredWidth(30);
                    table.getColumnModel().getColumn(1).setPreferredWidth(200);
                }

                revalidate();
                repaint();
            }
            else{
                JOptionPane.showMessageDialog(removeItemBtn, "Please select an item to add");
            }
        });
    }
}