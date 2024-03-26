package Game;

import java.util.*;
import javax.swing.*;
import Collections.Deck;
import Collections.Pile;
import GUI.GUIUtility;
import GUI.SoundUtility;
import Player.Player;
import java.awt.event.KeyEvent;

public class Game{
    private final static int NUM_OF_PILES = 2;
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
        piles = new Pile[NUM_OF_PILES];
        for (int i = 0; i < NUM_OF_PILES; i++) {
            piles[i] = new Pile();
        }
        openCardsToStart();
    }

    public void openCardsToStart() {
        // Check if both players have at least one card to open.
        if (!player1.getDeck().isEmpty() && !player2.getDeck().isEmpty()) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        } else {
            // Handle edge cases when one or both players don't have enough cards
            handleOpeningCardShortages();
        }
    }
    
    private void handleOpeningCardShortages() {
        // Scenario when one player has no cards but the other has more than one
        if (player1.getDeck().isEmpty() && player2.getDeck().size() > 1) {
            // Take two cards from Player 2 and open to the piles
            player2.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        } else if (player2.getDeck().isEmpty() && player1.getDeck().size() > 1) {
            // Take two cards from Player 1 and open to the piles
            player1.openCardToPile(piles[0]);
            player1.openCardToPile(piles[1]);
        } else if ((player1.getDeck().isEmpty() && player2.getDeck().size() == 1) ||
                   (player2.getDeck().isEmpty() && player1.getDeck().size() == 1)) {
            // If either player has exactly one card while the other has none,
            // declare the game a draw since neither can fulfill the "take 2 cards" requirement.
            System.out.println("DRAW due to insufficient cards");
            GameState.STATE = GameState.STALEMATE;
            end();
        } else {
            // Any other scenario (both players have exactly one card, or both have none)
            // should also result in a draw.
            System.out.println("DRAW due to both players having insufficient cards");
            GameState.STATE = GameState.STALEMATE;
            end();
        }
    }

    public boolean bothPlayersNoValidMoves(){
        boolean player1hasValidMoves = player1.getHand().anyValidMoves(piles);
        boolean player2hasValidMoves = player2.getHand().anyValidMoves(piles);
        if (!player1hasValidMoves && !player2hasValidMoves){
            return true;
        }
        return false;
    }

    

    public boolean draw(){
        boolean player1EmptyDeck = player1.getDeck().isEmpty();
        boolean player2EmptyDeck = player2.getDeck().isEmpty();
        boolean player1EmptyHand = player1.getHand().isEmpty();
        boolean player2EmptyHand = player2.getHand().isEmpty();

        if ((bothPlayersNoValidMoves() && player1EmptyDeck && player2EmptyDeck) ||
        (player1EmptyDeck && player1EmptyHand && player2EmptyDeck && player2EmptyHand)){
            System.out.println("DRAW");
            return true;
            
        }
        return false;
    }

    public boolean win(){
        boolean player1EmptyDeck = player1.getDeck().isEmpty();
        boolean player2EmptyDeck = player2.getDeck().isEmpty();
        boolean player1EmptyHand = player1.getHand().isEmpty();
        boolean player2EmptyHand = player2.getHand().isEmpty();

        if (player1EmptyDeck && player1EmptyHand && (!player2EmptyDeck || !player2EmptyHand)){
            System.out.println("PLAYER 1 WINS");
            GameState.STATE = GameState.RED_WINS;
            return true;
        } else if (player2EmptyDeck && player2EmptyHand && (!player1EmptyDeck || !player1EmptyHand)){
            System.out.println("PLAYER 2 WINS");
            GameState.STATE = GameState.BLU_WINS;
            return true;
        } 
        return false;

    }

    public void end() {
        System.out.println("Press spacebar to play a new game! Else, press '.' to exit.");
       
    }

    public void checkGameState(){
        if (draw() || win()){
            end();
        } else if (bothPlayersNoValidMoves()){
            System.out.println("FREEZE SCREEN!!! PRESS \"S\" AND \"K\" TO CONTINUE");
            GameState.STATE = GameState.NOVALIDMOVES;
        }
    }

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
            openCardsToStart();
        } else {
            System.out.println("Invalid Stress");
            // insert penalty here
        }
    }
    
}