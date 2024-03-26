package Player;

import java.util.Arrays;
import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import GUI.GUIUtility;
import Game.GameLogicUtils;

public class Hand {
    private Card[] cardsInHand = new Card[SIZE_OF_HAND];
    private static final int SIZE_OF_HAND = 4;

    public Hand() {
    }

    public Card getCardAtIndex(int index) {
        return cardsInHand[index];
    }

    protected Card popCardAtIndex(int index) {
        Card toReturn = cardsInHand[index];
        cardsInHand[index] = null;
        return toReturn;
    }

    // I think this should be better, removed the previous comments
    protected void drawCard(Deck deck) {
        if (deck.isEmpty()) {
            System.out.println("Deck is empty!");
            return;
        }
        int index = 0;
        while (cardsInHand[index] != null && index < SIZE_OF_HAND) {
            index++;
        }
        if (index == SIZE_OF_HAND) {
            System.out.println("Hand is already full");
            return;
        }
        cardsInHand[index] = deck.popTopCard();
    }

    public boolean anyValidMoves(Pile[] pile) {
        for (Card card : cardsInHand) {
            if ((GameLogicUtils.isValidThrow(card, pile[0].peekTopCard())) ||
                    (GameLogicUtils.isValidThrow(card, pile[1].peekTopCard()))) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        boolean isEmpty = true;
        for (Card card : cardsInHand) {
            if (card != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    @Override
    public String toString() {
        return "Cards: " + Arrays.toString(cardsInHand);
    }
}