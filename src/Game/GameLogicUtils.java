package game;

import cardcollections.*;

public class GameLogicUtils {

    public static boolean isValidThrow(Card card1, Card card2) {
        if (card1 == null || card2 == null) {
            return false;
        }
        int difference = Math.abs(card1.compareTo(card2));
        return difference == 12 || difference == 0 || difference == 1;
    }

    public static boolean isValidStress(Pile[] piles) {
        Card card1 = piles[0].peekTopCard();
        Card card2 = piles[1].peekTopCard();

        if (card1.compareTo(card2) == 0) {
            System.out.println("STRESS!");
            return true;
        }
        return false;
    }

    static boolean areBothPlayersOutOfMoves(Player player1, Player player2, Pile[] piles) {
        return !player1.getHand().hasValidMoves(piles) && !player2.getHand().hasValidMoves(piles);
    }

    static boolean doBothPlayersHaveAtLeast1CardInDeck(Player player1, Player player2) {
        return !player1.getDeck().isEmpty() && !player2.getDeck().isEmpty();
    }
}
