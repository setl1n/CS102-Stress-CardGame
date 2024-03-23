package GUI.Panels.GamePanelConponents;

import javax.swing.*;
import java.awt.*;

public class PilePanel extends JPanel {
    public PilePanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 186));

        /*
         * i will look further into why this works
         */

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 7, 15, 7); // Margin around components

        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        
        // Example pile panels
//        add(new CardPanel("Pile 1"), gbc);
//        add(new CardPanel("Pile 2"), gbc);


    }
}

