package cardcollections;

import java.util.Arrays;

import game.GameLogicUtils;

/**
 * This class represents a hand of cards. It provides methods to get, pop, and draw 
 * cards, and to check if the hand has valid moves or is empty.
 */
public class Hand {
    /**
     * The cards in the hand.
     */
    private Card[] cardsInHand = new Card[SIZE_OF_HAND];

    /**
     * The size of the hand.
     */
    private static final int SIZE_OF_HAND = 4;

    /**
     * Returns the card at the specified index.
     * @param index The index of the card.
     * @return The card at the specified index.
     */
    public Card getCardAtIndex(int index) {
        return cardsInHand[index];
    }

    /**
     * Removes and returns the card at the specified index.
     * @param index The index of the card.
     * @return The card at the specified index.
     */
    public Card popCardAtIndex(int index) {
        Card toReturn = cardsInHand[index];
        cardsInHand[index] = null;
        return toReturn;
    }

    /**
     * Draws a card from the specified deck and adds it to the hand.
     * If the deck is empty, an error message is printed and the method returns.
     * If the hand is already full, an error message is printed and the method returns.
     * @param deck The deck from which a card is drawn.
     */
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

    /**
     * Checks if the hand has any valid moves.
     * @param pile The piles to check for valid moves.
     * @return True if the hand has any valid moves, false otherwise.
     */
    public boolean hasValidMoves(Pile[] pile) {
        if (GameLogicUtils.isValidStress(pile)) {
            return true;
        }
        for (Card card : cardsInHand) {
            if ( GameLogicUtils.isValidThrow(card, pile[0].peekTopCard()) 
                    || GameLogicUtils.isValidThrow(card, pile[1].peekTopCard()) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the hand is empty.
     * @return True if the hand is empty, false otherwise.
     */
    public boolean isEmpty() {
        for (Card card : cardsInHand) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the hand.
     * @return A string representation of the hand.
     */
    @Override
    public String toString() {
        return String.format("Hand: %s\n", Arrays.toString(cardsInHand));
    }
}