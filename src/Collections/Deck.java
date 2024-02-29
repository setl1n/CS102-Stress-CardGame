package Collections;

import Collections.DeckComponents.CardComponents.*;
import javax.swing.ImageIcon;
import Collections.DeckComponents.*;

public class Deck extends CardCollection {
    /**
     * Creates a shuffled deck of 52 cards
     */
    public Deck() {
        super();
        for (Suit suit : (Iterable<Suit>)Suit.VALUES) {
            for (Rank rank : (Iterable<Rank>)Rank.VALUES) {
                ImageIcon cardFace = new ImageIcon(Card.getFilename(suit, rank));
                addCardToTop(new Card(suit, rank, cardFace));
            }
        }
        shuffle();
    }
}