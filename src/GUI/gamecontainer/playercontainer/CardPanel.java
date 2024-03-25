package GUI.gamecontainer.playercontainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Collections.DeckComponents.Card;
import GUI.GUIUtility;

public class CardPanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    private JLabel cardLabel;

    public CardPanel(Card card) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        cardLabel = GUIUtility.renderCard(card, width, height);
        add(cardLabel, BorderLayout.CENTER);
    }

    public void update(Card card) {
        GUIUtility.renderCard(cardLabel, card, width, height);
    }
}

