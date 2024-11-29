import java.awt.CardLayout;

import javax.swing.*;
import GUI.AppPanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Code Panda");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create CardLayout and main panel
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        cardPanel.add(new AppPanel(cardPanel, cardLayout), "AppPanel");
        // Add the main panel to the frame
        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
