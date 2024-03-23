package GUI.Panels.GamePanelConponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Collections.DeckComponents.Card;

public class CardPanel extends JPanel {
    public CardPanel(Card card) {
        setPreferredSize(new Dimension(138, 186));
        setLayout(new BorderLayout());

        Image image = card.getCardImage().getScaledInstance(138, 186, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(labelComponent, BorderLayout.CENTER);
    }
}

