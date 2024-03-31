package cardcollections;

import java.util.*;
import game.*;

public class Hand {
    private Card[] cardsInHand = new Card[SIZE_OF_HAND];
    private static final int SIZE_OF_HAND = 4;


    public Card getCardAtIndex(int index) {
        return cardsInHand[index];
    }

    public Card popCardAtIndex(int index) {
        Card toReturn = cardsInHand[index];
        cardsInHand[index] = null;
        return toReturn;
    }

    public void drawCard(Deck deck) {
        if (deck.isEmpty()) {
            System.out.println("Deck is empty!");
            return;
        }
        int index = 0;
        while (index < SIZE_OF_HAND && cardsInHand[index] != null) {
            index++;
        }
        if (index == SIZE_OF_HAND) {
            System.out.println("Hand is already full");
            return;
        }
        cardsInHand[index] = deck.popTopCard();
    }

    public boolean hasValidMoves(Pile[] pile) {
        if (GameLogicUtils.isValidStress(pile)) {
            return true;
        }
        for (Card card : cardsInHand) {
            if ( GameLogicUtils.isValidThrow(card, pile[0].peekTopCard()) ||
                 GameLogicUtils.isValidThrow(card, pile[1].peekTopCard()) ) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {

        for (Card card : cardsInHand) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Hand: %s\n", Arrays.toString(cardsInHand));
    }
}