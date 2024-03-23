package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import Player.Player;

public class Game extends JFrame {
    private final static int NUM_OF_PILES = 2;
    private Player player1;
    private Player player2;
    private Pile[] piles;
    private final Map<Character, Runnable> actions = new HashMap<>();

    public Game() {

        // maps actions to runnable
        actions.put('q', () -> player1.throwCardToPile(0, piles));
        actions.put('w', () -> player1.throwCardToPile(1, piles));
        actions.put('e', () -> player1.throwCardToPile(2, piles));
        actions.put('r', () -> player1.throwCardToPile(3, piles));
        actions.put('a', () -> player1.selectTargetPile('l'));
        actions.put('d', () -> player1.selectTargetPile('r'));
        actions.put('s', () -> this.stress(player2));
        actions.put('u', () -> player2.throwCardToPile(0, piles));
        actions.put('i', () -> player2.throwCardToPile(1, piles));
        actions.put('o', () -> player2.throwCardToPile(2, piles));
        actions.put('p', () -> player2.throwCardToPile(3, piles));
        actions.put('j', () -> player2.selectTargetPile('l'));
        actions.put('l', () -> player2.selectTargetPile('r'));
        actions.put('k', () -> this.stress(player1));

        // sets up the game
        Deck startingDeck = new Deck(false);
        // commented out shuffling for easy debug, utyalls
        // startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        player1 = new Player(startingDeck);
        player2 = new Player(halfDeck);
        piles = new Pile[NUM_OF_PILES];
        for (int i = 0; i < NUM_OF_PILES; i++) {
            piles[i] = new Pile();
        }
    }

    public void openCardsToStart() {
        if (!player1.isEmptyDeck() && !player2.isEmptyDeck()) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        }
        GameLogicUtils.checkNeedToResetGame(this);
    }

    public void end() {
        System.out.println("GAME END");
        // ask to restart
        // or end program
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
        Runnable action = actions.get(key);
        if (action != null) {
            action.run();
        } else {
            System.out.println("Invalid Move!");
        }
        printGameState();
    }


    public void stress(Player opponent) {
        Deck opponentDeck = opponent.getPlayerDeck();

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