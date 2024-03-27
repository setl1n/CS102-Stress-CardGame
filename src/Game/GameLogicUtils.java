package game;

import cardcollections.*;
import cardcollections.deckcomponents.*;

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
}
