package GUI.gamecontainer.playercontainer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

import Collections.Deck;

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

        Image image = deck.getDeckImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        deckLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(deckLabel, BorderLayout.CENTER);
    }

    public void updateDeck() {
        deck.updateImageToSize();
        Image image = deck.getDeckImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        deckLabel.setIcon(new ImageIcon(image));
        deckLabel.repaint();
    }
}
