package game;

import player.*;
import collections.Pile;
import collections.deckcomponents.Card;
import collections.deckcomponents.cardcomponents.*;

public class GameLogicUtils {

    public static boolean isValidThrow(Card card1, Card card2) {
        if (card1 == null || card2 == null) {
            return false;
        }
        int difference = Math.abs(card1.compareTo(card2));
        return difference == 12 || difference == 0 || difference == 1;
    }

    public static boolean isValidStress(Pile[] piles) {
        Rank card1Rank = piles[0].peekTopCard().getRank();
        Rank card2Rank = piles[1].peekTopCard().getRank();

        if (card1Rank == card2Rank) {
            System.out.println("STRESS!");
            return true;
        }
        return false;
    }
}
