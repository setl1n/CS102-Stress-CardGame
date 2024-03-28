package gui.gamecontainer.pilecontainer;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import gui.GUIUtility;
import cardcollections.*;
import cardcollections.deckcomponents.*;

public class PilePanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    private JLabel jLabel;

    public PilePanel(Pile pile) {

        pile.setPilePanel(this);
        this.setOpaque(false);

        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        
        // Card topCard = pile.peekTopCard();
        // System.out.println(topCard);
        // Image image = topCard.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // jLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);
        
        jLabel = GUIUtility.renderLabel("/assets/emptyDeck.png", "/assets/emptyDeck.png", width, height);
        add(jLabel, BorderLayout.CENTER);

    }

    public void updatePilePanel(Card card) {
        Image image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        jLabel.setIcon(new ImageIcon(image));
        jLabel.repaint();
    }
}
