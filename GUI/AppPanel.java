package GUI;

import javax.swing.*;

import BackEnd.Utils.Header;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppPanel extends JPanel {
    public AppPanel(JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;  // Center horizontally
        gbc.gridy = 0;  // Center vertically
        gbc.insets = new Insets(10, 0, 10, 0);  // 10px top and bottom margin
        gbc.anchor = GridBagConstraints.CENTER;  // Align content to the center

        JLabel label = new JLabel("Welcome to Code Panda");
        label.setBounds(10, 20, 200, 25);
        label.setFont(new Header());
        add(label, gbc);

        gbc.gridy = 1;
        JButton csLgnBtn = new JButton("Customer Login");
        csLgnBtn.setBounds(10, 60, 150, 25);
        add(csLgnBtn, gbc);

        csLgnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(new CustomerLoginPanel(cardPanel, cardLayout), "CustomerLoginPanel");
                cardLayout.show(cardPanel, "CustomerLoginPanel");
            }
        });
    }
}
