package gui.panels.gamecontainer.playercontainer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

import cardcollections.*;
import gui.GUIUtility;

public class DeckPanel extends JPanel {

    static final int WIDTH = 150;
    static final int HEIGHT = 210;

    private Deck deck;
    private JLabel deckLabel;

    public DeckPanel(Deck deck) {
        this.deck = deck;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        deckLabel = GUIUtility.renderDeck(deck, WIDTH, HEIGHT);
        add(deckLabel, BorderLayout.CENTER);
    }

    public void updateDeck() {
        deck.updateImageToSize();
        GUIUtility.renderDeck(deckLabel, deck, WIDTH, HEIGHT);
    }
}
