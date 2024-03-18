package Player;

import java.util.Arrays;
import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import Game.GameLogicUtils;

public class Hand{
    private Card[] cardsInHand;
    private static final int SIZEOFHAND = 4;

    public Hand(Deck deck) {
        cardsInHand = new Card[SIZEOFHAND];
        for (int i = 0; i < SIZEOFHAND; i++) {

            // this would throw IndexOutOfBoundsException if deck has less than 4 cards, but should not happen in normal flow of game
            cardsInHand[i] = deck.popTopCard();
        }
    }

    protected Card getCardAtIndex(int index) {
        return cardsInHand[index];
    }

    protected void removeCardAtIndex(int index) {
        cardsInHand[index] = null;
    }

    // I think this should be better, removed the previous comments
    protected void drawCard(Deck deck) {
        if (deck.isEmpty()) {
            System.out.println("Deck is empty!");
            return;
        }
        int index = 0;
        while (cardsInHand[index] != null && index < SIZEOFHAND) {
            index++;
        }
        if (index == SIZEOFHAND) {
            System.out.println("Hand is already full");
            return;
        }
        cardsInHand[index] = deck.popTopCard();
    }

    public boolean anyValidMoves(Pile[] pile){
        for (int i = 0; i < 4; i++) {
            if ((GameLogicUtils.isValidThrow(getCardAtIndex(i), pile[0].peekTopCard())) ||
            (GameLogicUtils.isValidThrow(getCardAtIndex(i), pile[1].peekTopCard()))){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hand [cardsInHand=" + Arrays.toString(cardsInHand) + "]";
    }
}
