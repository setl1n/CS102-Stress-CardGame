package Game;

import Collections.DeckComponents.Card;
import Collections.DeckComponents.CardComponents.*;;

public class GameLogicUtils {
    
    public static boolean isValidThrow(Card card1, Card card2){
        Rank card1Rank = card1.getRank();
        Rank card2Rank = card2.getRank();
        int difference = Math.abs(card1Rank.compareTo(card2Rank));
        return (difference == 13 || difference == 0 || difference == 1);
    }

    public static int getInt(String symbol) {
        char c = symbol.charAt(0);
        if (c == 'a') {
            return 1;
        } else if (c == '2') {
            return 2;
        } else if (c == '3') {
            return 3;
        } else if (c == '4') {
            return 4;
        } else if (c == '5') {
            return 5;
        } else if (c == '6') {
            return 6;
        } else if (c == '7') {
            return 7;
        } else if (c == '8') {
            return 8;
        } else if (c == '9') {
            return 9;
        } else if (c == 't') {
            return 10;
        } else if (c == 'j') {
            return 11;
        } else if (c == 'q') {
            return 12;
        } else if (c == 'k') {
            return 13;
        }
        // error catcher, remove before submission
        return -100;
    }
}
