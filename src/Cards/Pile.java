package src.Cards;

import java.util.ArrayList;
import java.util.List;

public class Pile {

    private List<Card> cards = new ArrayList<>();
    private int posX;
    private int posY;

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        return cards.get(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

    public void setX(int x) {
        posX = x;
    }

    public void setY(int y) {
        posY = y;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

}
