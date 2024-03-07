package src.Cards;

import java.util.ArrayList;
import java.util.List;

public class Pile {

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        return cards.get(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

}
