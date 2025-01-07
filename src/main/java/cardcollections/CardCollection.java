package cardcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a collection of cards. It provides methods to add, remove, peek, shuffle, and transfer cards.
 */
public class CardCollection {

    /**
     * The collection of cards.
     */
    private List<Card> cardCollection;

    /**
     * Constructs a new CardCollection and initializes it.
     */
    CardCollection() {
        this.cardCollection = new ArrayList<Card>();
    }

    /**
     * Adds a card to the collection. The card is added to the end of the collection.
     * Treats index 0 as the bottom of the deck
     * @param card The card to be added.
     */
    void add(Card card) {
        cardCollection.add(card);
    }
    
    /**
     * Removes and returns the top card from the collection.
     * @return The top card from the collection.
     */
    Card popTopCard() {
        return cardCollection.remove(cardCollection.size() - 1);
    }
    
    /**
     * Returns the top card from the collection without removing it.
     * @return The top card from the collection.
     */
    Card peekTopCard() {
        return cardCollection.get(cardCollection.size() - 1);
    }

    /**
     * Returns the size of the collection.
     * @return The size of the collection.
     */
    int size() {
        return cardCollection.size();
    }

    /**
     * Shuffles the collection of cards.
     */
    void shuffle() {
        Collections.shuffle(cardCollection);
    }

    /**
     * Checks if the collection is empty.
     * @return True if the collection is empty, false otherwise.
     */
    boolean isEmpty() {
        return cardCollection.isEmpty();
    }

    /**
     * Transfers all cards to another CardCollection from this one.
     * @param c The CardCollection to which cards are to be transferred.
     */
    void transfer(CardCollection c) {
        int size = c.size();
        for (int i = 0; i < size; i++) {
            cardCollection.add(c.popTopCard());
        }
    }

}