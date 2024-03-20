package Game;

import Collections.DeckComponents.Card;
import Collections.DeckComponents.CardComponents.*;;

public class GameLogicUtils {
    
    public static boolean isValidThrow(Card card1, Card card2){
        Rank card1Rank = card1.getRank();
        Rank card2Rank = card2.getRank();
        System.out.println("card1 symbol: " + card1Rank.getSymbol());
        System.out.println("card2 symbol: " + card2Rank.getSymbol());

        int difference = Math.abs(card1Rank.compareTo(card2Rank));
        return (difference == 13 || difference == 0 || difference == 1);
    }
    
}
