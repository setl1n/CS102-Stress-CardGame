package Game;

import Collections.Pile;
import Collections.DeckComponents.Card;
import Collections.DeckComponents.CardComponents.*;
import Player.*;

public class GameLogicUtils {

    public static boolean isValidThrow(Card card1, Card card2) {
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
