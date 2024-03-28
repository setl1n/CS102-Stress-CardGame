package gui.gamecontainer.playercontainer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

import gui.GUIUtility;
import collections.Deck;

public class DeckPanel extends JPanel {

    static final int width = 150;
    static final int height = 210;

    private Deck deck;
    private JLabel deckLabel;

    public DeckPanel(Deck deck) {
        this.deck = deck;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        deckLabel = GUIUtility.renderDeck(deck, width, height);
        add(deckLabel, BorderLayout.CENTER);
    }

    public void updateDeck() {
        deck.updateImageToSize();
        GUIUtility.renderDeck(deckLabel, deck, width, height);
    }
}
