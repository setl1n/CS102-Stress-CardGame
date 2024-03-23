package Game;

import Collections.Pile;
import Collections.DeckComponents.Card;
import Collections.DeckComponents.CardComponents.*;
import Player.*;

public class GameLogicUtils {
    
    public static boolean isValidThrow(Card card1, Card card2){
        int difference = Math.abs(card1.compareTo(card2));
        return difference == 12 || difference == 0 || difference == 1;
    }

    public static boolean isValidStress(Pile[] piles) {
        int size = piles.length;
        Card firstCardRank = piles[0].peekTopCard();
        for (int i = 1; i < size; i++) {
            if (firstCardRank != piles[i].peekTopCard()) {
                return false;
            }
        }
        return true;

    }
    
}
