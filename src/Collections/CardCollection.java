package Collections;

import Collections.DeckComponents.*;
import java.util.*;
import javax.swing.ImageIcon;

public class CardCollection {

    private ArrayList<Card> cardCollection;

    protected CardCollection() {
    }

    /**
     * The following assumes that index 0 of the array is the bottom card
     */
    protected void addCardToTop(Card card) {
        cardCollection.add(card);
    }

    protected void addCardToBottom(Card card) {
        cardCollection.add(0, card);
    }

    protected void removeCardFromTop(Card card) throws CardNotFoundException {
        if (!cardCollection.remove(card)) {
            throw new CardNotFoundException();
        }
    }

    protected Card getCard(int index) throws IndexOutOfBoundsException {
        return cardCollection.get(index);
    }


    /**
     * The size of a card collection.
     * 
     * @return the number of cards present in the full card collection.
     */
    protected int getSizeOfDeck() {
        return cardCollection.size();
    }

    /**
     * Shuffles the cards present in the card collection.
     */
    protected void shuffle() {
        Collections.shuffle(cardCollection);
    }

    /**
     * Looks for an empty card collection.
     * 
     * @return <code>true</code> if there are no cards left to be dealt from the
     *         deck.
     */
    protected boolean isEmpty() {
        return cardCollection.isEmpty();
    }

    protected void clear() {
        cardCollection.clear();
    }
}
