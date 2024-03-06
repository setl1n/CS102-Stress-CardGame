package Collections;

import Collections.DeckComponents.CardComponents.*;

import java.util.Collections;

import javax.swing.ImageIcon;
import Collections.DeckComponents.*;

public class Deck extends CardCollection {
    /**
     * Creates a shuffled deck of 52 cards
     */
    public Deck(Boolean isEmpty) {
        super();
        if (!isEmpty) {
            for (Suit suit : (Iterable<Suit>) Suit.VALUES) {
                for (Rank rank : (Iterable<Rank>) Rank.VALUES) {
                    ImageIcon cardFace = new ImageIcon(Card.getFilename(suit, rank));
                    add(new Card(suit, rank, cardFace));
                }
            }
        }
    }

    // makes shuffle available to caller as shuffle is protected in cardcollection
    // checked with chatgpt, this is good OOP as it's more important to encapsulate
    // alternatively, need to make arraylist cardcollections protected, which is worse than 
    // having a "layer" in the child class for shuffle
    public void shuffle() {
        super.shuffle();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    public int size() {
        return getSizeOfCardCollection();
    }

    public Card popTopCard(){
        return super.popTopCard();
    }

    // returns a new deck with top half of deck
    // original deck holds (remaining) bottom half
    //
    // note: order of cards that gets added to new deck gets reversed 
    //      while bottom half of old cards stay in order
    // -should not affect implementation, just for own's knowledge
    public Deck splitAndReturnHalf() {
        Deck deckToReturn = new Deck(true);
        int cardsToTransfer = this.size() / 2;
        for (int i = 0; i < cardsToTransfer; i++) {
            Card cardToTrasfer = this.popTopCard();
            deckToReturn.add(cardToTrasfer);
        }
        return deckToReturn;
    }
}