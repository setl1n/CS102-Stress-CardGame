package Player;

import java.util.Arrays;
import Game.GameLogicUtils;
import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;

public class Hand{
    private Card[] cardsInHand;
    private Deck deck;

    public Hand(Deck deck) {
        this.cardsInHand = new Card[4];
        this.deck = deck;
    }

    // purposely coded in a way to throw null pointer exception 
    // if incorrectly used down the line.
    // should prob recode before submission.
    private void addCard(Card card) throws NullPointerException {
        int index = 0;
        while (cardsInHand[index] != null) {
            index++;
        }
        cardsInHand[index] = card;
    }

    // deliberately did not throw exception for when hand is full as our programme should naturally avoid that
    // throws null pointer if u call to add card when hand is full
    // need to implement when u attempt to draw from empty deck
    public void drawCard() throws NullPointerException {
        if (deck.isEmpty()) {
            return;
        }
        Card card = deck.popTopCard();
        this.addCard(card);
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();
        }
    }

    @Override
    public String toString() {
        return "Hand [cardsInHand=" + Arrays.toString(cardsInHand) + "]";
    }

    public void throwCardToPile(int index, Pile pile) {
        if (GameLogicUtils.isValidThrow(cardsInHand[index], pile.peekTopCard())) {
            pile.add(cardsInHand[index]);
            
            cardsInHand[index] = null;
            drawCard();
        } else {
            // invalid move
            System.out.println("INVALID MOVE");
        }
    }
}
