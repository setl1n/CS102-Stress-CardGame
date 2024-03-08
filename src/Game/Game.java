package Game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Collections.Deck;
import Collections.Pile;
import Player.Player;

public class Game extends JFrame implements KeyListener {
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

    public void run() {
        setTitle("Stress");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
        // for now auto opencards to start but shld implement for
        // both of them to have to press a button at the same time
        openCardsToStart();
        player1.drawFourCards();
        player2.drawFourCards();
    }

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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // handle card selection
            case KeyEvent.VK_Q:
                player1.throwCardToPile(0, this);
                break;
            case KeyEvent.VK_W:
                player1.throwCardToPile(1, this);
                break;
            case KeyEvent.VK_E:
                player1.throwCardToPile(2, this);
                break;
            case KeyEvent.VK_R:
                player1.throwCardToPile(3, this);
                break;
            case KeyEvent.VK_U:
                player2.throwCardToPile(0, this);
                break;
            case KeyEvent.VK_I:
                player2.throwCardToPile(1, this);
                break;
            case KeyEvent.VK_O:
                player2.throwCardToPile(2, this);
                break;
            case KeyEvent.VK_P:
                player2.throwCardToPile(3, this);
                break;

            // handle deck selection
            case KeyEvent.VK_A:
                System.out.println("A was pressed");
                break;
            case KeyEvent.VK_D:
                System.out.println("D was pressed");
                break;
            case KeyEvent.VK_J:
                System.out.println("J was pressed");
                break;
            case KeyEvent.VK_L:
                System.out.println("L was pressed");
                break;

            // handle calling stress
            case KeyEvent.VK_S:
                System.out.println("S was pressed");
                break;
            case KeyEvent.VK_K:
                System.out.println("K was pressed");
                break;

            default:
                System.out.println("INVALID KEY!!");
                return;
        }
        printGameState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
