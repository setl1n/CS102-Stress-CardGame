package controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import collections.*;
import GUI.MainGUI;
import game.Game;
import game.GameState;
import collections.*;
import player.*;

public class MainControls extends KeyAdapter {
    private Set<Integer> pressedKeys = new HashSet<>();
    private Game game;
    private MainGUI GUI;

    public MainControls(Game game, MainGUI GUI) {
        this.game = game;
        this.GUI = GUI;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newKeyPress = e.getKeyCode();
        pressedKeys.add(newKeyPress);
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Pile[] piles = game.getBothPiles();
        switch (GameState.STATE) {
            // start screen
            case START_SCREEN:
                if (newKeyPress == KeyEvent.VK_SPACE) {
                    GUI.changeToPanel("Game");
                    GameState.STATE = GameState.PLAYING;
                }
                break;
            case OPEN_FIRST_CARDS:
                break;
            // standard gameplay conditions
            case PLAYING:
                switch (newKeyPress) {
                    case KeyEvent.VK_Q -> player1.throwCardToPile(0, piles);
                    case KeyEvent.VK_W -> player1.throwCardToPile(1, piles);
                    case KeyEvent.VK_E -> player1.throwCardToPile(2, piles);
                    case KeyEvent.VK_R -> player1.throwCardToPile(3, piles);
                    case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
                    case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
                    case KeyEvent.VK_S -> game.stress(player2);
                    case KeyEvent.VK_U -> player2.throwCardToPile(0, piles);
                    case KeyEvent.VK_I -> player2.throwCardToPile(1, piles);
                    case KeyEvent.VK_O -> player2.throwCardToPile(2, piles);
                    case KeyEvent.VK_P -> player2.throwCardToPile(3, piles);
                    case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
                    case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
                    case KeyEvent.VK_K -> game.stress(player1);
                }
                break;
            // freeze screen conditions
            case STRESS, NO_VALID_MOVES_BOTH_PLAYERS_HAVE_CARDS_IN_DECK, NO_VALID_MOVES_PLAYER1_HAS_CARDS_IN_DECK,
                    NO_VALID_MOVES_PLAYER2_HAS_CARDS_IN_DECK:
                switch (newKeyPress) {
                    case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
                    case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
                    case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
                    case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
                }
                if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
                    game.openCardsFromDeck();
                    GameState.STATE = GameState.PLAYING;
                }
                // play stress cut screen, lock
                break;
            // end game conditions
            case STALEMATE, PLAYER1_WINS, PLAYER2_WINS:
                System.out.println("END CONDITION DETECTED");
                switch (newKeyPress) {
                    case KeyEvent.VK_PERIOD -> GUI.dispose();
                }
                break;
        }
        System.out.println("Keys Pressed: " + pressedKeys.stream().map(i -> (char) i.intValue()).collect(Collectors.toList()));
        game.printGameInfo();
        game.checkGameState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
