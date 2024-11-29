package BackEnd.Utils;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BackBtn extends JButton {
    public BackBtn(JPanel cardPanel, CardLayout cardLayout, String name){
        super("Back");

        addActionListener(e -> {
            cardLayout.show(cardPanel, name);
        });
    }
}
