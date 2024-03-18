package Game;

import javax.swing.*;
import Collections.Deck;
import Collections.Pile;
import Player.Player;

public class Game extends JFrame  {
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
    }

<<<<<<< HEAD
=======
    public void run() {
        setTitle("Stress");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
        // for now auto creates game and opencards to start but shld implement for
        // both of them to have to press a button at the same time
        Game game = new Game();
        game.openCardsToStart();
        
    }

>>>>>>> main
    public void openCardsToStart() {
        if (!player1.isEmptyDeck() && !player2.isEmptyDeck()) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        }
    }

    public Pile getPile(int index) {
        return piles[index];
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
}