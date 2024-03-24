package GUI.gamecontainer.playercontainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Collections.DeckComponents.Card;

public class CardPanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    public CardPanel(Card card) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        Image image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(labelComponent, BorderLayout.CENTER);
    }
}
