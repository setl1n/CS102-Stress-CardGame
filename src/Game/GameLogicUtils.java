package game;

import cardcollections.*;

/**
 * This class provides utility methods for game logic.
 */
public class GameLogicUtils {

    /**
     * Checks if a throw is valid.
     * @param card1 The first card.
     * @param card2 The second card that is to be compared.
     * @return true if the throw is valid, false otherwise.
     */
    public static boolean isValidThrow(Card card1, Card card2) {
        if (card1 == null || card2 == null) {
            return false;
        }
        int difference = Math.abs(card1.compareTo(card2));
        return difference == 12 || difference == 0 || difference == 1;
    }

    /**
     * Checks if a stress is valid.
     * @param piles The piles of cards.
     * @return true if the stress is valid, false otherwise.
     */
    public static boolean isValidStress(Pile[] piles) {
        Card card1 = piles[0].peekTopCard();
        Card card2 = piles[1].peekTopCard();

        if (card1.compareTo(card2) == 0) {
            System.out.println("STRESS!");
            return true;
        }
        return false;
    }

    /**
     * Checks if both players are out of moves.
     * @param player1 The first player.
     * @param player2 The second player.
     * @param piles The piles of cards.
     * @return true if both players are out of moves, false otherwise.
     */
    static boolean bothPlayersOutOfMoves(Player player1, Player player2, Pile[] piles) {
        return !player1.getHand().hasValidMoves(piles) && !player2.getHand().hasValidMoves(piles);
    }

    /**
     * Checks if each player has at least one card in their deck.
     * @param player1 The first player.
     * @param player2 The second player.
     * @return true if each player has at least one card in their deck, false otherwise.
     */
    static boolean eachPlayerHasAtLeast1CardInDeck(Player player1, Player player2) {
        return !player1.getDeck().isEmpty() && !player2.getDeck().isEmpty();
    }
}