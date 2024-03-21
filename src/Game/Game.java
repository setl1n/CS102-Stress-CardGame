package Game;

import java.util.Scanner;

import javax.swing.*;
import Collections.Deck;
import Collections.Pile;
import Player.Player;

public class Game extends JFrame {
    private Player player1;
    private Player player2;
    private Pile[] piles;

    public Game() {
        // sets up the game
        Deck startingDeck = new Deck(false);
        // commented out shuffling for easy debug, utyalls
        // startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        player1 = new Player(startingDeck);
        player2 = new Player(halfDeck);
        piles = new Pile[2];
        for (int i = 0; i < 2; i++) {
            piles[i] = new Pile();
        }
        openCardsToStart();
    }

    public void openCardsToStart() {
        if (!player1.isEmptyDeck() && !player2.isEmptyDeck()) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        }
        
    }

    public boolean bothPlayersNoValidMoves(){
        boolean player1hasValidMoves = player1.getPlayerHand().anyValidMoves(piles);
        boolean player2hasValidMoves = player2.getPlayerHand().anyValidMoves(piles);
        if (!player1hasValidMoves && !player2hasValidMoves){
            return true;
        }
        return false;
    }

    public void stress(Player opponent){
        Deck opponentDeck = opponent.getPlayerDeck();

        if (GameLogicUtils.isValidStress(piles[0].peekTopCard(), piles[1].peekTopCard())){
            // add pile to loser's hand
            for (Pile p : piles){
                opponentDeck.transfer(p);
            }
            // shuffles opponent's deck
            opponentDeck.shuffle();
            // game "restarts"
            openCardsToStart();
        }
    }

    public boolean draw(){
        boolean player1EmptyDeck = player1.getPlayerDeck().isEmpty();
        boolean player2EmptyDeck = player2.getPlayerDeck().isEmpty();
        boolean player1EmptyHand = player1.getPlayerHand().isEmpty();
        boolean player2EmptyHand = player2.getPlayerHand().isEmpty();

        if ((bothPlayersNoValidMoves() && player1EmptyDeck && player2EmptyDeck) ||
        (player1EmptyDeck && player1EmptyHand && player2EmptyDeck && player2EmptyHand)){
            System.out.println("DRAW");
            return true;
            
        }
        return false;
    }

    public boolean win(){
        boolean player1EmptyDeck = player1.getPlayerDeck().isEmpty();
        boolean player2EmptyDeck = player2.getPlayerDeck().isEmpty();
        boolean player1EmptyHand = player1.getPlayerHand().isEmpty();
        boolean player2EmptyHand = player2.getPlayerHand().isEmpty();

        if (player1EmptyDeck && player1EmptyHand && (!player2EmptyDeck || !player2EmptyHand)){
            System.out.println("PLAYER 1 WINS");
            return true;
        } else if (player2EmptyDeck && player2EmptyHand && (!player1EmptyDeck || !player1EmptyHand)){
            System.out.println("PLAYER 2 WINS");
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
            openCardsToStart();
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
    public void printGameState() {
        if (piles[0] != null && piles[1] != null) {
            System.out.println("\nPile 1 Top Card: " + piles[0].peekTopCard());
            System.out.println("\nPile 2 Top Card: " + piles[1].peekTopCard());
        }
        System.out.println("\nPlayer 1 state:\n" + player1);
        System.out.println("\nPlayer 2 state:\n" + player2);
    }

    public void handleKeyPress(char key) {
        switch (key) {
            case 'q':
                player1.throwCardToPile(0, piles);
                break;
            case 'w':
                player1.throwCardToPile(1, piles);
                break;
            case 'e':
                player1.throwCardToPile(2, piles);
                break;
            case 'r':
                player1.throwCardToPile(3, piles);
                break;

            case 'a':
                player1.selectTargetPile('l');
                break;
            case 'd':
                player1.selectTargetPile('r');
                break;
            case 's':
                stress(player2);
                break;

            case 'u':
                player2.throwCardToPile(0, piles);
                break;
            case 'i':
                player2.throwCardToPile(1, piles);
                break;
            case 'o':
                player2.throwCardToPile(2, piles);
                break;
            case 'p':
                player2.throwCardToPile(3, piles);
                break;

            case 'j':
                player2.selectTargetPile('l');
                break;
            case 'l':
                player2.selectTargetPile('r');
            case 'k':
                stress(player1);
                break;

            case ' ':
                Game game = new Game();
                break;
            case '.': 
                System.out.println("Thanks for playing!");
                break;

            // default:
            // pause?
        }
        printGameState();
        checkGameState();
    }
}