package Game;

import Collections.Pile;
import Collections.DeckComponents.Card;
import Collections.DeckComponents.CardComponents.*;
import Player.*;

public class GameLogicUtils {
    
    public static boolean isValidThrow(Card card1, Card card2){
        Rank card1Rank = card1.getRank();
        Rank card2Rank = card2.getRank();
        System.out.println("card1 symbol: " + card1Rank.getSymbol());
        System.out.println("card2 symbol: " + card2Rank.getSymbol());

        int difference = Math.abs(card1Rank.compareTo(card2Rank));
        return (difference == 12 || difference == 0 || difference == 1);
    }

    public static boolean isValidStress(Card card1, Card card2){
        Rank card1Rank = card1.getRank();
        Rank card2Rank = card2.getRank();
        
        if (card1Rank == card2Rank){
            System.out.println("STRESS!");
            return true;
        }

        System.out.println("INVALID STRESS...");
        return false;
    }
    
}
