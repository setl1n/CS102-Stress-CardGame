package Game;

import Collections.DeckComponents.Card;

public class GameLogicUtils {
    public static boolean isValidThrow(Card card1, Card card2){
        System.out.println("card1 symbol: " + card1.getRank().getSymbol());
        System.out.println("card2 symbol: " + card2.getRank().getSymbol());
        String card1Rank = card1.getRank().getSymbol();
        String card2Rank = card2.getRank().getSymbol();

        // PLEASE CHANGE IT IDK WHATS GOING ON
        int card1RankAsInt = 1;
        int card2RankAsInt = 0;
        if ((card1RankAsInt == 1 && card2RankAsInt == 13) || (card1RankAsInt == 13 && card2RankAsInt == 1)) {
            return true;
        }
        return Math.abs(card1RankAsInt - card2RankAsInt) <= 1;
    }
}
