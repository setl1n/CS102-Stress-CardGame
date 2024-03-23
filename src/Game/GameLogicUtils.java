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
    
    public static boolean bothPlayersNoValidMoves(Game game){
        if ((!game.getPlayer1().getPlayerHand().anyValidMoves(game.getBothPiles())) &&
        (!game.getPlayer2().getPlayerHand().anyValidMoves(game.getBothPiles()))){
            return true;
        }
        return false;
    }

    public static void checkNeedToResetGame(Game game){
        if (bothPlayersNoValidMoves(game)){
            game.openCardsToStart();
        }
    }

    public static void gameEndsWithDraw(Game game){
        if (bothPlayersNoValidMoves(game) && (game.getPlayer1().getPlayerDeck().isEmpty()) 
        && (game.getPlayer2().getPlayerDeck().isEmpty())){
            System.out.println("DRAW");
            game.end();
        }
    }
}
