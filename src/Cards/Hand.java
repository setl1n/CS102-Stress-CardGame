package src.Cards;
import java.util.*;

public class Hand {
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void setCard(Card card, int index) {
        if (card != null) {
            cards.set(index, card);
        } else {
            cards.set(index, null);
        }
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}