package Collections;

import Collections.DeckComponents.*;
import java.util.*;

public class CardCollection {

    private ArrayList<Card> cardCollection;

    protected CardCollection() {
        this.cardCollection = new ArrayList<Card>();
    }

    /**
     * The following assumes that index 0 of the array is the bottom card
     */
    public void add(Card card) {
        cardCollection.add(card);
    }

    // commented out as prob not needed, not sure yet tho
    // rmb to delete cardnot found exception too if this method is not implemented
    // protected void removeCardFromTop(Card card) throws CardNotFoundException {
    //     if (!cardCollection.remove(card)) {
    //         throw new CardNotFoundException();
    //     }
    // }

    public Card peekTopCard() {
        return cardCollection.get(cardCollection.size() - 1);
    }

    protected Card popTopCard() {
        Card temp = cardCollection.remove(cardCollection.size() - 1);
            }

    /**
     * The size of a card collection.
     * 
     * @return the number of cards present in the full card collection.
     */
    protected int size() {
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

    protected void transfer(CardCollection c){
        int size = c.size();
        for (int i = 0; i < size; i++){
            cardCollection.add(c.peekTopCard());
            c.popTopCard();
        }
    }

    @Override
    public String toString() {
        return "CardCollection [cardCollection=" + cardCollection + "]";
    }
}