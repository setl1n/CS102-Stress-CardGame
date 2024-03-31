package cardcollections;

import java.util.*;


public class CardCollection {

    private List<Card> cardCollection;

    CardCollection() {
        this.cardCollection = new ArrayList<Card>();
    }

    /*
     * The following assumes that index 0 of the array is the bottom card
     */
    void add(Card card) {
        cardCollection.add(card);
    }
    
    Card popTopCard() {
        return cardCollection.remove(cardCollection.size() - 1);
    }
    
    Card peekTopCard() {
        return cardCollection.get(cardCollection.size() - 1);
    }

    int size() {
        return cardCollection.size();
    }

    void shuffle() {
        Collections.shuffle(cardCollection);
    }

    boolean isEmpty() {
        return cardCollection.isEmpty();
    }

    void transfer(CardCollection c) {
        int size = c.size();
        for (int i = 0; i < size; i++) {
            cardCollection.add(c.popTopCard());
        }
    }

}