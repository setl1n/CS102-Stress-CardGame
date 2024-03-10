package Player;

import java.util.Arrays;
import Game.GameLogicUtils;
import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;

public class Hand{
    private Card[] cardsInHand;

    public Hand() {
        this.cardsInHand = new Card[4];
    }

    protected Card getCardAtIndex(int index) {
        return cardsInHand[index];
    }

    protected void removeCardAtIndex(int index) {
        cardsInHand[index] = null;
    }

    // purposely coded in a way (the algo) to throw null pointer exception 
    // if incorrectly used down the line.
    // should prob recode before submission.
    // to (for int i = 0 i < 4)?? idk
    protected void drawCard(Deck deck) throws NullPointerException {
        int index = 0;
        while (cardsInHand[index] != null) {
            index++;
        }
        cardsInHand[index] = deck.popTopCard();
    }

    @Override
    public String toString() {
        return "Hand [cardsInHand=" + Arrays.toString(cardsInHand) + "]";
    }
}
