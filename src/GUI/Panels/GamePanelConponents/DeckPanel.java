package GUI.Panels.GamePanelConponents;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

import Collections.Deck;

public class DeckPanel extends JPanel {
    public DeckPanel(Deck deck) {
        setPreferredSize(new Dimension(150, 210));
        setLayout(new BorderLayout());

        Image image = deck.getDeckImage().getScaledInstance(150, 210, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(labelComponent, BorderLayout.CENTER);
    }
}
