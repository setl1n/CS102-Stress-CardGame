package game;

import collections.*;
import GUI.SoundUtility;
import player.*;

public class Game{
    private Player player1;
    private Player player2;
    private Pile[] piles;

    public Game() {
        // sets up the game
        SoundUtility.bgmSound();
        Deck startingDeck = new Deck(false);
        // commented out shuffling for easy debug, utyalls
        // startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        halfDeck.changeColour();
        player1 = new Player("Player 1", startingDeck);
        player2 = new Player("Player 2", halfDeck);
        piles = new Pile[]{new Pile(), new Pile()};
        openCardsFromDeck();
    }

    public void openCardsFromDeck() {
        // Check if both players have at least one card to open.
        if (GameState.STATE == GameState.NO_VALID_MOVES_BOTH_PLAYERS_HAVE_CARDS_IN_DECK) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);

        } else if (GameState.STATE == GameState.NO_VALID_MOVES_PLAYER1_HAS_CARDS_IN_DECK) {
            player1.openCardToPile(piles[0]);
            player1.openCardToPile(piles[1]);
            
        } else if (GameState.STATE == GameState.NO_VALID_MOVES_PLAYER2_HAS_CARDS_IN_DECK) {
            player2.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        }
        
    }
    
    // private void handleOpeningCardShortages() {
    //     // Scenario when one player has no cards but the other has more than one
    //     if (player1.getDeck().isEmpty() && player2.getDeck().size() > 1) {
    //         // Take two cards from Player 2 and open to the piles
    //         player2.openCardToPile(piles[0]);
    //         player2.openCardToPile(piles[1]);
    //     } else if (player2.getDeck().isEmpty() && player1.getDeck().size() > 1) {
    //         // Take two cards from Player 1 and open to the piles
    //         player1.openCardToPile(piles[0]);
    //         player1.openCardToPile(piles[1]);
    //     } else if ((player1.getDeck().isEmpty() && player2.getDeck().size() == 1) ||
    //                (player2.getDeck().isEmpty() && player1.getDeck().size() == 1)) {
    //         // If either player has exactly one card while the other has none,
    //         // declare the game a draw since neither can fulfill the "take 2 cards" requirement.
    //         System.out.println("DRAW due to insufficient cards");
    //         GameState.STATE = GameState.STALEMATE;
    //         end();
    //     } else {
    //         // Any other scenario (both players have exactly one card, or both have none)
    //         // should also result in a draw.
    //         System.out.println("DRAW due to both players having insufficient cards");
    //         GameState.STATE = GameState.STALEMATE;
    //         end();
    //     }
    // }

    private boolean areBothPlayersOutOfMoves() {
        return !player1.getHand().hasValidMoves(piles) && !player2.getHand().hasValidMoves(piles);
    }

    private boolean doBothPlayersHaveAtLeast1CardInDeck() {
        return !player1.getDeck().isEmpty() && !player2.getDeck().isEmpty();
    }

    private boolean doesPlayer1HaveAtLeast2CardsInDeck() {
        return player1.getDeck().size() >= 2;
    }
    private boolean doesPlayer2HaveAtLeast2CardsInDeck() {
        return player2.getDeck().size() >= 2;
    }

    public void checkGameState(){
        boolean isPlayer1HandEmpty = player1.getHand().isEmpty();
        boolean isPlayer2HandEmpty = player2.getHand().isEmpty();

        if (isPlayer1HandEmpty && !isPlayer2HandEmpty) {
            System.out.println("PLAYER 1 WINS");
            GameState.STATE = GameState.PLAYER1_WINS;

        } else if (isPlayer2HandEmpty && !isPlayer1HandEmpty) {
            System.out.println("PLAYER 2 WINS");
            GameState.STATE = GameState.PLAYER2_WINS;

        } else if (areBothPlayersOutOfMoves()) {

            if (doBothPlayersHaveAtLeast1CardInDeck()) {
                System.out.println("FREEZE SCREEN!!! PRESS \"S\" AND \"K\" TO CONTINUE");
                GameState.STATE = GameState.NO_VALID_MOVES_BOTH_PLAYERS_HAVE_CARDS_IN_DECK;

            } else if (doesPlayer1HaveAtLeast2CardsInDeck()) {
                GameState.STATE = GameState.NO_VALID_MOVES_PLAYER1_HAS_CARDS_IN_DECK;

            } else if (doesPlayer2HaveAtLeast2CardsInDeck()) {
                GameState.STATE = GameState.NO_VALID_MOVES_PLAYER2_HAS_CARDS_IN_DECK;

            } else {
                GameState.STATE = GameState.STALEMATE;
            }
        }
    }
    

    // public boolean draw(){
    //     boolean player1EmptyDeck = player1.getDeck().isEmpty();
    //     boolean player2EmptyDeck = player2.getDeck().isEmpty();
    //     boolean player1EmptyHand = player1.getHand().isEmpty();
    //     boolean player2EmptyHand = player2.getHand().isEmpty();

    //     if ((areBothPlayersOutOfMoves() && player1EmptyDeck && player2EmptyDeck) ||
    //     (player1EmptyDeck && player1EmptyHand && player2EmptyDeck && player2EmptyHand)){
    //         System.out.println("DRAW");
    //         GameState.STATE = GameState.STALEMATE;
    //         return true;
            
    //     }
    //     return false;
    // }

    // public boolean win(){
    //     boolean player1EmptyDeck = player1.getDeck().isEmpty();
    //     boolean player2EmptyDeck = player2.getDeck().isEmpty();
    //     boolean player1EmptyHand = player1.getHand().isEmpty();
    //     boolean player2EmptyHand = player2.getHand().isEmpty();

    //     if (player1EmptyDeck && player1EmptyHand && (!player2EmptyDeck || !player2EmptyHand)){
    //         System.out.println("PLAYER 1 WINS");
    //         GameState.STATE = GameState.RED_WINS;
    //         return true;
    //     } else if (player2EmptyDeck && player2EmptyHand && (!player1EmptyDeck || !player1EmptyHand)){
    //         System.out.println("PLAYER 2 WINS");
    //         GameState.STATE = GameState.BLU_WINS;
    //         return true;
    //     } 
    //     return false;

    // }

    public void end() {
        System.out.println("Press spacebar to play a new game! Else, press '.' to exit.");
       
    }

    // public void checkGameState(){

    //     if (draw() || win()){
    //         end();
    //     } else if (areBothPlayersOutOfMoves()){
    //         System.out.println("FREEZE SCREEN!!! PRESS \"S\" AND \"K\" TO CONTINUE");
    //         GameState.STATE = GameState.NOVALIDMOVES;
    //     }
    // }

    public Pile getPile(int index) {
        return piles[index];
    }

    public Pile[] getBothPiles() {
        return piles;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    // for debugging
    public void printGameInfo() {
        if (piles[0] != null && piles[1] != null) {
            System.out.println("\nPile 1 Top Card: " + piles[0].peekTopCard());
            System.out.println("\nPile 2 Top Card: " + piles[1].peekTopCard());
        }
        System.out.println("\nPlayer 1 state:\n" + player1);
        System.out.println("\nPlayer 2 state:\n" + player2);
    }

    public void stress(Player opponent) {
        Deck opponentDeck = opponent.getDeck();

        if (GameLogicUtils.isValidStress(piles)){
            // add pile to loser's hand
            for (Pile p : piles){
                opponentDeck.transfer(p);
            }
            // shuffles opponent's deck
            opponentDeck.shuffle();
            // game "restarts"
            openCardsFromDeck();
            GameState.STATE = GameState.STRESS;
        } else {
            System.out.println("Invalid Stress");
            // insert penalty here
        }
    }
    
}