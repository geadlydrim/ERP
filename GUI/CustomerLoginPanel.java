package GUI;

import javax.swing.*;

import BackEnd.Accounts.CustomerAccTemp;
import BackEnd.Accounts.CustomerAccount;
import BackEnd.DAOs.CustomerDAO;
import BackEnd.Utils.BackBtn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerLoginPanel extends JPanel{
    public CustomerLoginPanel(JPanel cardPanel, CardLayout cardLayout){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton login = new JButton("Login");
        add(login, gbc);

        gbc.gridy = 1;
        JButton signup = new JButton("Signup");
        add(signup, gbc);

        gbc.gridy = 2;
        JButton backBtn = new BackBtn(cardPanel, cardLayout, "AppPanel");
        add(backBtn, gbc);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardPanel.add(new LoginPanel(cardPanel, cardLayout), "LoginPanel");
                cardLayout.show(cardPanel, "LoginPanel");
            }
        });

        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(new SignupPanel(cardPanel, cardLayout), "SignupPanel");
                cardLayout.show(cardPanel, "SignupPanel");
            }
            
        });
    }
}

class LoginPanel extends JPanel{
    public LoginPanel(JPanel cardPanel, CardLayout cardLayout){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usrnmLabel = new JLabel("Username:");
        add(usrnmLabel, gbc);
        gbc.gridx++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200,30));
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel pwLabel = new JLabel("Password:");
        add(pwLabel, gbc);
        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200,30));
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitLogin = new JButton("Login");
        add(submitLogin, gbc);

        submitLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JLabel msgFailed = new JLabel("Incorrect username or password. Please try again.");
                msgFailed.setForeground(Color.RED);

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                CustomerAccount temp = new CustomerAccTemp(username, password);
                CustomerDAO dao = new CustomerDAO();

                if(dao.loginValid(temp)){
                    JOptionPane.showMessageDialog(submitLogin, "Login success.", "Status", JOptionPane.INFORMATION_MESSAGE);
                    temp = dao.getAccount(username);
                    cardPanel.add(new CustomerLandingPanel(cardPanel, cardLayout, temp), "CustomerLandingPanel");
                    cardLayout.show(cardPanel, "CustomerLandingPanel");
                }
                else{
                    JOptionPane.showMessageDialog(submitLogin, "Login failed.");
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.gridwidth = 2;
                    add(msgFailed, gbc);
                    revalidate();
                    repaint();
                }
            }
        });
    }
}

class SignupPanel extends JPanel{
    public SignupPanel(JPanel cardPanel, CardLayout cardLayout){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usrnmLabel = new JLabel("Username:");
        add(usrnmLabel, gbc);
        gbc.gridx++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200,30));
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel pwLabel = new JLabel("Password:");
        add(pwLabel, gbc);
        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200,30));
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel confirmPwLabel = new JLabel("Confirm Password:");
        add(confirmPwLabel, gbc);
        gbc.gridx++;
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200,30));
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton submitSignup = new JButton("Create Account");
        add(submitSignup, gbc);

        JLabel msgFailed = new JLabel("Username already exists. Please enter a unique username.");
        msgFailed.setForeground(Color.RED);
        JLabel msgFailed2 = new JLabel("Passwords do not match. Please try again.");
        msgFailed2.setForeground(Color.RED);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(msgFailed, gbc);
        add(msgFailed2, gbc);
        msgFailed.setVisible(false);
        msgFailed2.setVisible(false);

        submitSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                msgFailed.setVisible(false);
                msgFailed2.setVisible(false);

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if (password.isBlank() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(submitSignup, "Password cannot be empty!");
                }
                if(password.equals(confirmPassword)){
                    CustomerAccount temp = new CustomerAccTemp(username, password);
                    CustomerDAO dao = new CustomerDAO();
                    if(dao.accountExist(temp)){
                        JOptionPane.showMessageDialog(submitSignup, "Signup failed");
                        msgFailed.setVisible(true);
                    }
                    else{
                        temp = new CustomerAccount(username, password);
                        dao.addAccount(temp);
                        JOptionPane.showMessageDialog(submitSignup, "Signup success!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(submitSignup, "Login failed.");
                    msgFailed2.setVisible(true);
                }
            }
        });
    }
}